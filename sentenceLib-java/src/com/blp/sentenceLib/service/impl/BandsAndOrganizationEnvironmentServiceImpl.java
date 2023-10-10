package com.blp.sentenceLib.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.blp.sentenceLib.annotation.LogOperate;
import com.blp.sentenceLib.constant.RedisPrefixConstant;
import com.blp.sentenceLib.entity.BandEnvironment;
import com.blp.sentenceLib.enums.LogOperateType;
import com.blp.sentenceLib.remote.service.CoreRemoteService;
import com.blp.sentenceLib.service.BandsAndOrganizationEnvironmentService;
import com.blp.sentenceLib.util.JedisUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@NoArgsConstructor
public class BandsAndOrganizationEnvironmentServiceImpl implements BandsAndOrganizationEnvironmentService {

    @Autowired
    CoreRemoteService coreRemoteService;

    /*
    *  根据bandId获取organizationId
    *
    * */
    @Override
    public Long getOrganizationIdByBandId(Long objId) throws Exception {
        Map<String, Object> bandByBandObjId = coreRemoteService.getBandByBandObjId(Long.valueOf(objId));
        if (bandByBandObjId == null) {
            return null;
        }
        Long organizationId = Long.valueOf(bandByBandObjId.get("organizationID").toString());
        return organizationId;
    }

