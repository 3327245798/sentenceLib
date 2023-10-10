package com.blp.sentenceLib.entity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/*这个是新的词汇表，2022年10月21日谈论过后的，4个表之一，其余三个表是blp_firmware、
blp_firmware_request_attribute、blp_firmware_response_attribute*/
@Data
@Table(name="blp_word")
public class NewWord implements Serializable {
    //词汇id
    @Id
    @Column(name="id")
    private Long id;

    //词汇名称

    @Column(name="name")
    private String name;
    //词汇描述

    @Column(name="description")
    private String description;
    //词汇状态,0123分别表示审核中，启用中，未通过，禁用
    @Column(name="status")
    private byte status;

    //创建者姓名

    @Column(name="creator_name")
    private String creatorName;
    //创建者id

    @Column(name="creator_id")
    private String creatorId;
    //创建时间或更新时间

    @Column(name="update_time")
    private Date updateTime;
    //所属固件id

    @Column(name="firmware_id")
    private Long firmwareId;
    //所属固件类型

    @Column(name="firmware_type")
    private Long firmwareType;


}
