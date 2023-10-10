package com.blp.sentenceLib.service.impl;

import com.fy.basejar.tool.ActionToolBase;
import com.blp.sentenceLib.constant.DraftChapterConstant;
import com.blp.sentenceLib.dao.DraftChapterDao;
import com.blp.sentenceLib.entity.DraftChapter;
import com.blp.sentenceLib.entity.vo.DraftScriptVo;
import com.blp.sentenceLib.service.DraftChapterService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

/**
 * (DraftChapterConstant)表服务实现类
 *
 * @author makejava
 * @since 2022-02-28 17:01:05
 */
@Service
@NoArgsConstructor
public class DraftChapterServiceImpl implements DraftChapterService {

    @Autowired
    DraftChapterDao draftChapterDao;

    @Override
    public List<DraftChapter> pageConditionDraftChapterList(DraftChapter draftChapterQuery, Integer pageSize, Integer currentPage) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        List<DraftChapter> draftChapterList =
            draftChapterDao.pageConditionDraftChapterList(connection, draftChapterQuery, pageSize, currentPage);
        return draftChapterList;
    }
    //根据剧本id找对应的章节List
    @Override
    public List<DraftChapter> getDraftChapterListByDraftId(Long draftId) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        List<DraftChapter> draftChapterList =
                draftChapterDao.getDraftChapterListByDraftId(connection, draftId);
        return draftChapterList;

    }

    @Override
    public List<DraftChapter> getDraftchapterListOneToOneByAppcaseId(Long appcaseId) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        List<DraftChapter> draftChapterList =
                draftChapterDao.getDraftchapterListOneToOneByAppcaseId(connection, appcaseId);
        return draftChapterList;

    }

    @Override
    public int pageConditionDraftChapterListTotal(DraftChapter draftChapterQuery) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        int total = draftChapterDao.pageConditionDraftChapterListTotal(connection, draftChapterQuery);
        return total;
    }

    @Override
    public int updateDraftChapter(DraftChapter draftChapter) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        int i = (int)draftChapterDao.updateIgnoreNull(connection, draftChapter);
        return i;
    }

    @Override
    public void insertDraftChapter(DraftChapter draftChapter) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        draftChapterDao.save(connection, draftChapter);
    }

    @Override
    public int deleteDraftChapter(DraftChapter draftChapter) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        int i = (int)draftChapterDao.delete(connection, draftChapter);
        return i;
    }

    @Override
    public DraftChapter getDraftChapterById(Long draftChapterId) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        DraftChapter draftChapter = draftChapterDao.getDraftChapterById(connection, draftChapterId);
        return draftChapter;
    }

    @Override
    public void batchAddDraftChapter(DraftScriptVo draftScriptVo) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        String[] split = draftScriptVo.getText().split("。");
        long preParentId = 0L;
        for (int i = 0; i < split.length; i++) {
            split[i] += "。";

            DraftChapter draftChapter = new DraftChapter();
            draftChapter.setDraftId(draftScriptVo.getDraftId());
            draftChapter.setContent(split[i]);
            draftChapter.setParentId(preParentId);
            draftChapter.setIsDeveloped(DraftChapterConstant.DRAFT_CHAPTER_UNDEVELOPED);

            draftChapterDao.save(connection, draftChapter);
            preParentId = draftChapter.getId();
        }

    }

    @Override
    public DraftChapter getDraftChapterAndAnalyse(Long draftId) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        DraftChapter draftChapter = draftChapterDao.getDraftChapterAndAnalyseByDraftId(connection, draftId);
        return draftChapter;
    }
}
