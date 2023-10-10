package com.blp.sentenceLib.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * (ActionLog)实体类
 *
 * @author makejava
 * @since 2022-02-28 17:01:39
 */
@Data
@Table(name = "blp_action_log")
public class ActionLog implements Serializable {
    private static final long serialVersionUID = -67055580721677692L;
    /**
     * 操作日志表id
     */
    @Id
    @Column(name = "id")
    private Long id;
    /**
     * 操作行为
     */
    @Column(name = "action")
    private String action;
    /**
     * 显示操作的具体内容
     */
    @Column(name = "content")
    private String content;
    /**
     * 被操作的实体类型
     */
    @Column(name = "type")
    private String type;
    /**
     * 所属的应用案例
     */
    @Column(name = "application_case_name")
    private String applicationCaseName;
    /**
     * 操作者id
     */
    @Column(name = "user_id")
    private Long userId;
    /**
     * 操作者名称
     */
    @Column(name = "user_name")
    private String userName;
    /**
     * 操作时间
     */
    @Column(name = "time")
    private Date time;

}

