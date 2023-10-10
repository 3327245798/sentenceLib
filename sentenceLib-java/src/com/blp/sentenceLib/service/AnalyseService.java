package com.blp.sentenceLib.service;

import com.blp.sentenceLib.entity.Analyse;

import java.util.List;

/**
 * (Analyse)表服务接口
 *
 * @author makejava
 * @since 2022-02-28 17:01:20
 */
public interface AnalyseService {
    List<Analyse> pageConditionAnalyseList(Analyse analyseQuery, Integer pageSize, Integer currentPage) throws Exception;

    Integer pageConditionAnalyseListTotal(Analyse analyseQuery) throws Exception;

    void insertAnalyse(Analyse analyse) throws Exception;

    int updateAnalyse(Analyse analyse) throws Exception;

    int deleteAnalyse(Analyse analyse) throws Exception;

    List<Analyse> getAnalyseByDraftChapterId(Long draftChapterId) throws Exception;
    Analyse getAnalyseById( Long analyseId) throws Exception;//根据分析剧本id获取分析剧本
}
