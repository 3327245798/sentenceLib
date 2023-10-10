package com.blp.sentenceLib.remote.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blp.sentenceLib.remote.service.CoreRemoteService;
import com.blp.sentenceLib.remote.service.DispatcherService;
import com.blp.sentenceLib.util.BandCore;
import com.fy.toolhelper.tool.ActionTool;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.*;

/**
 * 调用系统工具 发布service
 */
@Service
@NoArgsConstructor
public class DispatcherServiceImpl implements DispatcherService {

    @Autowired
    CoreRemoteService coreRemoteService;

    /**
     * 请求发布工具，获取发布信息
     */
    @Override
    public JSONObject createShareInCore(String bandId,String roleName, String title, String description) throws Exception {

        JSONObject bandRoles = coreRemoteService.getBandRolesByRoleName(bandId, roleName);
        JSONObject tools = coreRemoteService.getToolByToolName("帮语平台", bandId);

        String roleId = "";
        for (Object item : ((JSONArray) bandRoles.get("rows"))) {
            if (((JSONObject) item).get("roleName").equals(roleName)) {
                roleId = ((JSONObject) item).get("roleID").toString();
                break;
            }
        }
//        if (StringUtils.isEmpty(roleId)){
//            throw new BLPException("roleId获取失败");
//        }


        
        JSONArray rows = tools.getJSONArray("rows");
        String objId = (String) rows.getJSONObject(0).get("objID");
        String organizationId = (String) rows.getJSONObject(0).get("organizationID");
        String realObjId = (String) rows.getJSONObject(0).get("realObjID");


        String userId = String.valueOf(ActionTool.getCurrentContext().getUserID());


        HashMap<String, String> params = new HashMap<>();

        Map<String,String> dispatch = new HashMap<>();
        dispatch.put("dispatchName", title);
        dispatch.put("description", description);
        dispatch.put("fromOrganizationID", organizationId);
        dispatch.put("toOrganizationID", organizationId);
        String dispatchInfo = JSON.toJSONString(dispatch);


        Map<String,String> bandRole = new HashMap<>();
        bandRole.put("bandID", bandId);
        bandRole.put("roleID", roleId);
        bandRole.put("roleName",roleName);
        String bandRoleInfo = JSON.toJSONString(bandRole);

        List<Map<String,Object>> dispatchItems = new ArrayList<>();
        Map<String, Object> dispatchItem = new HashMap<>();
        dispatchItem.put("objName", "普通剧本分享工具");
        dispatchItem.put("objID", objId);
        dispatchItem.put("realObjID", realObjId);
        dispatchItem.put("objType", "TOOL");
        List<String> permissionNames = Arrays.asList("browse", "run");
        dispatchItem.put("permissionNames", permissionNames);
        dispatchItems.add(dispatchItem);
        String dispatchItemsInfo = JSON.toJSONString(dispatchItems);

        List<String> targetUserIDs = new ArrayList<>();
        targetUserIDs.add(userId);
        String targetUserIDsInfo = JSON.toJSONString(targetUserIDs);

        params.put("bandID", bandId);
        params.put("dispatch", dispatchInfo);
        params.put("bandRole", bandRoleInfo);
        params.put("dispatchItems", dispatchItemsInfo);
        params.put("targetUserIDs", targetUserIDsInfo);



        String accessToken = ActionTool.getCurrentContext().getAccessToken();
        HashMap<String, String> organization = new HashMap<>();
        organization.put("organizationID", organizationId);
        String organizationInfo = JSON.toJSONString(organization);

        String url = "https://www.wetoband.com/tre//runSystemTool?" + "accessToken=" + accessToken
                + "&gid=" + bandId + "&toolID=4389165" + "&bandID=" + bandId + "&param=" + URLEncoder.encode(organizationInfo, "utf-8") + "&action=createDispatch"
                + "&returnType=value" + "&isPublic=true";


        String result = BandCore.doPostWithAccessToken(url, accessToken, params);
        JSONObject data = JSON.parseObject(result);

        return data;
    }


}
