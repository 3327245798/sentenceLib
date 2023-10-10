package com.blp.sentenceLib.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * (Draft)实体类
 *
 * @author makejava
 * @since 2022-02-28 17:00:42
 */
@Data
@Table(name = "blp_draft")
public class Draft implements Serializable {
    private static final long serialVersionUID = 384904563361861222L;
    /**
     * 草稿剧本id
     */
    @Id
    @Column(name = "id")
    private Long id;
    /**
     * 草稿剧本名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 草稿剧本描述
     */
    @Column(name = "description")
    private String description;
    /**
     * 所属的应用案例id
     */
    @Column(name = "application_case_id")
    private Long applicationCaseId;
    /**
     * 剧本类型，1：规范型；2：非规范型；3：混合型
     */
    @Column(name = "type")
    private String type;
    /**
     * 创建者id
     */
    @Column(name = "creator_id")
    private Long creatorId;
    /**
     * 创建者名称
     */
    @Column(name = "creator_name")
    private String creatorName;
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

}

