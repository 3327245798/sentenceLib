package com.blp.sentenceLib.service;

import com.blp.sentenceLib.entity.Sentence;

import java.util.List;

/**
 * (Sentence)表服务接口
 *
 * @author makejava
 * @since 2022-05-07 10:25:24
 */
public interface SentenceService {
    /**
     * 获取所有的句型
     * @return
     * @param type 草稿还是分析
     */

    List<Sentence> getAllSentences() throws Exception;
    List<Sentence> getAllSentencesByType(Integer type) throws Exception;
    List<Sentence> getAllSentencesByuserInputKeyword(String userInputKeyword) throws Exception;
    List<Sentence> getAllSentencesByCreatorName(String creatorName) throws Exception;
    List<Sentence> getAllSentenceByStatus(Integer status) throws Exception;
    List<Sentence> getAllSentenceInChecking() throws Exception;
    List<Sentence> getAllSentenceInDay() throws Exception;
    List<Sentence> getAllSentenceInWeek() throws Exception;
    List<Sentence> getAllSentenceInMonth() throws Exception;
    Sentence getSentenceById(Long sentenceId) throws Exception;



    int updateSentence(Sentence sentence) throws Exception;

    void insertSentence(Sentence sentence) throws Exception;

    int deleteSentence(Sentence sentence) throws Exception;


}