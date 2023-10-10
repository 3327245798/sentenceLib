package com.blp.sentenceLib.service.impl;

import com.fy.basejar.tool.ActionToolBase;
import com.blp.sentenceLib.constant.DraftReleaseType;
import com.blp.sentenceLib.dao.DraftReleaseDao;
import com.blp.sentenceLib.entity.DraftRelease;
import com.blp.sentenceLib.service.DraftReleaseService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;

import java.util.List;

/**
 * (Draft)表服务实现类
 *
 * @since 2022-07-14 15:23:20
 */
@Service
@NoArgsConstructor
public class DraftReleaseServiceImpl implements DraftReleaseService {

    @Autowired
    private DraftReleaseDao draftReleaseDao;

/**
 * 发布草稿剧本到帮语网站*/
    @Override
    public Integer shareToBlpWebSite(DraftRelease draftReleaseParams) throws Exception {
        Connection conn = ActionToolBase.getDBConnection();
        // 先判断该剧本是否已发布
        DraftRelease draftRelease = draftReleaseDao.getDraftReleaseByDraftIdAndVersion(conn, draftReleaseParams.getDraftId(), draftReleaseParams.getVersion());
        if(draftRelease!=null) { // 说明已发布
             return DraftReleaseType.RELEASED_DRAFT.getValue();
        } else {
            draftReleaseDao.save(conn,draftReleaseParams);
            return DraftReleaseType.NOT_RELEASED_DRAFT.getValue(); // 说明未发布，并且发布成功
        }
    }
    /**
     * 根据id 获取发布到帮语网站的草稿剧本*/
    @Override
    public DraftRelease getReleaseDraftById(Long draftId) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();

        return draftReleaseDao.getReleaseDraftById(connection, draftId);


    }
    @Override
    public List<DraftRelease> pageConditionDraftReleaseList(DraftRelease  draftReleaseQuery, Integer pageSize, Integer currentPage) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        List<DraftRelease> draftReleaseList = draftReleaseDao.pageConditionDraftReleaseList(connection, draftReleaseQuery, pageSize, currentPage);
        return draftReleaseList;

    }

    @Override
    public Integer pageConditionDraftReleaseListTotal(DraftRelease draftReleaseQuery) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        Integer total = draftReleaseDao.pageConditionDraftReleaseListTotal(connection, draftReleaseQuery);
        return total;
    }


}
