package com.blp.sentenceLib.dao;

import com.blp.sentenceLib.entity.UserTalkInfo;
import com.fy.toolhelper.db.IBaseDao;

import java.sql.Connection;
import java.util.List;

/**
 * (Comment)表数据库访问层
 *
 * @author makewangzhe
 * @since 2022-07-27 10:25:24
 */
public interface UserTalkInfoDao extends IBaseDao<UserTalkInfo> {
    //获取用户信息
    UserTalkInfo getUserTalkInfoByUserAccount(Connection connection, Long userAccount) throws Exception;


}