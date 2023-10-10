package com.blp.sentenceLib.dao;

import com.blp.sentenceLib.entity.BusinessType;
import com.fy.toolhelper.db.IBaseDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * (BusinessType)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-28 16:59:33
 */
public interface BusinessTypeDao extends IBaseDao<BusinessType> {

    List<BusinessType> pageConditionBusinessTypeList(Connection connection, BusinessType businessTypeQuery, Integer pageSize, Integer currentPage) throws SQLException;

    int pageConditionBusinessTypeListTotal(Connection connection, BusinessType businessTypeQuery) throws SQLException;

    List<BusinessType> pageAllBusinessTypeList(Connection connection) throws SQLException;

    Integer pageAllBusinessTypeListTotal(Connection connection) throws SQLException;
}

