package com.blp.sentenceLib.service;

import com.blp.sentenceLib.entity.BusinessType;

import java.util.List;

/**
 * (BusinessType)表服务接口
 *
 * @author makejava
 * @since 2022-02-28 16:59:48
 */
public interface BusinessTypeService {

    List<BusinessType> pageConditionBusinessTypeList(BusinessType businessTypeQuery, int pageSize, int currentPage) throws Exception;

    Integer pageConditionBusinessTypeListTotal(BusinessType businessTypeQuery) throws Exception;

    int updateBusinessType(BusinessType businessType) throws Exception;

    void insertBusinessType(BusinessType businessType) throws Exception;

    int deleteBusinessType(BusinessType businessType) throws Exception;

    List<BusinessType> pageAllBusinessTypeList() throws Exception;

    Integer pageAllBusinessTypeListTotal() throws Exception;
}
