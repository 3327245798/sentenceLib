package com.blp.sentenceLib.dao.impl;

import com.blp.sentenceLib.dao.DraftReleaseDao;
import com.blp.sentenceLib.entity.DraftRelease;
import com.fy.toolhelper.db.BaseDaoImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DraftReleaseDaoImpl extends BaseDaoImpl<DraftRelease> implements DraftReleaseDao  {
    public DraftReleaseDaoImpl() throws Exception {
    }

    @Override
    public DraftRelease getDraftReleaseById(Connection conn, Long draftId) {
        StringBuilder sql = new StringBuilder("SELECT * FROM blp_draft_release WHERE draft_id = ?");

        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm = conn.prepareStatement(sql.toString());
            pstm.setLong(1,draftId);
            rs = pstm.executeQuery();
            DraftRelease draftReleaseEntity = null;
            // 这里只有1条或0条两种情况
            if (rs.next()){
                draftReleaseEntity = getDraftReleaseEntity(rs);
            }
            return draftReleaseEntity;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstm != null) {
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public DraftRelease getDraftReleaseByDraftIdAndVersion(Connection conn, Long draftId, Long version) {
        StringBuilder sql = new StringBuilder("SELECT * FROM blp_draft_release WHERE draft_id = ? and version=?");

        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm = conn.prepareStatement(sql.toString());
            pstm.setLong(1,draftId);
            pstm.setLong(2,version);
            rs = pstm.executeQuery();
            DraftRelease draftReleaseEntity = null;
            // 这里只有1条或0条两种情况
            if (rs.next()){
                draftReleaseEntity = getDraftReleaseEntity(rs);
            }
            return draftReleaseEntity;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstm != null) {
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
/**wz
 * 根据draft id 获取已发布剧本（发布到帮语网站的）*/
    @Override
    public DraftRelease getReleaseDraftById(Connection conn, Long draftId) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM blp_draft_release WHERE draft_id = ?");

        PreparedStatement pstm = null;
        ResultSet rs = null;
        DraftRelease draftReleaseEntity = null;
        // 这里只有1条或0条两种情况
        if (rs.next()){
            draftReleaseEntity= getDraftReleaseEntity(rs);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return draftReleaseEntity;

    }

/**获取已发布到帮语网站的剧本实体*/
    private DraftRelease getDraftReleaseEntity(ResultSet rs) throws SQLException {
        DraftRelease draftRelease = new DraftRelease();
        draftRelease.setId(rs.getLong("id"));
        draftRelease.setDraftId(rs.getLong("draft_id"));
        draftRelease.setCreatorId(rs.getLong("creator_id"));
        draftRelease.setCreatorName(rs.getString("creator_name"));
        draftRelease.setCreateTime(rs.getTimestamp("create_time"));
        draftRelease.setVersion(rs.getLong("version"));
        draftRelease.setName(rs.getString("name"));
        draftRelease.setType(rs.getString("type"));
        draftRelease.setChapterId(rs.getLong("chapter_id"));

        return draftRelease;
    }
    /**分页查询 获取已发布到帮语网站的剧本列表*/
    @Override
    public List<DraftRelease> pageConditionDraftReleaseList(Connection connection, DraftRelease draftReleaseQuery, Integer pageSize, Integer currentPage)throws SQLException{
        int start = pageSize * (currentPage - 1);
        /*StringBuilder sql = new StringBuilder("select dr.*, d.name ,d.type from blp_draft_release dr," +
                " blp_draft d where dr.draft_id = d.id");*/
        StringBuilder sql = new StringBuilder("select dr.*, d.name, d.type,dc.id chapter_id from blp_draft_release dr, " +
                "blp_draft d,blp_draft_chapter dc where dr.draft_id = d.id and dr.draft_id =dc.draft_id") ;
        /*if (!StringUtils.isEmpty(draftReleaseQuery.getId())) {
            sql.append(" and id = ").append(draftReleaseQuery.getId());
        }
        if (!StringUtils.isEmpty(draftReleaseQuery.getDraftId())) {
            sql.append(" and draft_id=").append(draftReleaseQuery.getDraftId());
        }
        if (!StringUtils.isEmpty(draftReleaseQuery.getCreatorId())) {
            sql.append(" and creator_id = ").append(draftReleaseQuery.getCreatorId());
        }
        if (!StringUtils.isEmpty(draftReleaseQuery.getCreatorName())) {
            sql.append(" and creator_name = ").append(draftReleaseQuery.getCreatorName());
        }
        if (!StringUtils.isEmpty(draftReleaseQuery.getVersion())) {
            sql.append(" and version = ").append(draftReleaseQuery.getVersion());
        }*/
        sql.append(" limit ? , ?");
        PreparedStatement pstm = connection.prepareStatement(sql.toString());
        if(pageSize == -1 && currentPage == -1){
            pstm.setInt(1, 0);
            int total = pageConditionDraftReleaseListTotal(connection, draftReleaseQuery);
            pstm.setInt(2, total);
        }else{
            pstm.setInt(1, start);
            pstm.setInt(2, pageSize);
        }
        ResultSet rs = pstm.executeQuery();
        List<DraftRelease> draftReleaseList = new ArrayList<>();
        while (rs.next()) {
            DraftRelease draftRelease = new DraftRelease();
            DraftRelease draftReleaseEntity = getDraftReleaseEntity(rs);
            BeanUtils.copyProperties(draftReleaseEntity, draftRelease);
            draftReleaseList.add(draftRelease);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return draftReleaseList;

    }
    /**分页查询 获取已发布到帮语网站的剧本总数*/
    @Override
    public int pageConditionDraftReleaseListTotal( Connection connection,  DraftRelease draftReleaseQuery) throws SQLException{
        /*StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM blp_draft_release dr," +
                " blp_draft d where dr.draft_id = d.id");*/
        StringBuilder sql = new StringBuilder("select dr.*, d.name, d.type,dc.id chapter_id from blp_draft_release dr, " +
                "blp_draft d,blp_draft_chapter dc where dr.draft_id = d.id and dr.draft_id =dc.draft_id") ;
        if (!StringUtils.isEmpty(draftReleaseQuery.getId())) {
            sql.append(" and id = ").append(draftReleaseQuery.getId());
        }
        if (!StringUtils.isEmpty(draftReleaseQuery.getDraftId())) {
            sql.append(" and draft_id=").append(draftReleaseQuery.getDraftId());
        }
        if (!StringUtils.isEmpty(draftReleaseQuery.getCreatorId())) {
            sql.append(" and creator_id = ").append(draftReleaseQuery.getCreatorId());
        }
        if (!StringUtils.isEmpty(draftReleaseQuery.getCreatorName())) {
            sql.append(" and creator_name = ").append(draftReleaseQuery.getCreatorName());
        }
        if (!StringUtils.isEmpty(draftReleaseQuery.getVersion())) {
            sql.append(" and version = ").append(draftReleaseQuery.getVersion());
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
}
