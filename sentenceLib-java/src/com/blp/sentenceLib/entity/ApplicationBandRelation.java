package com.blp.sentenceLib.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * (ApplicationBandRelation)实体类
 *
 * @author makejava
 * @since 2022-02-28 17:02:27
 */
@Data
@Table(name = "blp_application_band_relation")
public class ApplicationBandRelation implements Serializable {
    private static final long serialVersionUID = -64394666069627124L;

    /*
    *  关联表id
    * */
    @Id
    @Column(name = "id")
    private Long id;
    /**
     * 应用案例id
     */
    @Column(name = "app_case_id")
    private Long appCaseId;
    /**
     * 相关帮区id
     */
    @Column(name = "band_id")
    private Long bandId;
    /**
     * 相关帮区名称
     */
    @Column(name = "band_name")
    private String bandName;

}

