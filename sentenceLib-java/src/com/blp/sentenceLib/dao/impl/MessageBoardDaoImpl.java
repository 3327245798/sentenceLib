package com.blp.sentenceLib.dao.impl;

import com.blp.sentenceLib.dao.MessageBoardDao;
import com.blp.sentenceLib.entity.MessageBoard;
import com.fy.toolhelper.db.BaseDaoImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MessageBoardDaoImpl extends BaseDaoImpl<MessageBoard> implements MessageBoardDao {
    public MessageBoardDaoImpl() throws Exception {
    }


    @Override
    public List<MessageBoard> pageConditionMessageBoardList(Connection connection, MessageBoard messageBoardQuery) throws SQLException{
        StringBuilder sql = new StringBuilder("SELECT * FROM blp_message_board WHERE 1 = 1 ");


        if (!StringUtils.isEmpty(messageBoardQuery.getApplicationCaseId())) {
            sql.append(" and application_case_id = ").append(messageBoardQuery.getApplicationCaseId());
        }
        if (!StringUtils.isEmpty(messageBoardQuery.getIsDeleted())) {
            sql.append(" and is_deleted = ").append(messageBoardQuery.getIsDeleted());
        }
        if (!StringUtils.isEmpty(messageBoardQuery.getUserId())) {
            sql.append(" and user_id = ").append(messageBoardQuery.getUserId());
        }
        if (!StringUtils.isEmpty(messageBoardQuery.getUserName())) {
            sql.append(" and user_name like '%").append(messageBoardQuery.getUserName()).append("%'");
        }

        PreparedStatement pstm = connection.prepareStatement(sql.toString());

        ResultSet rs = pstm.executeQuery();
        List<MessageBoard> messageBoardList = new ArrayList<>();
        while (rs.next()) {
            MessageBoard messageBoard = getMessageBoardEntity(rs);
            messageBoardList.add(messageBoard);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return messageBoardList;
    }

    @Override
    public List<MessageBoard> getMessageBoardByApplicationCaseId(Connection connection, Long applicationCaseId) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM blp_message_board WHERE application_case_id = ?");

        PreparedStatement pstm = connection.prepareStatement(sql.toString());
        pstm.setLong(1, applicationCaseId);

        ResultSet rs = pstm.executeQuery();
        List<MessageBoard> messageBoardList = new ArrayList<>();
        while (rs.next()) {
            MessageBoard messageBoard = getMessageBoardEntity(rs);
            messageBoardList.add(messageBoard);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return messageBoardList;
    }

    private MessageBoard getMessageBoardEntity(ResultSet rs) throws SQLException {
        MessageBoard messageBoard = new MessageBoard();
        messageBoard.setId(rs.getLong("id"));
        messageBoard.setApplicationCaseId(rs.getLong("application_case_id"));
        messageBoard.setParentId(rs.getLong("parent_id"));
        messageBoard.setContent(rs.getString("content"));
        messageBoard.setIsDeleted(rs.getString("is_deleted"));
        messageBoard.setUserId(rs.getLong("user_id"));
        messageBoard.setUserName(rs.getString("user_name"));
        messageBoard.setTime(rs.getTimestamp("time"));
        return messageBoard;
    }


}