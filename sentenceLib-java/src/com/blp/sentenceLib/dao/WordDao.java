package com.blp.sentenceLib.dao;

import com.blp.sentenceLib.entity.Sentence;
import com.blp.sentenceLib.entity.Word;
import com.fy.toolhelper.db.IBaseDao;

import java.sql.Connection;
import java.util.List;

/**
 * (Word)表数据库访问层
 *
 * @author makewangzhe
 * @since 2022-07-27 10:25:24
 */
public interface WordDao extends IBaseDao<Word> {
    //获取所有的已绑定固件的词汇
    List<Word> getAllWords(Connection connection) throws Exception;


    //根据状态获取词汇，目前有四种状态，0禁用，1审核中，2审核未通过，3审核通过并启用
    List<Word> getAllWordsByStatus(Connection connection, Integer status) throws Exception;
    //根据固件类型获取词汇，固件类型，(词汇绑定时可以进行选择)目前设计了五种，普通工具工具、系统工具、web service及微服务，用234分别表示
    List<Word> getPublicWordByUnitType(Connection connection, Integer unitType) throws Exception;


    List<Word> getAllWordsByCreatorName (Connection connection, String creatorName) throws Exception;

    //获取一个月内创建的所有词汇
    List<Word> getAllWordsInMonth (Connection connection) throws Exception;
    //获取最近7天创建的所有词汇
    //mysql   select * from bls_word  where DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(create_time);
    List<Word> getAllWordsInWeek (Connection connection) throws Exception;

    //获取一天内创建的所有词汇
    List<Word> getAllWordsInDay (Connection connection) throws Exception;
    //////////////////////////////////////////////////////////////////////////
    //这里是还未绑定固件的原始词汇（就是指所有的词汇，绑定固件和未绑定固件的）
    //获取所有词汇，绑定固件与不绑定固件的都获取到
    List<Word> getAllWordsNoUnit(Connection connection) throws Exception;
    //根据词汇状态获取原始的还未绑定固件的词汇，词汇状态，
    List<Word> getAllWordsNoUnitByStatus(Connection connection,Integer status) throws Exception;
    //根据创建者姓名获取原始的还未绑定固件的词汇，创造者姓名，
    List<Word> getAllWordsNoUnitByCreatorName(Connection connection,String creatorName) throws Exception;
    //一个月内创建的词汇，原始的还未绑定固件的词汇
    List<Word> getAllWordsNoUnitInMonth(Connection connection) throws Exception;
    //一周内创建的词汇，原始的还未绑定固件的词汇
    List<Word> getAllWordsNoUnitInWeek(Connection connection) throws Exception;
    //一天内创建的词汇，原始的还未绑定固件的词汇
    List<Word> getAllWordsNoUnitInWeekInDay(Connection connection) throws Exception;


}