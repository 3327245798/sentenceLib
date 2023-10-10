package com.blp.sentenceLib.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * (ApplicationCase)实体类
 *
 * @author makejava
 * @since 2022-02-28 17:01:59
 */
@ToString
@Data
@Table(name = "blp_application_case")
public class ApplicationCase implements Serializable {
    private static final long serialVersionUID = 138894147135651031L;
    /**
     * 应用案例表id
     */
    @Id
    @Column(name = "id")
    private Long id;
    /**
     * 应用案例名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 应用案例描述
     */
    @Column(name = "description")
    private String description;
    /**
     * 所属的业务类型id
     */
    @Column(name = "business_id")
    private Long businessId;

    /**
     * 应用案例创建者id
     */
    @Column(name = "creator_id")
    private Long creatorId;
    /**
     * 应用案例创建者名称
     */
    @Column(name = "creator_name")
    private String creatorName;
    /**
     * 应用案例是否公开  0:公开  1:私有
     */
    @Column(name = "is_private")
    private String isPrivate;
    /**
     * 点赞量
     */
    @Column(name = "likes")
    private Long likes = 0L;
    /**
     * 浏览量
     */
    @Column(name = "views")
    private Long views = 0L;
    /**
     * 应用案例创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 应用案例修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /*
    *  业务属性
    * */
    /*
    *  应用案例相关帮区列表
    * */
    private List<Band> bandList;
    /**
     * 所属的业务类型name
     */
    private String businessName;
}

