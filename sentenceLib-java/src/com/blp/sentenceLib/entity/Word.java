package com.blp.sentenceLib.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Data
@Table(name="bls_word")
public class Word implements Serializable {
    @Id
    @Column(name="id")
    /**该词汇id*/
    private  Long id;

    @Column(name="name")
    /**该词汇名称*/
    private String name;

    @Column(name="creator_id")
    /**该词汇创建者id*/
    private Long creatorId;

    @Column(name="creator_name")
    /**该词汇创建者姓名*/
    private String creatorName;

    @Column(name="status")
    /**该词汇状态，0表示禁用，1表示审查中，2表示审查不通过，3表示审查通过并启用*/
    private int status;
    /**所属场景id，已经删除了*/
    /*@Column(name="band_obj_id")
    private Long bandObjId;*/

    @Column(name="description")
    /**词汇描述，比如对天气工具的当天天气部件调用|对开关灯工具的开灯部件调用|对萤石喇叭的调用|对萤石喇叭调用的参数名*/
    private String description;

    @Column(name="create_time")
    /**创建时间*/
    private Date createTime;

    @Column(name="update_time")
    /**更新时间*/
    private Date updateTime;

    @Column(name="meaning")
    /**词汇含义，action调用的一些url或者参数名*/
    private String meaning;
    /*词汇绑定的固件id*/
    @Column(name="firmware_id")
    private Long firmwareId;
    /*type，用来区分是meaning还是firmwareId*/
    @Column(name="type")
    private Integer type;


    /**
     * 业务属性：tool_name，unit_name toolId  unitlId  unitType
     */

    private String toolName;

    private String unitName;
    private Long toolId;
    private Long unitId;
    private int unitType;
    /*业务属性*/
    private String firmwareName;
    private Integer firmwareType;
    private String firmwareCode;




}
