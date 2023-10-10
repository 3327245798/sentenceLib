package com.blp.sentenceLib.service.impl;

import com.fy.basejar.tool.ActionToolBase;
import com.blp.sentenceLib.dao.ApplicationCaseDao;
import com.blp.sentenceLib.entity.ApplicationCase;
import com.blp.sentenceLib.service.ApplicationCaseService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

/**
 * (ApplicationCase)表服务实现类
 *
 * @author makejava
 * @since 2022-02-28 17:01:59
 */
@Service
@NoArgsConstructor
public class ApplicationCaseServiceImpl implements ApplicationCaseService {


    @Autowired
    ApplicationCaseDao applicationCaseDao;
//根据业务类型id获取该业务类型的所有案例
    @Override
    public List<ApplicationCase> getAppcaseByBusinessId(Long busineeTypeId) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        List<ApplicationCase> applicationCaseList =applicationCaseDao.getAppcaseByBusinessId(connection,busineeTypeId);
        return applicationCaseList;
    }

    @Override
    public List<ApplicationCase> pageConditionApplicationCaseList(ApplicationCase applicationCaseQuery, Integer pageSize, Integer currentPage) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        List<ApplicationCase> applicationCaseList = applicationCaseDao.pageConditionApplicationCaseList(connection, applicationCaseQuery, pageSize, currentPage);
        return applicationCaseList;
    }

    @Override
    public Integer pageConditionApplicationCaseListTotal(ApplicationCase applicationCaseQuery) throws Exception{
        final Connection connection = ActionToolBase.getDBConnection();
        Integer total = applicationCaseDao.pageConditionApplicationCaseListTotal(connection, applicationCaseQuery);
        return total;
    }

    @Override
    public int updateApplicationCase(ApplicationCase applicationCase) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        int i = (int) applicationCaseDao.updateIgnoreNull(connection, applicationCase);
        return i;
    }

    @Override
    public void insertApplicationCase(ApplicationCase applicationCase) throws Exception{
        final Connection connection = ActionToolBase.getDBConnection();
        applicationCaseDao.save(connection, applicationCase);
    }

    @Override
    public int deleteApplicationCase(ApplicationCase applicationCase) throws Exception{
        final Connection connection = ActionToolBase.getDBConnection();
        int i = (int) applicationCaseDao.delete(connection, applicationCase);
        return i;
    }

    @Override
    public List<ApplicationCase> hotApplicationCaseList() throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        List<ApplicationCase> applicationCaseList = applicationCaseDao.hotApplicationCaseList(connection);
        return applicationCaseList;
    }

    @Override
    public ApplicationCase getApplicationCaseById(Long applicationCaseId) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        ApplicationCase applicationCase = applicationCaseDao.getApplicationCaseById(connection, applicationCaseId);
        return applicationCase;
    }
}
