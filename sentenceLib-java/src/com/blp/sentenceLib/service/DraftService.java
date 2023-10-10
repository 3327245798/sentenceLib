package com.blp.sentenceLib.service;

import com.blp.sentenceLib.entity.Draft;

import java.util.List;

/**
 * (Draft)表服务接口
 *
 * @author makejava
 * @since 2022-02-28 17:00:42
 */
public interface DraftService {
    //获取剧本列表
    List<Draft> getDraftList()throws Exception;
    List<Draft> pageConditionDraftList(Draft draftQuery, Integer pageSize, Integer currentPage) throws Exception;
    List<Draft> getDraftListByAppcaseId(Long appcaseId) throws Exception;

    Integer pageConditionDraftListTotal(Draft draftQuery) throws Exception;

    void insertDraft(Draft draft) throws Exception;

    int updateDraft(Draft draft) throws Exception;

    int deleteDraft(Draft draft) throws Exception;

    Draft getDraftById(Long draftId) throws Exception;



}