    /**
     * TODO 这里的redis操作逻辑有问题，产生数据不一致问题，先简单解决是缓存的数据30分钟失效
     *获取当前机构及该机构下的所有帮区
     * @param objId
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> getOrganizationAndBands(Long objId) throws Exception {

        HashMap<String, Object> result = new HashMap<>();
        ArrayList<HashMap> hashMaps = new ArrayList<>();
        Map<String, Object> bandByBandObjId = coreRemoteService.getBandByBandObjId(Long.valueOf(objId));
        if (bandByBandObjId == null) {
            return null;
        }
        Long ownerId = Long.valueOf(bandByBandObjId.get("ownerID").toString());
        Long organizationId = Long.valueOf(bandByBandObjId.get("organizationID").toString());
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedis();
            if (jedis.exists(RedisPrefixConstant.ORGANIZATION_ENVIRONMENT.getPrefix() + organizationId + ":" + ownerId)) {
                result = JSON.parseObject(jedis.get(RedisPrefixConstant.ORGANIZATION_ENVIRONMENT.getPrefix()+ organizationId + ":" + ownerId),
                    new TypeReference<HashMap<String, Object>>() {});
                return result;

            } else {
                JSONObject bandsByOrganizationId = coreRemoteService.getBandsByOrganizationId(organizationId);
                String rows = bandsByOrganizationId.get("rows").toString();
                List<BandEnvironment> environmentVoList =
                    JSON.parseObject(rows, new TypeReference<List<BandEnvironment>>() {});
                for (BandEnvironment environmentVo : environmentVoList) {
                    HashMap<String, Object> temp = new HashMap<>();
                    String name = environmentVo.getName();
                    String description = environmentVo.getDescription();
                    String objID = environmentVo.getObjID();
                    String realObjID = environmentVo.getRealObjID();
                    String keyWords = environmentVo.getKeywords();
                    String type = "";

                    if (keyWords.equals("机构主区")) {
                        type = "Organization";
                    } else if (keyWords.equals("部门主区")) {
                        type = "Department";
                    } else {
                        type = "Band";
                    }

                    JSONObject bandsPath = coreRemoteService.getBandsPath(Long.valueOf(objID));

                    if (bandsPath == null) {
                        temp.put("name", name);
                        temp.put("description", description);
                        temp.put("objID", objID);
                        temp.put("realObjID", realObjID);
                        temp.put("path", "路径请求异常");
                        temp.put("type", type);
                        hashMaps.add(insertIndex(type, hashMaps.size()), temp);
                        continue;
                    }

                    ArrayList<String> pathList = new ArrayList<>();
                    if (bandsPath.getJSONArray("rows").size() > 0) {
                        JSONArray paths = bandsPath.getJSONArray("rows").getJSONObject(0).getJSONArray("bandPath");
                        for (int i = 0; i < paths.size(); i++) {
                            pathList.add(0,
                                paths.getJSONObject(i).getJSONObject(String.valueOf(i)).get("name").toString());
                        }
                    } else {
                        pathList.add(name);
                    }
                    temp.put("name", name);
                    temp.put("description", description);
                    temp.put("objID", objID);
                    temp.put("realObjID", realObjID);
                    temp.put("path", pathList);
                    temp.put("type", type);
                    hashMaps.add(insertIndex(type, hashMaps.size()), temp);
                }
                result.put("bands", hashMaps);
                result.put("organization", getOrganizationInfo(organizationId));
                jedis.setnx(RedisPrefixConstant.ORGANIZATION_ENVIRONMENT.getPrefix()+ organizationId + ":" + ownerId,
                    JSON.toJSONString(result, SerializerFeature.WRITE_MAP_NULL_FEATURES));
            }
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    public int insertIndex(String type, int size) {
        if (type.equals("Organization")) {
            return 0;
        } else if (type.equals("Department")) {
            return 1;
        } else {
            return size;
        }
    }

    // todo 根据机构id查询机构下所有帮区
    @Override
    public Map<String, Object> getOrganizationEnvironment(Long organizationId) throws Exception {
        HashMap<String, Object> map = null;
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedis();
            if (jedis.exists(RedisPrefixConstant.ORGANIZATION_ENVIRONMENT.getPrefix() + organizationId)) {
                map = JSON.parseObject(
                    jedis.get(RedisPrefixConstant.ORGANIZATION_ENVIRONMENT.getPrefix() + organizationId),
                    new TypeReference<HashMap<String, Object>>() {});
                return map;
            } else {
                map = new HashMap<>();
                map.put("department", getDepartments(organizationId));
                map.put("position", getPositions(organizationId));
                map.put("users", getUsers(organizationId));
                map.put("organizationInfo", getOrganizationInfo(organizationId));
                jedis.setnx(RedisPrefixConstant.ORGANIZATION_ENVIRONMENT.getPrefix() + organizationId,
                    JSON.toJSONString(map, SerializerFeature.WRITE_MAP_NULL_FEATURES));
            }
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return map;
    }

    @Override
    public Map<String, Object> getDepartmentEnvironment(Long departmentId) throws Exception {
        return null;
    }

    @Override
    @LogOperate(value = "根据帮区id获取语境",type = LogOperateType.QUERY)
    public Map<String, Object> getBandEnvironment(Long bandId, Long realObjId) throws Exception {

        HashMap<String, Object> map = null;
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedis();
            if (jedis.exists(RedisPrefixConstant.BAND_ENVIRONMENT.getPrefix() + realObjId)) {
                map = JSON.parseObject(jedis.get(RedisPrefixConstant.BAND_ENVIRONMENT.getPrefix() + realObjId),
                    new TypeReference<HashMap<String, Object>>() {});
                return map;
            } else {
                map = new HashMap<>();
                map.put("bandRoles", getRoles(bandId));
                map.put("bandUsers", getUsersBand(bandId));
                map.put("bandChatrooms", getChatrooms(bandId));
                map.put("bandDocuments", getDocuments(bandId));
                JSONObject bandInfo = coreRemoteService.getBandInfo(bandId);
                if (!ObjectUtils.isEmpty(new JSONObject[]{bandInfo})){
                    map.put("bandInfo", bandInfo.get("rows"));
                }
                map.put("bandTools", getTools(bandId));
                jedis.setnx(RedisPrefixConstant.BAND_ENVIRONMENT.getPrefix() + realObjId,
                    JSON.toJSONString(map, SerializerFeature.WRITE_MAP_NULL_FEATURES));
            }
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return map;
    }

    @Override
    public Map<String, Object> getScriptEnvironment(Long scriptId) throws Exception {
        return null;
    }

    /*
     * 获取机构语境相关内容
     * */
    private List<Map<String, Object>> getDepartments(Long organizationId) throws Exception {

        JSONObject departments = coreRemoteService.getDepartmentByOrganizationId(organizationId);
        JSONArray departmentsArray = departments.getJSONArray("rows");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < departmentsArray.size(); i++) {
            JSONObject jsonObject = departmentsArray.getJSONObject(i);
            Map<String, Object> temp = new HashMap<String, Object>();
            temp.put("departmentName", jsonObject.get("departmentName"));
            temp.put("departmentId", jsonObject.get("departmentID"));
            list.add(temp);
        }
        return list;
    }

    private List<Map<String, Object>> getPositions(Long organizationId) throws Exception {

        JSONObject positions = coreRemoteService.getPositionByOrganizationId(organizationId);
        JSONArray positionArray = positions.getJSONArray("rows");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < positionArray.size(); i++) {
            JSONObject jsonObject = positionArray.getJSONObject(i);
            Map<String, Object> temp = new HashMap<String, Object>();
            temp.put("positionName", jsonObject.get("name"));
            temp.put("positionId", jsonObject.get("positionID"));
            list.add(temp);
        }

        return list;
    }

    private List<Map<String, Object>> getUsers(Long organizationId) throws Exception {

        JSONObject jsonObject = coreRemoteService.getMembersByOrganizationId(organizationId);
        JSONArray members = jsonObject.getJSONArray("rows");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < members.size(); i++) {
            Map<String, Object> temp = new HashMap<String, Object>();
            temp.put("userName", members.getJSONObject(i).get("userName"));
            temp.put("userAccount", members.getJSONObject(i).get("userAccount"));
            temp.put("userId", members.getJSONObject(i).get("userID"));
            list.add(temp);
        }
        return list;
    }

    private Map<String, Object> getOrganizationInfo(Long organizationId) throws Exception {

        HashMap<String, Object> map = null;
        JSONObject organizationInfo = coreRemoteService.getOrganizationInfo(organizationId);
        JSONArray rows = organizationInfo.getJSONArray("rows");
        map = new HashMap<>();
        map.put("organizationId", organizationId);
        map.put("organizationName", rows.getJSONObject(0).get("organizationName"));
        map.put("address", rows.getJSONObject(0).get("address"));
        map.put("introduction", rows.getJSONObject(0).get("introduction"));
        map.put("mainBandID", rows.getJSONObject(0).get("mainBandID"));
        return map;
    }

    /*
     * 获取帮区语境相关内容
     * */
    private List<Map<String, Object>> getRoles(Long bandId) throws Exception {

        JSONObject rolesInBand = coreRemoteService.getRolesInBand(bandId);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        JSONArray rows = rolesInBand.getJSONArray("rows");
        for (int i = 0; i < rows.size(); i++) {
            Map<String, Object> temp = new HashMap<String, Object>();
            temp.put("roleName", rows.getJSONObject(i).get("roleName"));
            temp.put("roleId", rows.getJSONObject(i).get("roleID"));
            list.add(temp);
        }
        return list;
    }

    private List<Map<String, Object>> getUsersBand(Long bandId) throws Exception {

        JSONObject membersInBand = coreRemoteService.getUsersInBand(bandId);
        JSONArray members = membersInBand.getJSONArray("rows");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < members.size(); i++) {
            Map<String, Object> temp = new HashMap<String, Object>();
            temp.put("userName", members.getJSONObject(i).get("userName"));
            temp.put("userAccount", members.getJSONObject(i).get("userAccount"));
            temp.put("userId", members.getJSONObject(i).get("userID"));
            list.add(temp);
        }
        return list;
    }

    private List<Map<String, Object>> getChatrooms(Long bandId) throws Exception {

        JSONObject chatroomsInBand = coreRemoteService.getChatroomsInBand(bandId);
        JSONArray rows = chatroomsInBand.getJSONArray("rows");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < rows.size(); i++) {
            Map<String, Object> temp = new HashMap<String, Object>();
            temp.put("chatroomName", rows.getJSONObject(i).get("chatroomName"));
            temp.put("chatroomDescription", rows.getJSONObject(i).get("description"));
            temp.put("chatroomUserNumber", rows.getJSONObject(i).get("roomUserNumber"));
            temp.put("chatroomId", rows.getJSONObject(i).get("chatroomID"));
            list.add(temp);
        }
        return list;
    }

    private List<Map<String, Object>> getDocuments(Long bandId) throws Exception {

        JSONObject documentsInBand = coreRemoteService.getDocumentsInBand(bandId);
        JSONArray rows = documentsInBand.getJSONArray("rows");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < rows.size(); i++) {
            Map<String, Object> temp = new HashMap<String, Object>();
            temp.put("documentName", rows.getJSONObject(i).get("name"));
            temp.put("documentId", rows.getJSONObject(i).get("documentID"));
            temp.put("documentExtension", rows.getJSONObject(i).get("extension"));
            temp.put("documentDescription", rows.getJSONObject(i).get("description"));
            list.add(temp);
        }

        return list;
    }

    private List<Map<String, Object>> getTools(Long bandId) throws Exception {

        JSONObject toolsInBand = coreRemoteService.getToolsInBand(bandId);
        JSONArray rows = toolsInBand.getJSONArray("rows");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < rows.size(); i++) {
            Map<String, Object> temp = new HashMap<String, Object>();
            temp.put("toolId", rows.getJSONObject(i).get("toolShopToolID"));
            temp.put("toolName", rows.getJSONObject(i).get("name"));
            list.add(temp);
        }

        return list;
    }

}
