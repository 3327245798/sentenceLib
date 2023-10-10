package com.blp.sentenceLib.dao.impl;

import com.blp.sentenceLib.dao.UserTalkInfoDao;

import com.blp.sentenceLib.entity.UserTalkInfo;
import com.fy.toolhelper.db.BaseDaoImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * (PostDaoImpl)表数据库访问层的实现
 *
 * @author makewangzhe
 * @since 2022-07-27 20:34:27
 */
@Repository
public class UserTalkInfoDaompl extends BaseDaoImpl<UserTalkInfo> implements UserTalkInfoDao {

    public UserTalkInfoDaompl() throws Exception {}


    @Override
    public UserTalkInfo getUserTalkInfoByUserAccount(Connection connection, Long userAccount) throws Exception {
        String sql="SELECT * FROM blp_talk_userinfo where user_account=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = null;
        UserTalkInfo userTalkInfoEntity = null;
        pstm.setLong(1, userAccount);
         rs = pstm.executeQuery();

        // 这里只有1条或0条两种情况
        if (rs.next()){
            userTalkInfoEntity= getUserTalkInfoEntity(rs);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return userTalkInfoEntity;
    }
    private UserTalkInfo getUserTalkInfoEntity(ResultSet rs) throws SQLException {
        UserTalkInfo userTalkInfo=new UserTalkInfo();
        userTalkInfo.setId(rs.getLong("id"));
        userTalkInfo.setUserAccount(rs.getLong("user_account"));
        userTalkInfo.setUserName(rs.getString("user_name"));
        userTalkInfo.setUserAvatar(rs.getBytes("user_avatar"));
        return userTalkInfo;


    }
}

