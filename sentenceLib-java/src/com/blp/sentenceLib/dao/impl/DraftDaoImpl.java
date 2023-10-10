package com.blp.sentenceLib.dao.impl;

import com.blp.sentenceLib.dao.DraftDao;
import com.blp.sentenceLib.entity.Draft;
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
public class DraftDaoImpl extends BaseDaoImpl<Draft> implements DraftDao {
    public DraftDaoImpl() throws Exception {
    }

    @Override
    public List<Draft> getDraftList(Connection connection)throws SQLException{
        String sql = "SELECT * FROM blp_draft ";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        List<Draft> draftList = new ArrayList<>();
        while (rs.next()) {
            Draft draft = new Draft();
            Draft draftEntity = getDraftEntity(rs);
            BeanUtils.copyProperties(draftEntity, draft);
            draftList.add(draft);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return draftList;
    }


    @Override
    public List<Draft> pageConditionDraftList(Connection connection, Draft draftQuery, int pageSize, int currentPage) throws SQLException{
        int start = pageSize * (currentPage - 1);
        StringBuilder sql = new StringBuilder("SELECT * FROM blp_draft WHERE 1 = 1 ");
        if (!StringUtils.isEmpty(draftQuery.getId())) {
            sql.append(" and id = ").append(draftQuery.getId());
        }
        if (!StringUtils.isEmpty(draftQuery.getName())) {
            sql.append(" and name like '%").append(draftQuery.getName()).append("%'");
        }
        if (!StringUtils.isEmpty(draftQuery.getApplicationCaseId())) {
            sql.append(" and application_case_id = ").append(draftQuery.getApplicationCaseId());
        }
        if (!StringUtils.isEmpty(draftQuery.getCreatorId())) {
            sql.append(" and creator_id = ").append(draftQuery.getCreatorId());
        }
        if (!StringUtils.isEmpty(draftQuery.getCreatorName())) {
            sql.append(" and creator_name = ").append(draftQuery.getCreatorName());
        }

        sql.append(" limit ? , ?");

        PreparedStatement pstm = connection.prepareStatement(sql.toString());
        if(pageSize == -1 && currentPage == -1){
            pstm.setInt(1, 0);
            int total = pageConditionDraftListTotal(connection, draftQuery);
            pstm.setInt(2, total);
        }else{
            pstm.setInt(1, start);
            pstm.setInt(2, pageSize);
        }
        ResultSet rs = pstm.executeQuery();
        List<Draft> draftList = new ArrayList<>();
        while (rs.next()) {
            Draft draft = new Draft();
            Draft draftEntity = getDraftEntity(rs);
            BeanUtils.copyProperties(draftEntity, draft);
            draftList.add(draft);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return draftList;
    }


    @Override
    public int pageConditionDraftListTotal(Connection connection, Draft draftQuery) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM blp_draft WHERE 1 = 1 ");
        if (!StringUtils.isEmpty(draftQuery.getId())) {
            sql.append(" and id = ").append(draftQuery.getId());
        }
        if (!StringUtils.isEmpty(draftQuery.getName())) {
            sql.append(" and name like '%").append(draftQuery.getName()).append("%'");
        }
        if (!StringUtils.isEmpty(draftQuery.getApplicationCaseId())) {
            sql.append(" and application_case_id = ").append(draftQuery.getApplicationCaseId());
        }
        if (!StringUtils.isEmpty(draftQuery.getCreatorId())) {
            sql.append(" and creator_id = ").append(draftQuery.getCreatorId());
        }
        if (!StringUtils.isEmpty(draftQuery.getCreatorName())) {
            sql.append(" and creator_name = ").append(draftQuery.getCreatorName());
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

    @Override
    public Draft getDraftById(Connection connection, Long draftId) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM blp_draft WHERE id = ? ");

        PreparedStatement pstm = connection.prepareStatement(sql.toString());
        pstm.setLong(1,draftId);
        ResultSet rs = pstm.executeQuery();
        Draft draftEntity = null;
        // 这里只有1条或0条两种情况
        if (rs.next()){
            draftEntity= getDraftEntity(rs);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return draftEntity;
    }
    //根据业务appcaseid获取一个个剧本

    @Override
    public List<Draft> getDraftListByAppcaseId(Connection connection, Long appcaseId) throws SQLException {
        String sql = "SELECT * FROM blp_draft WHERE application_case_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setLong(1, appcaseId);
        ResultSet rs = pstm.executeQuery();
        List<Draft> draftList = new ArrayList<>();
        while (rs.next()) {
            Draft draft = new Draft();
            Draft draftEntity = getDraftEntity(rs);
            BeanUtils.copyProperties(draftEntity, draft);
            draftList.add(draft);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return draftList;
    }

    private Draft getDraftEntity(ResultSet rs) throws SQLException {
        Draft draft = new Draft();
        draft.setId(rs.getLong("id"));
        draft.setName(rs.getString("name"));
        draft.setDescription(rs.getString("description"));
        draft.setType(rs.getString("type"));
        draft.setApplicationCaseId(rs.getLong("application_case_id"));
        draft.setCreatorId(rs.getLong("creator_id"));
        draft.setCreatorName(rs.getString("creator_name"));
        draft.setCreateTime(rs.getTimestamp("create_time"));
        draft.setUpdateTime(rs.getTimestamp("update_time"));
        return draft;
    }

}