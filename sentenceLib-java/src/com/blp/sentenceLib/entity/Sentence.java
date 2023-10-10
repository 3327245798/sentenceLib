package com.blp.sentenceLib.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * (Sentence)实体类
 *
 * @author makejava
 * @since 2022-05-07 10:25:22
 */
@Data
@Table(name = "blp_sentence")
public class Sentence implements Serializable {
    private static final long serialVersionUID = -47144095328793967L;
    /**
     * 句型id
     */
    @Id
    @Column(name = "id")
    private Long id;


    /**
     * 句型库类型 0:草稿剧本句型库 1:分析剧本句型库
     */
    @Column(name = "type")
    private String type;
    /***
     *句型库转态，句型有5种状态，0表示审核中，1表示审核未通过，
     * 2表示审核通过但未开发，3表示已开发并启用，4表示禁用
     */
    @Column(name = "status")
    private int status;
    /**
     * 句型名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 序列化的句型结构体
     */
    @Column(name = "serialization")
    private String serialization;
    /**
     * 句型实例
     */
    @Column(name = "example")
    private String example;
    /**
     * 句型本身
     */
    @Column(name = "description")
    private String description;
    /**
     * 句型创建者id
     */
    @Column(name = "creator_id")
    private Long creatorId;
    /**
     * 句型创建者名称
     */
    @Column(name = "creator_name")
    private String creatorName;
    /**
     * 句型创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 句型更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;
    /*记录输入参数的顺序*/
    @Column(name = "sequence")
    private String sequence;

    /*工具id和部件action的拼接，可以唯一确定一个部件*/
    @Column(name = "toolId_action")
    private String toolIdAndAction;
    /*句型对应固件的文本描述unit_text*/
    @Column(name = "unit_text")
    private String unitText;
    /*sentence_description
    * 句型描述包括句型介绍、固件的执行过程概述、预期执行结果，比如描述为创建一个术语（或称为变量），
    * 并为其赋值。固件简述为以设备序列号为名，XY0001为值形成词汇放入到动态语境中。
    * 预期执行结果为在动态语境添加了名称为设备序列号，值为XY0001的词汇。*/
    @Column(name = "sentence_description")
    private String sentenceDescription;



}