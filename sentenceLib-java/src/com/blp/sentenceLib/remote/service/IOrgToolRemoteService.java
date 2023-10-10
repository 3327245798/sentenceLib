package com.blp.sentenceLib.remote.service;

import com.alibaba.fastjson.JSONObject;

/**
 * 消息工具远程请求服务
 * @author linjie
 * @since 4.5.0
 */
public interface IOrgToolRemoteService {

    /**
     *
     * @param message
     * @return
     * @throws Exception
     */
    JSONObject getMyOrganList() throws Exception;

}