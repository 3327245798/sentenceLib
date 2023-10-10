package com.blp.sentenceLib.dao;

import com.blp.sentenceLib.entity.DraftChapter;
import com.fy.toolhelper.db.IBaseDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * (DraftChapterConstant)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-28 17:01:04
 */
public interface DraftChapterDao extends IBaseDao<DraftChapter> {

    List<DraftChapter> pageConditionDraftChapterList(Connection connection, DraftChapter draftChapterQuery, int pageSize, int currentPage) throws SQLException;
    //根据剧本id找对应的章节List
    List<DraftChapter> getDraftChapterListByDraftId(Connection connection, Long chapterId) throws SQLException;
    /*根据应用案例id获取所有的剧本章节，目前剧本与剧本章节是一对一,是draft和draftChapter连表查询，
    因为目前draft和draftChapter是一对一的关系，所以直接根据应用案例id可以找到剧本id即剧本案例id*/
    List<DraftChapter> getDraftchapterListOneToOneByAppcaseId(Connection connection, Long appcaseId) throws SQLException;
    int pageConditionDraftChapterListTotal(Connection connection, DraftChapter draftChapterQuery) throws SQLException;
    DraftChapter getDraftChapterById(Connection connection, Long draftChapterId) throws SQLException;

    DraftChapter getDraftChapterAndAnalyseByDraftId(Connection connection, Long draftId) throws SQLException;
}

