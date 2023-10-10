package com.blp.sentenceLib.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * (MessageBoard)实体类
 *
 * @author makejava
 * @since 2022-02-28 17:02:44
 */
@Data
@Table(name = "blp_message_board")
public class MessageBoard implements Serializable {
    private static final long serialVersionUID = -45243627783858196L;
    /**
     * 留言板id
     */
    @Id
    @Column(name = "id")
    private Long id;
    /**
     * 所属的应用案例
     */
    @Column(name = "application_case_id")
    private Long applicationCaseId;
    /**
     * 上条留言id  0:主楼层   
     */
    @Column(name = "parent_id")
    private Long parentId;
    /**
     * 留言内容
     */
    @Column(name = "content")
    private String content;
    /**
     * 逻辑删除（0-未删除；1-已删除）
     */
    @Column(name = "is_deleted")
    private String isDeleted = "0";
    /**
     * 留言用户id
     */
    @Column(name = "user_id")
    private Long userId;
    /**
     * 留言用户名称
     */
    @Column(name = "user_name")
    private String userName;
    /**
     * 留言时间
     */
    @Column(name = "time")
    private Date time;

    // 业务数据
    private List<MessageBoard> children;

}

