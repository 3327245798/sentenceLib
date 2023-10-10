package com.blp.sentenceLib.remote.service;

import com.alibaba.fastjson.JSONObject;

/**
 * 消息工具远程请求服务
 * @author linjie
 * @since 4.5.0
 */
public interface IMessageToolRemoteService {

    /**
     *
     * @param message
     * @return
     * @throws Exception
     */
    JSONObject sendMessage(Message message) throws Exception;

    /**
     * 使用ActionTool的getGlobal获取消息工具的ID的值时使用的Key
     *
     * @author linjie
     * @since 1.0.0
     */
    public static final String GLB_KEY_MESSAGE_TOOL_ID = "GLB_KEY_MESSAGE_TOOL_ID";
}