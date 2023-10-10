package com.blp.sentenceLib.remote.service;

import com.alibaba.fastjson.JSONObject;

/**
 * 调用系统工具 发布service
 */

public interface DispatcherService {
    JSONObject createShareInCore(String bandId,String roleName, String title, String description) throws Exception;
}
