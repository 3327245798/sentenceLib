package com.blp.sentenceLib.remote.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fy.basejar.remote.service.BaseRemoteService;
import com.fy.basejar.remote.util.RemoteRequestUtils;
import com.blp.sentenceLib.remote.service.IMessageToolRemoteService;
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
public class MessageToolRemoteServiceImpl extends BaseRemoteService.ToolRemoteServiceBase
    implements IMessageToolRemoteService {

    @Override
    public JSONObject sendMessage(Message message) throws Exception {
        final Map<String, Object> params = new HashMap<String, Object>();
        // todo 问题
        RemoteRequestUtils.addNotNullableParam(params, "gid", message.getChatroomID());
        RemoteRequestUtils.addNotNullableParam(params, "action", "sendMessages");
        RemoteRequestUtils.addNotNullableParam(params, "message", JSONObject.toJSONString(message));
        return CONNECTOR.doPost(PROCESS, JSON_RESULT, null, params);
    }

    @Override
    protected Long getToolID() throws Exception {
        // return (Long) ActionTool.getGlobal(GLB_KEY_MESSAGE_TOOL_ID);
        return 2217867L;
    }
}