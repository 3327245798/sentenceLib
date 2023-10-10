package com.blp.sentenceLib.dao.impl;

import com.blp.sentenceLib.dao.AnalyseDao;
import com.blp.sentenceLib.dao.DraftChapterDao;
import com.blp.sentenceLib.dao.DraftDao;
import com.blp.sentenceLib.entity.Analyse;
import com.blp.sentenceLib.entity.Draft;
import com.blp.sentenceLib.entity.DraftChapter;
import com.fy.toolhelper.db.BaseDaoImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DraftChapterDaoImpl extends BaseDaoImpl<DraftChapter> implements DraftChapterDao {
    @Autowired
    AnalyseDao analyseDao;

    @Autowired
    DraftDao draftDao;

    public DraftChapterDaoImpl() throws Exception {}

    @Override
    public List<DraftChapter> pageConditionDraftChapterList(Connection connection, DraftChapter draftChapterQuery,
        int pageSize, int currentPage) throws SQLException {
        int start = pageSize * (currentPage - 1);
        StringBuilder sql = new StringBuilder("SELECT * FROM blp_draft_chapter WHERE 1 = 1 ");

        if (!StringUtils.isEmpty(draftChapterQuery.getContent())) {
            sql.append(" and content like '%").append(draftChapterQuery.getContent()).append("%'");
        }
        if (!StringUtils.isEmpty(draftChapterQuery.getDraftId())) {
            sql.append(" and draft_id = ").append(draftChapterQuery.getDraftId());
        }
        if (!StringUtils.isEmpty(draftChapterQuery.getParentId())) {
            sql.append(" and parent_id = ").append(draftChapterQuery.getParentId());
        }
        if (!StringUtils.isEmpty(draftChapterQuery.getAnalyseId())) {
            sql.append(" and analyse_id = ").append(draftChapterQuery.getAnalyseId());
        }

        sql.append(" limit ? , ?");

        PreparedStatement pstm = connection.prepareStatement(sql.toString());
        if (pageSize == -1 && currentPage == -1) {
            pstm.setInt(1, 0);
            int total = pageConditionDraftChapterListTotal(connection, draftChapterQuery);
            pstm.setInt(2, total);
        } else {
            pstm.setInt(1, start);
            pstm.setInt(2, pageSize);
        }
        ResultSet rs = pstm.executeQuery();
        List<DraftChapter> draftChapterList = new ArrayList<>();
        while (rs.next()) {
            DraftChapter draftChapter = getDraftChapterEntity(rs);
            // 融合 分析剧本
            Analyse analyse = draftChapter.getAnalyseId() == null ? new Analyse()
                : analyseDao.getAnalyseById(connection, draftChapter.getAnalyseId());
            draftChapter.setAnalyse(analyse);
            draftChapterList.add(draftChapter);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return draftChapterList;
    }
//根据剧本id找章节,//根据剧本id找对应的章节List
    @Override
    public List<DraftChapter> getDraftChapterListByDraftId(Connection connection, Long chapterId) throws SQLException {
        String sql="SELECT dc.*,d.name,d.description,d.creator_name,d.create_time,d.update_time FROM blp_draft_chapter dc,blp_draft d WHERE dc.draft_id=d.id and d.id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setLong(1, chapterId);
        ResultSet rs = pstm.executeQuery();
        List<DraftChapter> draftChapterList = new ArrayList<>();
        while (rs.next()) {
            DraftChapter draftChapter = new DraftChapter();
            DraftChapter draftChapterEntity = getDraftChapterEntity(rs);
            BeanUtils.copyProperties(draftChapterEntity, draftChapter);
            draftChapterList.add(draftChapter);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }

        return draftChapterList;
    }
    //根据应用案例id获取所有的剧本章节，目前剧本与剧本章节是一对一
    @Override
    public List<DraftChapter> getDraftchapterListOneToOneByAppcaseId(Connection connection, Long appcaseId) throws SQLException {
        String sql="SELECT dc.*,d.name,d.description,d.creator_name,d.create_time,d.update_time FROM blp_draft_chapter dc,blp_draft d WHERE dc.draft_id=d.id and d.application_case_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setLong(1, appcaseId);
        ResultSet rs = pstm.executeQuery();
        List<DraftChapter> draftChapterList = new ArrayList<>();
        while (rs.next()) {
            DraftChapter draftChapter = new DraftChapter();
            DraftChapter draftChapterEntity = getDraftChapterEntity(rs);
            BeanUtils.copyProperties(draftChapterEntity, draftChapter);
            draftChapterList.add(draftChapter);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }

        return draftChapterList;
    }

    @Override
    public int pageConditionDraftChapterListTotal(Connection connection, DraftChapter draftChapterQuery)
        throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM blp_draft_chapter WHERE 1 = 1 ");

        if (!StringUtils.isEmpty(draftChapterQuery.getContent())) {
            sql.append(" and content like '%").append(draftChapterQuery.getContent()).append("%'");
        }
        if (!StringUtils.isEmpty(draftChapterQuery.getDraftId())) {
            sql.append(" and draft_id = ").append(draftChapterQuery.getDraftId());
        }
        if (!StringUtils.isEmpty(draftChapterQuery.getParentId())) {
            sql.append(" and parent_id = ").append(draftChapterQuery.getParentId());
        }
        if (!StringUtils.isEmpty(draftChapterQuery.getAnalyseId())) {
            sql.append(" and analyse_id = ").append(draftChapterQuery.getAnalyseId());
        }

        PreparedStatement pstm = connection.prepareStatement(sql.toString());
        ResultSet rs = pstm.executeQuery();
        int count = -1;
        while (rs.next()) {
            count = rs.getInt(1);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return count;
    }
//根据章节id获取章节相关信息
    @Override
    public DraftChapter getDraftChapterById(Connection connection, Long draftChapterId) throws SQLException {
        DraftChapter draftChapter = null;
        StringBuilder sql = new StringBuilder("SELECT * FROM blp_draft_chapter WHERE id = ? ");

        PreparedStatement pstm = connection.prepareStatement(sql.toString());
        pstm.setLong(1, draftChapterId);
        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {
            draftChapter = getDraftChapterEntity(rs);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return draftChapter;
    }

    @Override
    public DraftChapter getDraftChapterAndAnalyseByDraftId(Connection connection, Long draftId) throws SQLException {

        String sql = "SELECT * FROM blp_draft_chapter WHERE draft_id = ? ";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setLong(1, draftId);

        ResultSet rs = pstm.executeQuery();
        DraftChapter draftChapter = null;
        if (rs.next()) {
            draftChapter = getDraftChapterEntity(rs);

            // 联合 分析剧本
            Analyse analyse = draftChapter.getAnalyseId() == null ? new Analyse()
                : analyseDao.getAnalyseById(connection, draftChapter.getAnalyseId());
            draftChapter.setAnalyse(analyse);
            // 联合 草稿剧本
            Draft draft = draftDao.getDraftById(connection, draftChapter.getDraftId());
            draftChapter.setTitle(draft.getName());
            draftChapter.setDescription(draft.getDescription());
            draftChapter.setCreatorName(draft.getCreatorName());
            draftChapter.setCreateTime(draft.getCreateTime());
            draftChapter.setUpdateTime(draft.getUpdateTime());
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return draftChapter == null ? new DraftChapter() : draftChapter;
    }

    private DraftChapter getDraftChapterEntity(ResultSet rs) throws SQLException {
        DraftChapter draftChapter = new DraftChapter();
        draftChapter.setId(rs.getLong("id"));
        draftChapter.setDraftId(rs.getLong("draft_id"));
        draftChapter.setParentId(rs.getLong("parent_id"));
        draftChapter.setContent(rs.getString("content"));
        draftChapter.setSerialization(rs.getString("serialization"));
        draftChapter.setIsDeveloped(rs.getString("is_developed"));
        draftChapter.setAnalyseId(rs.getLong("analyse_id"));

        //业务属性是d.name,d.description,d.creator_name,d.create_time,d.update_time（blp_draft_chapter dc和blp_draft连表查询用到的）
        draftChapter.setTitle(rs.getString("name"));
        draftChapter.setDescription(rs.getString("description"));
        draftChapter.setCreatorName(rs.getString("creator_name"));
        draftChapter.setUpdateTime(rs.getTimestamp("update_time"));
        draftChapter.setCreateTime(rs.getTimestamp("create_time"));
        return draftChapter;
    }

}