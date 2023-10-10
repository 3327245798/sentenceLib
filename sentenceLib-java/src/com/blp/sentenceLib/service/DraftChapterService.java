package com.blp.sentenceLib.service;

import com.blp.sentenceLib.entity.DraftChapter;
import com.blp.sentenceLib.entity.vo.DraftScriptVo;

import java.util.List;

/**
 * (DraftChapterConstant)表服务接口
 *
 * @author makejava
 * @since 2022-02-28 17:01:04
 */
public interface DraftChapterService {
    List<DraftChapter> pageConditionDraftChapterList(DraftChapter draftChapterQuery, Integer pageSize, Integer currentPage) throws Exception;
   ////根据剧本id找对应的章节List
    List<DraftChapter> getDraftChapterListByDraftId(Long draftId) throws Exception;
////根据应用案例id获取所有的剧本章节，目前剧本与剧本章节是一对一
List<DraftChapter> getDraftchapterListOneToOneByAppcaseId(Long appcaseId) throws Exception;
    int pageConditionDraftChapterListTotal(DraftChapter draftChapterQuery) throws Exception;

    void insertDraftChapter(DraftChapter draftChapter) throws Exception;

    int updateDraftChapter(DraftChapter draftChapter) throws Exception;

    int deleteDraftChapter(DraftChapter draftChapter) throws Exception;

    DraftChapter getDraftChapterById(Long draftChapterId) throws Exception;

    void batchAddDraftChapter(DraftScriptVo draftScriptVo) throws Exception;

    DraftChapter getDraftChapterAndAnalyse(Long draftId) throws Exception;
}
