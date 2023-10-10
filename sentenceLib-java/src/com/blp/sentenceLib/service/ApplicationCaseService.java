package com.blp.sentenceLib.service;

import com.blp.sentenceLib.entity.ApplicationCase;

import java.util.List;

/**
 * (ApplicationCase)表服务接口
 *
 * @author makejava
 * @since 2022-02-28 17:01:59
 */
public interface ApplicationCaseService {
    List<ApplicationCase> getAppcaseByBusinessId(Long busineeTypeId) throws Exception;

    List<ApplicationCase> pageConditionApplicationCaseList(ApplicationCase applicationCaseQuery, Integer pageSize, Integer currentPage) throws Exception;

    Integer pageConditionApplicationCaseListTotal(ApplicationCase applicationCaseQuery) throws Exception;

    void insertApplicationCase(ApplicationCase applicationCase) throws Exception;

    int updateApplicationCase(ApplicationCase applicationCase) throws Exception;

    int deleteApplicationCase(ApplicationCase applicationCase) throws Exception;

    List<ApplicationCase> hotApplicationCaseList() throws Exception;

    ApplicationCase getApplicationCaseById(Long applicationCaseId) throws Exception;
}
