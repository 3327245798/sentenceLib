package com.blp.sentenceLib.dao;

import com.blp.sentenceLib.entity.WordVersion;
import com.fy.toolhelper.db.IBaseDao;

import java.sql.Connection;
import java.util.List;

/**
 * (WordVersionDao)表数据库访问层
 *
 * @author makewangzhe
 * @since 2022-08-4 10:25:24
 */
public interface WordVersionDao extends IBaseDao<WordVersion> {
//根据词汇id获取词汇版本记录
    List<WordVersion> getAllWordVersionByWordId(Connection connection, Long wordId) throws Exception;

}