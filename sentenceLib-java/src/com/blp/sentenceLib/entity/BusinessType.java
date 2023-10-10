package com.blp.sentenceLib.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * (BusinessType)实体类
 *
 * @author makejava
 * @since 2022-02-28 16:59:41
 */
@Data
@Table(name = "blp_business_type")
public class BusinessType implements Serializable {
    private static final long serialVersionUID = -97384448456314027L;
    /**
     * 业务类型表id
     */
    @Id
    @Column(name = "id")
    private Long id;
    /**
     * 业务类型名称
     */
    @Column(name = "name")
    private String name;

}

