package com.blp.sentenceLib.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * (DraftPublish)实体类
 *
 * @since 2022-07-14 15:23:20
 */
@Data
@Table(name = "blp_draft_release")
public class DraftRelease implements Serializable {


    /**
     * 唯一标识
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 已发布剧本的id
     */
    @Column(name = "draft_id")
    private Long draftId;

    /**
     * 发布时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 发布者的id
     */
    @Column(name = "creator_id")
    private Long creatorId;

    /**
     * 发布者的名称
     */
    @Column(name = "creator_name")
    private String creatorName;

    /**
     * 发布时剧本的版本
     */
    @Column(name = "version")
    private Long version;


    /**
     * 业务属性
     * 只在java实体对象中定义，不写在数据库中
     */
    private String name;
    private String type;
    private Long chapterId;


}
