package com.blp.sentenceLib.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * (DraftChapterConstant)实体类
 *
 * @author makejava
 * @since 2022-02-28 17:01:04
 */
@Data
@Table(name = "blp_draft_chapter")
public class DraftChapter implements Serializable {
    private static final long serialVersionUID = -99927755422612809L;
    /**
     * 草稿章节id
     */
    @Id
    @Column(name = "id")
    private Long id;
    /**
     * 所属草稿剧本id
     */
    @Column(name = "draft_id")
    private Long draftId;
    /**
     * 上一草稿章节id 0:头草稿章节
     */
    @Column(name = "parent_id")
    private Long parentId;
    /**
     * 草稿章节内容
     */
    @Column(name = "content")
    private String content;
    /**
     * 草稿章节结构序列化
     */
    @Column(name = "serialization")
    private String serialization;

    /**
     * 草稿章节是否已开发 0:未开发    1:已开发
     */
    @Column(name = "is_developed")
    private String isDeveloped = "0";
    /**
     * 已开发的草稿章节对应的分析剧本
     */
    @Column(name = "analyse_id")
    private Long analyseId;

    // 业务属性
    private List<DraftChapter> children;

    private Analyse analyse;

    // 草稿剧本名称
    private String title;

    // 草稿剧本描述
    private String description;

    // 创建时间
    private Date createTime;

    // 修改时间
    private Date updateTime;

    // 创建者名称
    private String creatorName;
}

