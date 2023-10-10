package com.blp.sentenceLib.entity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/*这参数表，2022年11月14日谈论过后的，2个表之一，
这2个表是blp_param、blp_firmware*/

@Data
@Table(name="blp_firmware")
public class NewParam implements Serializable {


    //参数id
    @Id
    @Column(name="id")
    private Long id;

    /**参数输入输出类型
     * 1 输入；2输出参数
     */
    @Column(name="in_out_type")
    private Integer inOutType;

    /**
     * 参数字段,英文的
     */
    @Column(name="name")
    private String name;

    /**
     * 参数字段中文名
     */
    @Column(name="cn_name")
    private String cnName;

    /**
     * 参数描述
     */
    @Column(name="desc")
    private String desc;

    /**
     * 对应的固件id
     */
    @Column(name="firmware_id")
    private Long firmwareId;

    /**
     * 默认值
     */
    @Column(name="default_value")
    private String defaultValue;

    /**
     * 是否必填字段（0非必填；1必填）
     */
    @Column(name="required")
    private Integer required;

    /**
     * 参数存放位置，类型（1 url；2 body；3 form；4.cookie，5、header、）
     */
    @Column(name="exist_type")
    private Integer existType;

    /**
     * 子参数的的父参数id，对于对象类型，比如发消息部件的一条语句，用到了消息message这个对象，使用到了里面两个参数，chatRoomId（聊天室id）和content（消息内容）
     */
    @Column(name="parent_id")
    private Long parentId;

    /**
     * 记录返回结果的 解析路径 如 status = result.data.status 保存的时候 写入
     */
    @Column(name="param_path")
    private String parmaPath;

    /**
     * 参数类型 1、int  ， 2、float，3、String ，4、Data  5、boolean 、 5、object 、6  list
     */
    @Column(name="obj_type")
    private Integer objType;






}
