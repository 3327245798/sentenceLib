package com.blp.sentenceLib.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * (Analyse)实体类
 *
 * @author makejava
 * @since 2022-02-28 17:01:20
 */
@Data
@Table(name = "blp_analyse")
public class Analyse implements Serializable {
    private static final long serialVersionUID = -41372470601138979L;
    /**
     * 分析剧本id
     */
    @Id
    @Column(name = "id")
    private Long id;
    /**
     * 分析剧本可执行语句
     */
    @Column(name = "content")
    private String content;
    /**
     * 分析剧本结构序列化
     */
    @Column(name = "serialization")
    private String serialization;
    /**
     * 运行状态 0:未运行    1:已运行
     */
    @Column(name = "status")
    private String status = "0";
    /**
     * 开发者id
     */
    @Column(name = "developer_id")
    private Long developerId;
    /**
     * 开发者名称
     */
    @Column(name = "developer_name")
    private String developerName;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    // 属性
    @Column(name = "draft_chapter_id")
    private Long draftChapterId;
    //在分析剧本表根据sentenceId找固件
    @Column(name = "sentence_id")
    private Long sentenceId;
}

