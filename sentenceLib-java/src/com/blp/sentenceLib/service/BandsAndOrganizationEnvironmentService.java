package com.blp.sentenceLib.service;

import java.util.Map;

public interface BandsAndOrganizationEnvironmentService {

    Long getOrganizationIdByBandId(Long objId) throws Exception;
    Map<String, Object> getOrganizationAndBands(Long objId) throws Exception;

    Map<String, Object> getOrganizationEnvironment(Long organizationId) throws Exception;
    Map<String, Object> getDepartmentEnvironment(Long departmentId) throws Exception;
    Map<String, Object> getBandEnvironment(Long bandId, Long realObjId) throws Exception;
    Map<String, Object> getScriptEnvironment(Long scriptId) throws Exception;
}
