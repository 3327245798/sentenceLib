package com.blp.sentenceLib.remote.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fy.basejar.remote.parameter.WtbCoreParameter;
import com.fy.basejar.remote.service.BaseRemoteService;
import com.fy.basejar.remote.util.RemoteRequestUtils;
import com.fy.basejar.tool.ActionToolBase;
import com.blp.sentenceLib.remote.service.CoreRemoteService;
import com.fy.toolhelper.tool.ActionTool;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据远程请求的项目区分RemoteService
 * 默认的连接器基于HttpClient实现的, 并定义一套接口, 开发者继承{@link /BaseRemoteServiceImpl来获取CONNECTOR属性(静态变量).
 * 一般需要提供两个处理器: {@link IHttpProcessResolver}和 {@link IHttpResultResolver}以处理各个请求过程中通用的代码.
 * {@link BaseRemoteService}内置了三个写好的请求模板, 开发者选用即可:
 * {@link WtbCoreRemoteServiceBase} 请求吾托帮核心的处理, 注意: 吾托帮核心的通用查询参数封装在{@link WtbCoreParameter}中了, 直接使用即可.
 * {@link WtbStoreRemoteServiceBase} 请求吾托帮商城的处理, 注意: 吾托帮商城的通用查询参数封装在{@link WtbStoreParameter}中了, 直接使用即可.
 * {@link ToolRemoteServiceBase} 请求运行其它工具的处理
 * 如果有其它请求的需要, 请继承{@link BaseRemoteService}重写相应回调即可.
 */
@Service
@NoArgsConstructor
public class CoreRemoteServiceImpl extends BaseRemoteService.WtbCoreRemoteServiceBase implements CoreRemoteService {

    Map<String, Object> params = null;


    @Override
    public Map<String, Object> getBandByBandObjIds(List<Long> bandObjIds) throws Exception {
        params = RemoteRequestUtils.getNotNullableQueryParams(new WtbCoreParameter());
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < bandObjIds.size(); i++) {
            if (i == bandObjIds.size() - 1) {
                sb.append(bandObjIds.get(i));
            } else {
                sb.append(bandObjIds.get(i) + ",");
            }
        }
        sb.append("]");
        RemoteRequestUtils.addNotNullableParam(params, "accessToken", ActionTool.getTempory(ActionToolBase.TMP_KEY_ACCESS_TOKEN));
        RemoteRequestUtils.addNotNullableParam(params, "aid", "21419389378723787447");
        RemoteRequestUtils.addNotNullableParam(params, "objIDs", sb.toString());
        //	String url="/core/v4/user/me/objs“; 参数为 realObjID
        //	String url="/core/v4/user/me/obj”;  参数为 objID
        String url = "/core/v4/user/me/objs";
        JSONObject jsonObject = CONNECTOR.doGet(PROCESS, JSON_RESULT, url, params);
        JSONArray rows = jsonObject.getJSONArray("rows");
        return rows.size() == 0 ? null : rows.getJSONObject(0);
    }

    @Override
    public JSONObject getBandsByBandObjId(Long bandID) throws Exception {
        params = RemoteRequestUtils.getNotNullableQueryParams(new WtbCoreParameter());
        RemoteRequestUtils.addNotNullableParam(params, "accessToken", ActionTool.getTempory(ActionToolBase.TMP_KEY_ACCESS_TOKEN));
        RemoteRequestUtils.addNotNullableParam(params, "aid", "21419389378723787447");
        RemoteRequestUtils.addNotNullableParam(params, "gid", bandID);
        String url = "/core/v4/band/{?}/band";
        JSONObject jsonObject = CONNECTOR.doGet(PROCESS, JSON_RESULT, url, params,bandID.toString());
        return jsonObject;
    }
/**wz根据bandObjId获取band信息，为了获取band视图id,*/
    @Override
    public Map<String, Object> getBandByBandObjId(Long ObjId) {
        JSONObject jsonObject = null;
        JSONArray rows = null;
        try {
            params = RemoteRequestUtils.getNotNullableQueryParams(new WtbCoreParameter());
            RemoteRequestUtils.addNotNullableParam(params, "gid", ObjId);
            RemoteRequestUtils.addNotNullableParam(params, "aid", "21419389378723787447");


            RemoteRequestUtils.addNotNullableParam(params, "objID", ObjId);
            //	String url="/core/v4/user/me/objs“; 参数为 realObjID
            //	String url="/core/v4/user/me/obj”;  参数为 objID
            String url = "/core/v4/user/me/obj";
            jsonObject = CONNECTOR.doGet(PROCESS, JSON_RESULT, url, params);
            rows = jsonObject.getJSONArray("rows");
        } catch (Exception e) {
            return null;
        }
        return rows.size() == 0 ? null : rows.getJSONObject(0);
    }
    /**获取所在机构信息，返回结果是当前用户视图下的所有机构wz*/
    @Override
    public JSONObject getOrganizationInfoByMe() throws Exception{
        params = RemoteRequestUtils.getNotNullableQueryParams(new WtbCoreParameter());
        RemoteRequestUtils.addNotNullableParam(params, "gid", 1);
        String url = "/core/v4/organization/me";
        return CONNECTOR.doGet(PROCESS, JSON_RESULT, url, params);

}
    @Override
    public JSONObject getRolesInBand(Long bandId) throws Exception {
        params = RemoteRequestUtils.getNotNullableQueryParams(new WtbCoreParameter());
        Long gid = ActionTool.getCurrentContext().getGid();
        RemoteRequestUtils.addNotNullableParam(params, "gid", gid);
        String url = "/core/v4/band/{?}/role";
        return CONNECTOR.doGet(PROCESS, JSON_RESULT, url, params, bandId.toString());
    }
//获取当前用户的角色
    @Override
    public JSONObject getUserRolesInBand(Long bandId, Long userID) throws Exception {
        params = RemoteRequestUtils.getNotNullableQueryParams(new WtbCoreParameter());
        Long gid = ActionTool.getCurrentContext().getGid();
        RemoteRequestUtils.addNotNullableParam(params, "gid", gid);
        String url = "/core/v4/bandRole/band/{?}/user/{?}/bandRoles";
        return CONNECTOR.doGet(PROCESS, JSON_RESULT, url, params, bandId.toString(), userID.toString());
    }


    @Override
    public JSONObject getUsersInBand(Long bandId) throws Exception {
        params = RemoteRequestUtils.getNotNullableQueryParams(new WtbCoreParameter());
        Long gid = ActionTool.getCurrentContext().getGid();
        RemoteRequestUtils.addNotNullableParam(params, "gid", gid);
        String url = "/core/v4/band/{?}/user";
        return CONNECTOR.doGet(PROCESS, JSON_RESULT, url, params, bandId.toString());
    }

    @Override
    public JSONObject getChatroomsInBand(Long bandId) throws Exception {
        params = RemoteRequestUtils.getNotNullableQueryParams(new WtbCoreParameter());
        Long gid = ActionTool.getCurrentContext().getGid();
        RemoteRequestUtils.addNotNullableParam(params, "gid", gid);
        String url = "/core/v4/band/{?}/chatroom";
        return CONNECTOR.doGet(PROCESS, JSON_RESULT, url, params, bandId.toString());
    }

    @Override
    public JSONObject getDocumentsInBand(Long bandId) throws Exception {
        params = RemoteRequestUtils.getNotNullableQueryParams(new WtbCoreParameter());
        Long gid = ActionTool.getCurrentContext().getGid();
        RemoteRequestUtils.addNotNullableParam(params, "gid", gid);
        String url = "/core/v4/band/{?}/document";
        return CONNECTOR.doGet(PROCESS, JSON_RESULT, url, params, bandId.toString());
    }

    @Override
    public JSONObject getToolsInBand(Long bandId) throws Exception {
        params = RemoteRequestUtils.getNotNullableQueryParams(new WtbCoreParameter());
        Long gid = ActionTool.getCurrentContext().getGid();
        RemoteRequestUtils.addNotNullableParam(params, "gid", gid);
        String url = "/core/v4/band/{?}/tool";
        return CONNECTOR.doGet(PROCESS, JSON_RESULT, url, params, bandId.toString());
    }

    @Override
    public JSONObject getBandInfo(Long bandId) throws Exception {
        params = RemoteRequestUtils.getNotNullableQueryParams(new WtbCoreParameter());
        Long gid = ActionTool.getCurrentContext().getGid();
        RemoteRequestUtils.addNotNullableParam(params, "gid", gid);

        String url = "/core/v4/band/{?}";
        return CONNECTOR.doGet(PROCESS, JSON_RESULT, url, params, bandId.toString());
    }

    @Override
    public JSONObject getOrganizationInfo(Long organizationId) throws Exception {
        params = RemoteRequestUtils.getNotNullableQueryParams(new WtbCoreParameter());
        Long gid = ActionTool.getCurrentContext().getGid();
        RemoteRequestUtils.addNotNullableParam(params, "gid", gid);
        String url = "/core/v4/organization/{?}";
        return CONNECTOR.doGet(PROCESS, JSON_RESULT, url, params, organizationId.toString());
    }

    @Override
    public JSONObject getDepartmentByOrganizationId(Long organizationId) throws Exception {
        params = RemoteRequestUtils.getNotNullableQueryParams(new WtbCoreParameter());
        Long gid = ActionTool.getCurrentContext().getGid();
        RemoteRequestUtils.addNotNullableParam(params, "gid", gid);
        String url = "/core/v4/organization/{?}/department";
        return CONNECTOR.doGet(PROCESS, JSON_RESULT, url, params, organizationId.toString());
    }

    @Override
    public JSONObject getPositionByOrganizationId(Long organizationId) throws Exception {
        params = RemoteRequestUtils.getNotNullableQueryParams(new WtbCoreParameter());
        Long gid = ActionTool.getCurrentContext().getGid();
        RemoteRequestUtils.addNotNullableParam(params, "gid", gid);
        String url = "/core/v4/organization/{?}/position";
        return CONNECTOR.doGet(PROCESS, JSON_RESULT, url, params, organizationId.toString());
    }

    @Override
    public JSONObject getMembersByOrganizationId(Long organizationId) throws Exception {
        HashMap<String, Object> query = new HashMap<>();
        params = RemoteRequestUtils.getNotNullableQueryParams(new WtbCoreParameter());
        Long gid = ActionTool.getCurrentContext().getGid();
        RemoteRequestUtils.addNotNullableParam(params, "gid", gid);
        String url = "/core/v4/organization/{?}/user";
        return CONNECTOR.doGet(PROCESS, JSON_RESULT, url, params, organizationId.toString());
    }

    // 根据机构Id获取该机构下的所有帮区
    @Override
    public JSONObject getBandsByOrganizationId(Long organizationId) throws Exception {
        params = RemoteRequestUtils.getNotNullableQueryParams(new WtbCoreParameter());
        Long gid = ActionTool.getCurrentContext().getGid();
        RemoteRequestUtils.addNotNullableParam(params, "gid", gid);
        RemoteRequestUtils.addNotNullableParam(params, "aid", "21419389378723787447");

        String url = "/core/v4/organization/{?}/band";
        return CONNECTOR.doGet(PROCESS, JSON_RESULT, url, params, organizationId.toString());
    }

    @Override
    public JSONObject getBandsPath(Long bandId) {
        JSONObject jsonObject = null;
        try {
            params = RemoteRequestUtils.getNotNullableQueryParams(new WtbCoreParameter());
            Long gid = ActionTool.getCurrentContext().getGid();
            RemoteRequestUtils.addNotNullableParam(params, "gid", gid);
            String url = "/core/v4/band/{?}/bandPath";
            jsonObject = CONNECTOR.doGet(PROCESS, JSON_RESULT, url, params, bandId.toString());
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
        return jsonObject;
    }

    @Override
    public JSONObject getUsersInDepartment(Long departmentId) throws Exception {
        params = RemoteRequestUtils.getNotNullableQueryParams(new WtbCoreParameter());
        Long gid = ActionTool.getCurrentContext().getGid();
        RemoteRequestUtils.addNotNullableParam(params, "gid", gid);
        String url = "/core/v4/department/{?}/user";
        return CONNECTOR.doGet(PROCESS, JSON_RESULT, url, params, departmentId.toString());
    }

    @Override
    public JSONObject getPositionsInDepartment(Long departmentId) throws Exception {
        params = RemoteRequestUtils.getNotNullableQueryParams(new WtbCoreParameter());
        Long gid = ActionTool.getCurrentContext().getGid();
        RemoteRequestUtils.addNotNullableParam(params, "gid", gid);
        String url = "/core/v4/department/{?}/position";
        return CONNECTOR.doGet(PROCESS, JSON_RESULT, url, params, departmentId.toString());
    }

    @Override
    public JSONObject getUserByUserName(String userName) throws Exception {
        params = RemoteRequestUtils.getNotNullableQueryParams(new WtbCoreParameter());
        String query = "{\"userName\":\"%s\",\"userStatus\": \"NORMAL\"}";
        query = String.format(query, userName);
        RemoteRequestUtils.addNotNullableParam(params, "query", query);
        Long gid = ActionTool.getCurrentContext().getGid();
        RemoteRequestUtils.addNotNullableParam(params, "gid", gid);
        String url = "/core/v4/user";
        return CONNECTOR.doGet(PROCESS, JSON_RESULT, url, params);
    }

    @Override
    public JSONObject getBandByBandName(String bandName) throws Exception {

        params = RemoteRequestUtils.getNotNullableQueryParams(new WtbCoreParameter());
        Long gid = ActionTool.getCurrentContext().getGid();

        String query = "{\"name\":\"%s\"}";
        query = String.format(query, bandName);

        RemoteRequestUtils.addNotNullableParam(params, "query", query);
        RemoteRequestUtils.addNotNullableParam(params, "gid", gid);

        String url = "/core/v4/band";
        return CONNECTOR.doGet(PROCESS, JSON_RESULT, url, params);
    }

    @Override
    public JSONObject getChatroomByChatroomName(String chatroomName, String bandId) throws Exception {
        params = RemoteRequestUtils.getNotNullableQueryParams(new WtbCoreParameter());

        // aid在config.xml中配置了
        Long gid = ActionTool.getCurrentContext().getGid();

        String query = "{\"name\":\"%s\"}";
        query = String.format(query, chatroomName);

        RemoteRequestUtils.addNotNullableParam(params, "query", query);
        RemoteRequestUtils.addNotNullableParam(params, "gid", gid);

        String url = "/core/v4/band/{?}/chatroom";
        return CONNECTOR.doGet(PROCESS, JSON_RESULT, url, params, bandId);
    }

    @Override
    public JSONObject getToolByToolName(String toolName, String bandId) throws Exception {
        params = RemoteRequestUtils.getNotNullableQueryParams(new WtbCoreParameter());

        // aid在config.xml中配置了
        // todo 获取运行帮区id
        // solved 方法参数上bandID
        bandId = "".equals(bandId) ? String.valueOf(ActionTool.getCurrentContext().getBandID()) : bandId;
        Long gid = ActionTool.getCurrentContext().getGid();

        String query = "{\"name\":\"%s\",\"bandID\":\"%s\"}";
        query = String.format(query, toolName, bandId);

        RemoteRequestUtils.addNotNullableParam(params, "query", query);
        RemoteRequestUtils.addNotNullableParam(params, "gid", gid);
        RemoteRequestUtils.addNotNullableParam(params, "aid", "21419389378723787447");

        String url = "/core/v4/tool";
        return CONNECTOR.doGet(PROCESS, JSON_RESULT, url, params);
    }

    @Override
    public JSONObject getUserByUserId(Long userId) throws Exception {
        params = RemoteRequestUtils.getNotNullableQueryParams(new WtbCoreParameter());
        Long gid = ActionTool.getCurrentContext().getGid();
        RemoteRequestUtils.addNotNullableParam(params, "gid", gid);
        String url = "/core/v4/user/{?}";
        return CONNECTOR.doGet(PROCESS, JSON_RESULT, url, params, userId.toString());
    }

    @Override
    public JSONObject getBandRolesByRoleName(String bandId, String roleName) throws Exception {
        params = RemoteRequestUtils.getNotNullableQueryParams(new WtbCoreParameter());
        Long gid = ActionTool.getCurrentContext().getGid();
        RemoteRequestUtils.addNotNullableParam(params, "gid", gid);
        RemoteRequestUtils.addNotNullableParam(params, "roleName", roleName);
        String url = "/core/v4/band/{?}/role";
        return CONNECTOR.doGet(PROCESS, JSON_RESULT, url, params, bandId);
    }

}
