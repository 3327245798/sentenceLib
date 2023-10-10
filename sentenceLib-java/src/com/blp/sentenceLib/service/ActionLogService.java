package com.blp.sentenceLib.service;

import com.blp.sentenceLib.entity.ActionLog;

import java.util.List;

/**
 * (ActionLog)表服务接口
 *
 * @author makejava
 * @since 2022-02-28 17:01:39
 */
public interface ActionLogService {
    List<ActionLog> pageConditionActionLogList(ActionLog actionLogQuery, Integer pageSize, Integer currentPage) throws Exception;

    Integer pageConditionActionLogListTotal(ActionLog actionLogQuery) throws Exception;

    void insertActionLog(ActionLog actionLog) throws Exception;

    int updateActionLog(ActionLog actionLog) throws Exception;

    int deleteActionLog(ActionLog actionLog) throws Exception;
}
