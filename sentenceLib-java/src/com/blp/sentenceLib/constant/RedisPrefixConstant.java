package com.blp.sentenceLib.constant;

public enum RedisPrefixConstant {

    BAND_ENVIRONMENT("blp_band_environment:"),

    ORGANIZATION_ENVIRONMENT("blp_organization_environment:"),

    JOB_RESULT_MAP("blp_job_result_map:"),
    JOB("blp_job:"),
    NORMAL_JOBID_LIST("blp_normal_jobId_list:"),
    INTERRUPT_JOBID_LIST("blp_interrupt_jobId_list:");
    private String prefix;

    RedisPrefixConstant(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

}
