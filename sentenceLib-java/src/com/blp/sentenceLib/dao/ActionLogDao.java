package com.blp.sentenceLib.dao;


import com.blp.sentenceLib.entity.ActionLog;
import com.fy.toolhelper.db.IBaseDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * (ActionLog)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-28 17:01:39
 */
public interface ActionLogDao extends IBaseDao<ActionLog> {

    List<ActionLog> pageConditionActionLogList(Connection connection, ActionLog actionLogQuery, int pageSize, int currentPage) throws SQLException;

    int pageConditionActionLogListTotal(Connection connection, ActionLog actionLogQuery) throws SQLException;
}

