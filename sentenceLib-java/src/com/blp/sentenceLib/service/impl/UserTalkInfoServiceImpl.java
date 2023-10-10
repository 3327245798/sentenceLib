package com.blp.sentenceLib.service.impl;

import com.blp.sentenceLib.dao.CommentDao;
import com.blp.sentenceLib.dao.UserTalkInfoDao;
import com.blp.sentenceLib.entity.Comment;
import com.blp.sentenceLib.entity.UserTalkInfo;
import com.blp.sentenceLib.service.CommentService;
import com.blp.sentenceLib.service.UserTalkInfoService;
import com.fy.basejar.tool.ActionToolBase;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

/**
 * (CommentServiceImpl)表服务实现类
 *
 * @author makewz
 * @since 2022-10-17 17:00:42
 */
@Service
@NoArgsConstructor
public class UserTalkInfoServiceImpl implements UserTalkInfoService {

    @Autowired
    UserTalkInfoDao userTalkInfoDao;

    @Override
    public UserTalkInfo getUserTalkInfoByUserAccountId(Long userAccount) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        return userTalkInfoDao.getUserTalkInfoByUserAccount(connection,userAccount);
    }

    @Override
    public void insertUserTalkInfo(UserTalkInfo userTalkInfo) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        userTalkInfoDao.save(connection, userTalkInfo);
    }

    @Override
    public int updateUserTalkInfo(UserTalkInfo userTalkInfo) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        int i=(int)userTalkInfoDao.updateIgnoreNull(connection,userTalkInfo);
        return i;
    }

    @Override
    public int deleteUserTalkInfo(UserTalkInfo userTalkInfo) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        int i=(int)userTalkInfoDao.delete(connection,userTalkInfo);
        return i;

    }
}
