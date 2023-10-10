package com.blp.sentenceLib.service;

import com.blp.sentenceLib.entity.Sentence;
import com.blp.sentenceLib.entity.Word;
import com.blp.sentenceLib.entity.WordVersion;

import java.util.List;

/**
 * (Word)表服务接口
 *
 * @author makejava
 * @since 2022-7-27
 */
public interface WordService {
    /**
     * 获取所有词汇
     * @return
     * @param status 词汇的状态，0禁用 1审核中 2审核未通过 3审核通过并启用
     */
    //获取所有的词汇，绑定固件和未绑定固件的都要获取到
    List<Word> getAllWords() throws Exception;

    List<Word> getAllWordsByStatus(Integer status) throws Exception;
    List<Word> getPublicWordByUnitType(Integer unitType) throws Exception;
    List<Word> getAllWordsByCreatorName(String creatorName) throws Exception;
    List<Word> getAllWordsInMonth() throws Exception;
    List<Word> getAllWordsInWeek() throws Exception;
    List<Word> getAllWordsInDay() throws Exception;
//未绑定固件的原始词汇，
    List<Word> getAllWordsNoUnit() throws Exception;
    List<Word> getAllWordsNoUnitByStatus(Integer status) throws Exception;
    List<Word> getAllWordsNoUnitByCreatorName (String creatorName) throws Exception;
    List<Word> getAllWordsNoUnitInMonth() throws Exception;
    List<Word> getAllWordsNoUnitInWeek() throws Exception;
    List<Word> getAllWordsNoUnitInDay() throws Exception;

    int updateWord(Word word) throws Exception;

    void insertWord(Word word) throws Exception;

    int deleteWord(Word word) throws Exception;


}