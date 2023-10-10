package com.blp.sentenceLib.dao.impl;

import com.blp.sentenceLib.dao.BusinessTypeDao;
import com.blp.sentenceLib.entity.BusinessType;
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
public class BusinessTypeDaoImpl extends BaseDaoImpl<BusinessType> implements BusinessTypeDao {
    public BusinessTypeDaoImpl() throws Exception {
    }

    @Override
    public List<BusinessType> pageConditionBusinessTypeList(Connection connection, BusinessType businessTypeQuery, Integer pageSize, Integer currentPage) throws SQLException {
        int start = pageSize * (currentPage - 1);
        StringBuilder sql = new StringBuilder("SELECT * FROM blp_business_type WHERE 1 = 1 ");

        if (!StringUtils.isEmpty(businessTypeQuery.getName())) {
            sql.append(" and name like '%").append(businessTypeQuery.getName()).append("%'");
        }

        sql.append(" limit ? , ?");

        PreparedStatement pstm = connection.prepareStatement(sql.toString());
        if(pageSize == -1 && currentPage == -1){
            pstm.setInt(1, 0);
            int total = pageConditionBusinessTypeListTotal(connection, businessTypeQuery);
            pstm.setInt(2, total);
        }else{
            pstm.setInt(1, start);
            pstm.setInt(2, pageSize);
        }
        ResultSet rs = pstm.executeQuery();
        List<BusinessType> businessTypeList = new ArrayList<>();
        while (rs.next()) {
            BusinessType businessType = new BusinessType();
            BusinessType businessTypeEntity = getBusinessTypeEntity(rs);
            BeanUtils.copyProperties(businessTypeEntity, businessType);
            businessTypeList.add(businessType);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return businessTypeList;
    }

    @Override
    public int pageConditionBusinessTypeListTotal(Connection connection, BusinessType businessTypeQuery) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM blp_business_type WHERE 1 = 1 ");

        if (!StringUtils.isEmpty(businessTypeQuery.getName())) {
            sql.append(" and name like '%").append(businessTypeQuery.getName()).append("%'");
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
    public List<BusinessType> pageAllBusinessTypeList(Connection connection) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM blp_business_type");

        PreparedStatement pstm = connection.prepareStatement(sql.toString());

        ResultSet rs = pstm.executeQuery();
        List<BusinessType> businessTypeList = new ArrayList<>();
        while (rs.next()) {
            BusinessType businessType = new BusinessType();
            BusinessType businessTypeEntity = getBusinessTypeEntity(rs);
            BeanUtils.copyProperties(businessTypeEntity, businessType);
            businessTypeList.add(businessType);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return businessTypeList;
    }

    @Override
    public Integer pageAllBusinessTypeListTotal(Connection connection) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM blp_business_type");

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

    private BusinessType getBusinessTypeEntity(ResultSet rs) throws SQLException {
        BusinessType businessType = new BusinessType();
        businessType.setId(rs.getLong("id"));
        businessType.setName(rs.getString("name"));
        return businessType;
    }
}