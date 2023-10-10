package com.blp.sentenceLib.dao.impl;

import com.blp.sentenceLib.dao.ActionLogDao;
import com.blp.sentenceLib.entity.ActionLog;
import com.fy.toolhelper.db.BaseDaoImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ActionLogDaoImpl extends BaseDaoImpl<ActionLog> implements ActionLogDao {
    public ActionLogDaoImpl() throws Exception {}

    @Override
    public List<ActionLog> pageConditionActionLogList(Connection connection, ActionLog actionLogQuery, int pageSize,
        int currentPage) throws SQLException {
        int start = pageSize * (currentPage - 1);
        StringBuilder sql = new StringBuilder("SELECT * FROM blp_action_log WHERE 1 = 1 ");

        if (!StringUtils.isEmpty(actionLogQuery.getAction())) {
            sql.append(" and action = ").append(actionLogQuery.getAction());
        }
        if (!StringUtils.isEmpty(actionLogQuery.getType())) {
            sql.append(" and type = ").append(actionLogQuery.getType());
        }
        if (!StringUtils.isEmpty(actionLogQuery.getApplicationCaseName())) {
            sql.append(" and application_case_name = ").append(actionLogQuery.getApplicationCaseName());
        }
        if (!StringUtils.isEmpty(actionLogQuery.getUserId())) {
            sql.append(" and user_id = ").append(actionLogQuery.getUserId());
        }
        if (!StringUtils.isEmpty(actionLogQuery.getUserName())) {
            sql.append(" and user_name = ").append(actionLogQuery.getUserName());
        }

        sql.append(" limit ? , ?");

        PreparedStatement pstm = connection.prepareStatement(sql.toString());
        if (pageSize == -1 && currentPage == -1) {
            pstm.setInt(1, 0);
            int total = pageConditionActionLogListTotal(connection, actionLogQuery);
            pstm.setInt(2, total);
        } else {
            pstm.setInt(1, start);
            pstm.setInt(2, pageSize);
        }
        ResultSet rs = pstm.executeQuery();
        List<ActionLog> actionLogList = new ArrayList<>();
        while (rs.next()) {
            ActionLog actionLog = new ActionLog();
            ActionLog actionLogEntity = getActionLogEntity(rs);
            BeanUtils.copyProperties(actionLogEntity, actionLog);
            actionLogList.add(actionLog);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return actionLogList;
    }

    @Override
    public int pageConditionActionLogListTotal(Connection connection, ActionLog actionLogQuery) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM blp_action_log WHERE 1 = 1 ");

        if (!StringUtils.isEmpty(actionLogQuery.getAction())) {
            sql.append(" and action = ").append(actionLogQuery.getAction());
        }
        if (!StringUtils.isEmpty(actionLogQuery.getType())) {
            sql.append(" and type = ").append(actionLogQuery.getType());
        }
        if (!StringUtils.isEmpty(actionLogQuery.getApplicationCaseName())) {
            sql.append(" and application_case_name = ").append(actionLogQuery.getApplicationCaseName());
        }
        if (!StringUtils.isEmpty(actionLogQuery.getUserId())) {
            sql.append(" and user_id = ").append(actionLogQuery.getUserId());
        }
        if (!StringUtils.isEmpty(actionLogQuery.getUserName())) {
            sql.append(" and user_name = ").append(actionLogQuery.getUserName());
        }

        PreparedStatement pstm = connection.prepareStatement(sql.toString());
        ResultSet rs = pstm.executeQuery();
        int count = -1;
        while (rs.next()) {
            count = rs.getInt(1);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return count;
    }

    private ActionLog getActionLogEntity(ResultSet rs) throws SQLException {
        ActionLog actionLog = new ActionLog();
        actionLog.setId(rs.getLong("id"));
        actionLog.setAction(rs.getString("action"));
        actionLog.setContent(rs.getString("content"));
        actionLog.setType(rs.getString("type"));
        actionLog.setApplicationCaseName(rs.getString("application_case_name"));
        actionLog.setUserId(rs.getLong("user_id"));
        actionLog.setUserName(rs.getString("user_name"));
        return actionLog;
    }
}