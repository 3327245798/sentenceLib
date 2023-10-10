package com.blp.sentenceLib.entity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/*这固件表，2022年11月14日谈论过后的，2个表之一，
这2个表是blp_param、blp_firmware*/
@Data
@Table(name="blp_firmware")
public class Firmware implements Serializable {
    //固件id
    @Id
    @Column(name="id")
    private Long id;
    //部件名称
    @Column(name="firmware_name")
    private String firmwareName;
    /**
     * 工具id；都使用上架id；如果是普通工具，先根据上架id获取部署id；core和外部api没有工具id
     */
    @Column(name="tool_id")
    private Long toolId;
    //工具名称
    @Column(name="tool_name")
    private String toolName;
    //工具部件的action或webserve的api
    @Column(name="action_api")
    private String actionApi;

    /**
     * 固件类型：1 core；2 普通工具；3 系统工具（消息板、编辑器、编译器、剧本集工具、执行器，因为只需要逻辑安装）；4 外部api
     目前只用到234，因为1被封装成了一个工具
     */
    @Column(name="firmware_type")
    private Integer firmwareType;
    //固件描述
    @Column(name="firmware_desc")
    private String firmwareDesc;
    //基础url，对WebService有用
    @Column(name="base_url")
    private String baseURL;
    /*请求方法
     * 非工具使用：1、get 2、post 3、delete 、 4 put
     */
    @Column(name="request_method")
    private Integer requestMethod;
    /**
     * 第三方工具可能需要凭证，类似于token
     */
    @Column(name="certification")
    private String certification;
    /**
     * 负责人（用于报警）
     */
    @Column(name="master")
    private String master;

    /**
     * 负责人邮箱（用于报警）
     */
    @Column(name="email")
    private String email;

    /**词汇固件代码*/
    @Column(name="firmware_code")
    private String firmwareCode;
/*业务属性id(参数id),name(参数英文名)，cn_name（参数中文名）,obj_type(参数本身类型，是String还是int等等),
   in_out_type（输入输出参数类型，是输入还是输出）,required（参数是否必填）,父参数Id(parent_id)
   */
    private List<NewParam> inputParams;
    private List<NewParam> outputParams;
//    private Long   paramId;
//    private String paramENname;
//    private String paramCName;
//    private  Integer objType;
//    private Integer inOutType;
//    private Integer required;
//    private Long parentId;







}
