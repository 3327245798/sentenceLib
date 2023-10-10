package com.blp.sentenceLib.service.impl;

import com.fy.basejar.tool.ActionToolBase;
import com.blp.sentenceLib.annotation.LogOperate;
import com.blp.sentenceLib.dao.ActionLogDao;
import com.blp.sentenceLib.entity.ActionLog;
import com.blp.sentenceLib.enums.LogOperateType;
import com.blp.sentenceLib.service.ActionLogService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

/**
 * (ActionLog)表服务实现类
 *
 * @author makejava
 * @since 2022-02-28 17:01:39
 */
@Service
@NoArgsConstructor
public class ActionLogServiceImpl implements ActionLogService {

    @Autowired
    ActionLogDao actionLogDao;

    @Override
    @LogOperate(value = "分页条件查询操作日志",type = LogOperateType.QUERY)
    public List<ActionLog> pageConditionActionLogList(ActionLog actionLogQuery, Integer pageSize, Integer currentPage)
        throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        List<ActionLog> actionLogList =
            actionLogDao.pageConditionActionLogList(connection, actionLogQuery, pageSize, currentPage);
        return actionLogList;
    }

    @Override
    public Integer pageConditionActionLogListTotal(ActionLog actionLogQuery) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        Integer total = actionLogDao.pageConditionActionLogListTotal(connection, actionLogQuery);
        return total;
    }

    @Override
    public int updateActionLog(ActionLog actionLog) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        int i = (int)actionLogDao.updateIgnoreNull(connection, actionLog);
        return i;
    }

    @Override
    public void insertActionLog(ActionLog actionLog) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        actionLogDao.save(connection, actionLog);
    }

    @Override
    public int deleteActionLog(ActionLog actionLog) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        int i = (int)actionLogDao.delete(connection, actionLog);
        return i;
    }

}
