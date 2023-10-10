package com.blp.sentenceLib.entity;

import com.mysql.jdbc.Blob;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * (Comment)实体类
 *
 * @author wz
 * @since 2022-10-15 17:00:42
 */
@Data
@Table(name = "blp_comment")
public class Comment implements Serializable {
    private static final long serialVersionUID = 384904563361861222L;
    /**
     * 评论id
     */
    @Id
    @Column(name = "id")
    private Long id;

    /*该评论对应的帖子id*/
    @Column(name = "post_id")
    private  Long PostId;
    /**
     * 评论者姓名
     */
    @Column(name = "commentor_name")
    private String commentorName;
    /**
     * 评论内容
     */
    @Column(name = "content")
    private String content;
    /**
     * 皮评论时间
     */
    @Column(name = "comment_time")
    private Date commentTime;
   /*评论者的头像*/
    @Column(name = "avatar")
    private byte[] avatar;


}

