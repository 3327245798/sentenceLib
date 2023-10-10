package com.blp.sentenceLib.service;

import com.blp.sentenceLib.entity.DraftRelease;

import java.util.List;

/**
 * (DraftReleaseService)表服务接口
 *
 * @since 2022-07-14 15:23:20
 */

public interface DraftReleaseService {
    Integer shareToBlpWebSite(DraftRelease draftReleaseParams) throws Exception;
    DraftRelease getReleaseDraftById (Long draftId) throws Exception;
    List<DraftRelease> pageConditionDraftReleaseList( DraftRelease draftReleaseQuery, Integer pageSize, Integer currentPage) throws Exception;
    Integer pageConditionDraftReleaseListTotal(DraftRelease draftReleaseQuery) throws Exception;
}

