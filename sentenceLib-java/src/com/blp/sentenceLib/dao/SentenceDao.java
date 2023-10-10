package com.blp.sentenceLib.dao;

import com.blp.sentenceLib.entity.Sentence;
import com.fy.toolhelper.db.IBaseDao;

import java.sql.Connection;
import java.util.List;

/**
 * (Sentence)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-07 10:25:24
 */
public interface SentenceDao extends IBaseDao<Sentence> {

    List<Sentence> getAllSentences(Connection connection) throws Exception;
    List<Sentence> getAllSentencesByType(Connection connection, Integer type) throws Exception;
    List<Sentence> getAllSentencesByuserInputKeyword(Connection connection, String userInputKeyword) throws Exception;
    List<Sentence> getAllSentencesByCreatorName(Connection connection, String creatorName) throws Exception;

    List<Sentence> getAllSentenceByStatus(Connection connection,Integer status)throws Exception;
    //查询处于审核中的句型，为帮语审核员服务
    List<Sentence> getAllSentenceInChecking(Connection connection)throws Exception;
    List<Sentence> getAllSentenceInDay (Connection connection) throws Exception;
    List<Sentence> getAllSentenceInWeek (Connection connection) throws Exception;
    List<Sentence> getAllSentenceInMonth(Connection connection) throws Exception;
    Sentence getSentenceById(Connection connection ,Long snetenceId) throws Exception;
}