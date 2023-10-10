package com.blp.sentenceLib.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * (帮语论坛的用户)实体类
 *
 * @author wz
 * @since 2022-10-15 17:00:42
 */
@Data
@Table(name = "blp_talk_userinfo")
public class UserTalkInfo implements Serializable {
    private static final long serialVersionUID = 384904563361861222L;
    /**
     *
     */
    @Id
    @Column(name = "id")
    private Long id;

    /*用户账号*/
    @Column(name = "user_account")
    private  Long userAccount;
    /**
     * 用户姓名
     */
    @Column(name = "user_name")
    private String userName;

   /*用户头像*/
    @Column(name = "user_avatar")
    private byte[] userAvatar;


}

