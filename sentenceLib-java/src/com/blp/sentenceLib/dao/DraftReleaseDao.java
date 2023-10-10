package com.blp.sentenceLib.dao;

import com.blp.sentenceLib.entity.DraftRelease;
import com.fy.toolhelper.db.IBaseDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * (DraftRelease)表数据库访问层
 *
 * @since 2022-07-14 15:23:20
 */
public interface DraftReleaseDao extends IBaseDao<DraftRelease> {
    DraftRelease getDraftReleaseById(Connection conn, Long draftId) throws SQLException;

    DraftRelease getDraftReleaseByDraftIdAndVersion(Connection conn, Long draftId, Long version);

    DraftRelease getReleaseDraftById(Connection conn, Long draftId) throws SQLException;
    List<DraftRelease> pageConditionDraftReleaseList(Connection connection, DraftRelease draftReleaseQuery, Integer pageSize, Integer currentPage)throws SQLException;
    int pageConditionDraftReleaseListTotal( Connection connection,  DraftRelease draftReleaseQuery)throws SQLException;
}
