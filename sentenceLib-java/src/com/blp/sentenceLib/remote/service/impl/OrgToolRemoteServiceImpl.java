package com.blp.sentenceLib.remote.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fy.basejar.remote.service.BaseRemoteService;
import com.fy.basejar.remote.util.RemoteRequestUtils;
import com.blp.sentenceLib.remote.service.IMessageToolRemoteService;
import com.blp.sentenceLib.remote.service.IOrgToolRemoteService;
import com.blp.sentenceLib.remote.service.Message;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息工具远程服务实现
 * 
 * @author linjie
 * @since 4.5.0
 */
@Service
@NoArgsConstructor
public class OrgToolRemoteServiceImpl extends BaseRemoteService.ToolRemoteServiceBase
    implements IOrgToolRemoteService {
    /**获取所有的机构信息*/
    @Override
    public JSONObject getMyOrganList() throws Exception {
        final Map<String, Object> params = new HashMap<String, Object>();
        RemoteRequestUtils.addNotNullableParam(params, "gid", 1);
        RemoteRequestUtils.addNotNullableParam(params, "param", "%7B%22toolAction%22:%22getMyOrganList%22%7D");
        return CONNECTOR.doPost(PROCESS, JSON_RESULT, null, params);
    }

    @Override
    protected Long getToolID() throws Exception {
        return 2920472L;
    }
}