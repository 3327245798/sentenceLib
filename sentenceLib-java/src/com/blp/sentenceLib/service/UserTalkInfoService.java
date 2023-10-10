package com.blp.sentenceLib.service;

import com.blp.sentenceLib.entity.UserTalkInfo;

import java.util.List;

/**
 * (UserTalkInfo)表服务接口
 *
 * @author makejava
 * @since 2022-02-28 17:00:42
 */
public interface UserTalkInfoService {

    UserTalkInfo getUserTalkInfoByUserAccountId(Long userAccount) throws Exception;

    void insertUserTalkInfo(UserTalkInfo userTalkInfo) throws Exception;

    int updateUserTalkInfo(UserTalkInfo userTalkInfo) throws Exception;

    int deleteUserTalkInfo(UserTalkInfo userTalkInfo) throws Exception;




}
