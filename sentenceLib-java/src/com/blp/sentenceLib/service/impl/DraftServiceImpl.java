package com.blp.sentenceLib.service.impl;

import com.fy.basejar.tool.ActionToolBase;
import com.blp.sentenceLib.dao.DraftDao;
import com.blp.sentenceLib.entity.Draft;
import com.blp.sentenceLib.service.DraftService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

/**
 * (Draft)表服务实现类
 *
 * @author makejava
 * @since 2022-02-28 17:00:42
 */
@Service
@NoArgsConstructor
public class DraftServiceImpl implements DraftService {

    @Autowired
    DraftDao draftDao;

    @Override
    public List<Draft> getDraftList() throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        List<Draft> draftList = draftDao.getDraftList(connection);
        return draftList;
    }

    @Override
    public List<Draft> pageConditionDraftList(Draft draftQuery, Integer pageSize, Integer currentPage) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        List<Draft> draftList = draftDao.pageConditionDraftList(connection, draftQuery, pageSize, currentPage);
        return draftList;
    }

    @Override
    public List<Draft> getDraftListByAppcaseId(Long appcaseId) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        List<Draft> draftList = draftDao.getDraftListByAppcaseId(connection, appcaseId);
        return draftList;
    }

    @Override
    public Integer pageConditionDraftListTotal(Draft draftQuery) throws Exception{
        final Connection connection = ActionToolBase.getDBConnection();
        Integer total = draftDao.pageConditionDraftListTotal(connection, draftQuery);
        return total;
    }

    @Override
    public int updateDraft(Draft draft) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        int i = (int) draftDao.updateIgnoreNull(connection, draft);
        return i;
    }

    @Override
    public void insertDraft(Draft draft) throws Exception{
        final Connection connection = ActionToolBase.getDBConnection();
        draftDao.save(connection, draft);
    }

    @Override
    public int deleteDraft(Draft draft) throws Exception{
        final Connection connection = ActionToolBase.getDBConnection();
        int i = (int) draftDao.delete(connection, draft);
        return i;
    }

    @Override
    public Draft getDraftById(Long draftId) throws Exception{
        final Connection connection = ActionToolBase.getDBConnection();
        return draftDao.getDraftById(connection, draftId);
    }

}
