package com.blp.sentenceLib.dao;

import com.blp.sentenceLib.entity.Analyse;
import com.fy.toolhelper.db.IBaseDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * (Analyse)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-28 17:01:20
 */
public interface AnalyseDao extends IBaseDao<Analyse> {

    List<Analyse> pageConditionAnalyseList(Connection connection, Analyse draftQuery, int pageSize, int currentPage) throws SQLException;

    int pageConditionAnalyseListTotal(Connection connection, Analyse draftQuery) throws SQLException;

    Analyse getAnalyseById(Connection connection, Long analyseId) throws SQLException;

   List <Analyse> getAnalyseByDraftChapterId(Connection connection, Long draftChapterId) throws SQLException;
}

