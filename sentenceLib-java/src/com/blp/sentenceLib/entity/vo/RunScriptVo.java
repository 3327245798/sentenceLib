package com.blp.sentenceLib.entity.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class RunScriptVo {

    private List<Script> scripts;

    private Long draftId;

    private Long userId;

    private String content;

    private Map<String, String> param;

    private Integer status;

    private Date createTime;

    private Date updateTime;

}
