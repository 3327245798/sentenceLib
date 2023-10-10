package com.blp.sentenceLib.service.impl;

import com.fy.basejar.tool.ActionToolBase;
import com.blp.sentenceLib.constant.DraftChapterConstant;
import com.blp.sentenceLib.constant.ResultCodeEnum;
import com.blp.sentenceLib.dao.AnalyseDao;
import com.blp.sentenceLib.entity.Analyse;
import com.blp.sentenceLib.entity.DraftChapter;
import com.blp.sentenceLib.exception.BLPException;
import com.blp.sentenceLib.service.AnalyseService;
import com.blp.sentenceLib.service.DraftChapterService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * (Analyse)表服务实现类
 *
 * @author makejava
 * @since 2022-02-28 17:01:20
 */
@Service
@NoArgsConstructor
public class AnalyseServiceImpl implements AnalyseService {

    @Autowired
    AnalyseDao analyseDao;

    @Autowired
    DraftChapterService draftChapterService;

    @Override
    public List<Analyse> pageConditionAnalyseList(Analyse analyseQuery, Integer pageSize, Integer currentPage)
        throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        List<Analyse> analyseList =
            analyseDao.pageConditionAnalyseList(connection, analyseQuery, pageSize, currentPage);
        return analyseList;
    }

    @Override
    public Integer pageConditionAnalyseListTotal(Analyse analyseQuery) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        Integer total = analyseDao.pageConditionAnalyseListTotal(connection, analyseQuery);
        return total;
    }

    @Override
    public int updateAnalyse(Analyse analyse) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        int i = (int)analyseDao.updateIgnoreNull(connection, analyse);
        return i;
    }

    @Override
    public void insertAnalyse(Analyse analyse) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();

        Date date = new Date();
        analyse.setCreateTime(date);
        analyse.setUpdateTime(date);

        analyseDao.save(connection, analyse);

        if (StringUtils.isEmpty(analyse.getDraftChapterId())) {
            throw new BLPException(ResultCodeEnum.DRAFT_CHAPTER_UPDATE_EXCEPTION.getCode(),
                ResultCodeEnum.DRAFT_CHAPTER_UPDATE_EXCEPTION.getMsg());
        }

        DraftChapter draftChapter = draftChapterService.getDraftChapterById(analyse.getDraftChapterId());
        draftChapter.setAnalyseId(analyse.getId());
        draftChapter.setIsDeveloped(DraftChapterConstant.DRAFT_CHAPTER_DEVELOPED);
        draftChapterService.updateDraftChapter(draftChapter);
    }

    @Override
    public int deleteAnalyse(Analyse analyse) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        int i = (int)analyseDao.delete(connection, analyse);
        return i;
    }

    @Override
    public List<Analyse> getAnalyseByDraftChapterId(Long draftChapterId) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        List<Analyse> analyseList = analyseDao.getAnalyseByDraftChapterId(connection, draftChapterId);
        return analyseList;
    }
//根据分析剧本id获取分析剧本
    @Override
    public Analyse getAnalyseById(Long analyseId) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        Analyse analyse = analyseDao.getAnalyseById(connection, analyseId);
        return analyse;
    }
}
