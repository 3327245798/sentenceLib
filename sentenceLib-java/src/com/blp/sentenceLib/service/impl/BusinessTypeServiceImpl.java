package com.blp.sentenceLib.service.impl;

import com.fy.basejar.tool.ActionToolBase;
import com.blp.sentenceLib.dao.BusinessTypeDao;
import com.blp.sentenceLib.entity.BusinessType;
import com.blp.sentenceLib.service.BusinessTypeService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

/**
 * (BusinessType)表服务实现类
 *
 * @author makejava
 * @since 2022-02-28 16:59:50
 */
@Service
@NoArgsConstructor
public class BusinessTypeServiceImpl implements BusinessTypeService {

    @Autowired
    BusinessTypeDao businessTypeDao;

    @Override
    public List<BusinessType> pageConditionBusinessTypeList(BusinessType businessTypeQuery, int pageSize, int currentPage) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        List<BusinessType> businessTypeList = businessTypeDao.pageConditionBusinessTypeList(connection, businessTypeQuery, pageSize, currentPage);
        return businessTypeList;
    }

    @Override
    public Integer pageConditionBusinessTypeListTotal(BusinessType businessTypeQuery) throws Exception{
        final Connection connection = ActionToolBase.getDBConnection();
        Integer total = businessTypeDao.pageConditionBusinessTypeListTotal(connection, businessTypeQuery);
        return total;
    }

    @Override
    public int updateBusinessType(BusinessType businessType) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        int i = (int) businessTypeDao.updateIgnoreNull(connection, businessType);
        return i;
    }

    @Override
    public void insertBusinessType(BusinessType businessType) throws Exception{
        final Connection connection = ActionToolBase.getDBConnection();
        businessTypeDao.save(connection, businessType);
    }

    @Override
    public int deleteBusinessType(BusinessType businessType) throws Exception{
        final Connection connection = ActionToolBase.getDBConnection();
        int i = (int) businessTypeDao.delete(connection, businessType);
        return i;
    }

    @Override
    public List<BusinessType> pageAllBusinessTypeList() throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        List<BusinessType> businessTypeList = businessTypeDao.pageAllBusinessTypeList(connection);
        return businessTypeList;
    }

    @Override
    public Integer pageAllBusinessTypeListTotal() throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        Integer total = businessTypeDao.pageAllBusinessTypeListTotal(connection);
        return total;
    }
}
