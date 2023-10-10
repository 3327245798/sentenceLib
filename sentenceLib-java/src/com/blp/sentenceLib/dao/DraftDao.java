package com.blp.sentenceLib.dao;

import com.blp.sentenceLib.entity.Draft;
import com.fy.toolhelper.db.IBaseDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * (Draft)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-28 17:00:42
 */
public interface DraftDao extends IBaseDao<Draft> {
    List<Draft> getDraftList(Connection connection)throws SQLException;
    List<Draft> pageConditionDraftList(Connection connection, Draft draftQuery, int pageSize, int currentPage) throws SQLException;

    int pageConditionDraftListTotal(Connection connection, Draft draftQuery) throws SQLException;

    Draft getDraftById(Connection connection, Long draftId) throws SQLException;
    List<Draft> getDraftListByAppcaseId(Connection connection,Long appcaseId)throws SQLException;

}

