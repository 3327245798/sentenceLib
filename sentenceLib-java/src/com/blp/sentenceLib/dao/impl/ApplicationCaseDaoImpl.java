package com.blp.sentenceLib.dao.impl;

import com.blp.sentenceLib.dao.ApplicationCaseDao;
import com.blp.sentenceLib.entity.ApplicationCase;
import com.blp.sentenceLib.entity.Band;
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
public class ApplicationCaseDaoImpl extends BaseDaoImpl<ApplicationCase> implements ApplicationCaseDao {
    public ApplicationCaseDaoImpl() throws Exception {
    }
//查询某一业务类型的所有应用案例wz
    @Override
    public List<ApplicationCase> getAppcaseByBusinessId(Connection connection,Long businessId) throws Exception{
        String sql="SELECT ac.*,bt.`name` businesstype_name from blp_application_case ac,blp_business_type bt WHERE ac.business_id=bt.id and bt.id=? ";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setLong(1, businessId);
        ResultSet rs = pstm.executeQuery();
        List<ApplicationCase> appCaseList = new ArrayList();
        while (rs.next()) {
            ApplicationCase appcase = new ApplicationCase();
            ApplicationCase appcaseEntity = getApplicationCaseEntity(rs);
            BeanUtils.copyProperties(appcaseEntity, appcase);
            appCaseList.add(appcase);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return appCaseList;


    }
    @Override
    public List<ApplicationCase> pageConditionApplicationCaseList(Connection connection, ApplicationCase applicationCaseQuery, int pageSize, int currentPage) throws SQLException{
        int start = pageSize * (currentPage - 1);
        StringBuilder sql = new StringBuilder("SELECT " +
                " ac.id, " +
                " ac.NAME, " +
                " ac.description, " +
                " ac.business_id, " +
                " ac.creator_id, " +
                " ac.creator_name, " +
                " ac.is_private, " +
                " ac.likes, " +
                " ac.views, " +
                " ac.create_time, " +
                " ac.update_time, " +
                " bt.`name` AS business_name " +
                "FROM " +
                " blp_application_case ac " +
                " LEFT JOIN blp_business_type bt ON ac.business_id = bt.id " +
                "WHERE 1=1 ");

        if (!StringUtils.isEmpty(applicationCaseQuery.getName())) {
            sql.append(" and ac.name like '%").append(applicationCaseQuery.getName()).append("%'");
        }
        if (!StringUtils.isEmpty(applicationCaseQuery.getId())) {
            sql.append(" and ac.id = ").append(applicationCaseQuery.getId());
        }
        if (!StringUtils.isEmpty(applicationCaseQuery.getBusinessId())) {
            sql.append(" and ac.business_id = ").append(applicationCaseQuery.getBusinessId());
        }
        if (!StringUtils.isEmpty(applicationCaseQuery.getCreatorId())) {
            sql.append(" and ac.creator_id = ").append(applicationCaseQuery.getCreatorId());
        }
        if (!StringUtils.isEmpty(applicationCaseQuery.getCreatorName())) {
            sql.append(" and ac.creator_name = ").append(applicationCaseQuery.getCreatorName());
        }
        if (!StringUtils.isEmpty(applicationCaseQuery.getIsPrivate())) {
            sql.append(" and ac.is_private = ").append(applicationCaseQuery.getIsPrivate());
        }

        sql.append(" limit ? , ?");

        PreparedStatement pstm = connection.prepareStatement(sql.toString());
        if(pageSize == -1 && currentPage == -1){
            pstm.setInt(1, 0);
            int total = pageConditionApplicationCaseListTotal(connection, applicationCaseQuery);
            pstm.setInt(2, total);
        }else{
            pstm.setInt(1, start);
            pstm.setInt(2, pageSize);
        }
        ResultSet rs = pstm.executeQuery();
        List<ApplicationCase> applicationCaseList = new ArrayList<>();
        while (rs.next()) {
            ApplicationCase applicationCase = getApplicationCaseEntity(rs);
            applicationCase.setBusinessName(rs.getString("business_name"));
            applicationCaseList.add(applicationCase);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return applicationCaseList;
    }


    @Override
    public int pageConditionApplicationCaseListTotal(Connection connection, ApplicationCase applicationCaseQuery) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM blp_application_case WHERE 1 = 1 ");

        if (!StringUtils.isEmpty(applicationCaseQuery.getName())) {
            sql.append(" and name like '%").append(applicationCaseQuery.getName()).append("%'");
        }
        if (!StringUtils.isEmpty(applicationCaseQuery.getId())) {
            sql.append(" and id = ").append(applicationCaseQuery.getId());
        }
        if (!StringUtils.isEmpty(applicationCaseQuery.getBusinessId())) {
            sql.append(" and business_id = ").append(applicationCaseQuery.getBusinessId());
        }
        if (!StringUtils.isEmpty(applicationCaseQuery.getCreatorId())) {
            sql.append(" and creator_id = ").append(applicationCaseQuery.getCreatorId());
        }
        if (!StringUtils.isEmpty(applicationCaseQuery.getCreatorName())) {
            sql.append(" and creator_name = ").append(applicationCaseQuery.getCreatorName());
        }
        if (!StringUtils.isEmpty(applicationCaseQuery.getIsPrivate())) {
            sql.append(" and is_private = ").append(applicationCaseQuery.getIsPrivate());
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
    public List<ApplicationCase> hotApplicationCaseList(Connection connection) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT " +
                " ac.id, " +
                " ac.NAME, " +
                " ac.description, " +
                " ac.business_id, " +
                " ac.creator_id, " +
                " ac.creator_name, " +
                " ac.is_private, " +
                " ac.likes, " +
                " ac.views, " +
                " ac.create_time, " +
                " ac.update_time, " +
                " bt.`name` AS business_name  " +
                "FROM " +
                " blp_application_case ac " +
                " LEFT JOIN blp_business_type bt ON ac.business_id = bt.id  " +
                "ORDER BY " +
                " views DESC  " +
                " LIMIT 0,9");

        PreparedStatement pstm = connection.prepareStatement(sql.toString());

        ResultSet rs = pstm.executeQuery();
        List<ApplicationCase> applicationCaseList = new ArrayList<>();
        while (rs.next()) {
            ApplicationCase applicationCase = getApplicationCaseEntity(rs);
            applicationCase.setBusinessName(rs.getString("business_name"));
            applicationCaseList.add(applicationCase);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return applicationCaseList;
    }

    @Override
    public ApplicationCase getApplicationCaseById(Connection connection, long applicationCaseId) throws SQLException {

        StringBuilder sql = new StringBuilder("SELECT " +
                " ac.id, " +
                " ac.name, " +
                " ac.description, " +
                " ac.business_id, " +
                " ac.creator_id, " +
                " ac.creator_name, " +
                " ac.is_private, " +
                " ac.likes, " +
                " ac.views, " +
                " ac.create_time, " +
                " ac.update_time, " +
                " bt.`name` AS business_name, " +
                " abr.band_id, " +
                " abr.band_name  " +
                "FROM " +
                " blp_application_case ac " +
                " LEFT JOIN blp_business_type bt ON ac.business_id = bt.id " +
                " LEFT JOIN blp_application_band_relation abr ON ac.id = abr.app_case_id  " +
                "WHERE " +
                " ac.id = ?");

        PreparedStatement pstm = connection.prepareStatement(sql.toString());
        pstm.setLong(1, applicationCaseId);
        ResultSet rs = pstm.executeQuery();

        ApplicationCase applicationCase = null;
        List<Band> bandList = new ArrayList();
        int index = -1;
        while (rs.next()) {
            index++;
            if(index == 0){
                applicationCase = getApplicationCaseEntity(rs);
                applicationCase.setBusinessName(rs.getString("business_name"));
            }
            long bandId = rs.getLong("band_id");
            String bandName = rs.getString("band_name");
            Band band = new Band(bandId, bandName);
            bandList.add(band);
        }
        applicationCase.setBandList(bandList);
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return applicationCase;
    }

    private ApplicationCase getApplicationCaseEntity(ResultSet rs) throws SQLException {
        ApplicationCase applicationCase = new ApplicationCase();
        applicationCase.setId(rs.getLong("id"));
        applicationCase.setName(rs.getString("name"));
        applicationCase.setDescription(rs.getString("description"));
        applicationCase.setBusinessId(rs.getLong("business_id"));
        applicationCase.setCreatorId(rs.getLong("creator_id"));
        applicationCase.setCreatorName(rs.getString("creator_name"));
        applicationCase.setIsPrivate(rs.getString("is_private"));
        applicationCase.setLikes(rs.getLong("likes"));
        applicationCase.setViews(rs.getLong("views"));
        applicationCase.setCreateTime(rs.getTimestamp("create_time"));
        applicationCase.setUpdateTime(rs.getTimestamp("update_time"));

        return applicationCase;
    }

}