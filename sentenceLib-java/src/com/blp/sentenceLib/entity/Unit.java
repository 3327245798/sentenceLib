package com.blp.sentenceLib.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * (Unit)实体类
 *
 * @author makejava
 * @since 2022-05-06 20:34:27
 */
@Data
@Table(name = "blp_unit")
public class Unit implements Serializable {
    private static final long serialVersionUID = 998356296773326355L;
    /**
     * 部件id
     */
    @Id
    @Column(name = "id")
    private Long id;
    /**
     * 所属工具名称
     */
    @Column(name = "tool_name")
    private String toolName;
    /**
     * 所属工具id
     */
    @Column(name = "tool_id")
    private Long toolId;
    /**
     * 部件英文名称
     */
    @Column(name = "action")
    private String action;
    /**
     * 部件中文名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 参数实体类型
     */
    @Column(name = "clazz")
    private String clazz;
    /**
     * 参数实体字段序列化内容
     */
    @Column(name = "params")
    private String params;

    /**
     * 执行结果字段序列化内容
     */
    @Column(name = "result")
    private String result;
    /**
     * 固件类别，固件类型，(词汇绑定时可以进行选择)
     * 目前设计了五种，值、工具、页面、web service、微服务，用01234分别表示
     */
    @Column(name = "unit_type")
    private int unitType;
    /**
     * 固件绑定的词汇id
     */
    @Column(name = "word_id")
    private Long wordId;


}