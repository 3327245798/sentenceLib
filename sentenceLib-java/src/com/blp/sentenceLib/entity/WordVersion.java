package com.blp.sentenceLib.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name="blp_word_version")
public class WordVersion implements Serializable {
    @Id
    @Column
    /**id*/
    private  Long id;
    /*词汇word_id,标识某一个词汇，用于追溯该词汇的版本*/
    @Column
    private Long word_id;
    /*词汇名称,*/
    @Column
    private String name;
    /*词汇新版本的创建时间*/
    @Column
    private Date createTime;
    /*词汇描述*/
    @Column
    private String description;
    /*词汇新版本的创建者id*/
    @Column
    private Long creatorId;
    /*词汇新版本的创建者姓名*/
    @Column
    private String creatorName;
    /*词汇新版本*/
    @Column
    private Long version;



}
