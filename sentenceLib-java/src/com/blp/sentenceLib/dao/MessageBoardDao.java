package com.blp.sentenceLib.dao;

import com.blp.sentenceLib.entity.MessageBoard;
import com.fy.toolhelper.db.IBaseDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * (MessageBoard)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-28 17:02:44
 */
public interface MessageBoardDao extends IBaseDao<MessageBoard> {

    List<MessageBoard> pageConditionMessageBoardList(Connection connection, MessageBoard messageBoardQuery) throws SQLException;

    List<MessageBoard> getMessageBoardByApplicationCaseId(Connection connection, Long applicationCaseId) throws SQLException;
}

