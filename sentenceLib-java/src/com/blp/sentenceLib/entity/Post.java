package com.blp.sentenceLib.entity;

import com.mysql.jdbc.Blob;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * (Post)实体类
 *
 * @author wz
 * @since 2022-10-15 17:00:42
 */
@Data
@Table(name = "blp_post")
public class Post implements Serializable {
    private static final long serialVersionUID = 384904563361861222L;
    /**
     * 帖子id
     */
    @Id
    @Column(name = "id")
    private Long id;

    /*帖子title*/
    @Column(name = "title")
    private  String title;
    /**
     * 帖子内容
     */
    @Column(name = "content")
    private String content;
    /**
     * 发帖人姓名
     */
    @Column(name = "creator_name")
    private String creatorName;
    /**
     * 帖子封面
     */
    @Column(name = "img")
    private byte[] img;
   /*帖子点赞数量*/
    @Column(name = "like_count")
    private int likeCount;
    /*帖子点踩数量*/
    @Column(name = "dislike_count")
    private int dislikeCount;
    /*帖子评论数量*/
    @Column(name = "comment_count")
    private int commentCount;
    /*帖子收藏数量*/
    @Column(name = "collection_count")
    private int collectionCount;
    /*帖子创建时间*/
    @Column(name = "create_time")
    private Date createTime;


}

