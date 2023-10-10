package com.blp.sentenceLib.dao;

import com.blp.sentenceLib.entity.ApplicationCase;
import com.fy.toolhelper.db.IBaseDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * (ApplicationCase)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-28 17:01:59
 */
public interface ApplicationCaseDao extends IBaseDao<ApplicationCase> {

    List<ApplicationCase> getAppcaseByBusinessId(Connection connection,Long businessId) throws Exception;
    List<ApplicationCase> pageConditionApplicationCaseList(Connection connection, ApplicationCase applicationCaseQuery, int pageSize, int currentPage) throws SQLException;

    int pageConditionApplicationCaseListTotal(Connection connection, ApplicationCase applicationCaseQuery) throws SQLException;

    List<ApplicationCase> hotApplicationCaseList(Connection connection) throws SQLException;

    ApplicationCase getApplicationCaseById(Connection connection, long applicationCaseId) throws SQLException;
}

