package com.blp.sentenceLib.remote.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * 消息
 * @since 4.5.0
 * @author linjie
 */
public class Message implements Serializable {
    private static final long serialVersionUID = -3188545072903825788L;

    /**
     * 三选一：1.目标消息板，可以视图也可以对象ID
     */
    private Long chatroomID;

    /**
     * 三选一：2.批量目标消息板，可以视图也可以对象ID
     */
    private JSONArray chatroomIDs;

    /**
     * 三选一：3.批量模板私聊用户，可以用户ID也可以用户账号
     */
    private JSONArray receiverKeys;

    /**
     * 消息的本地ID，保证不会重复发
     */
    private String localID;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息附加动作
     */
    private JSONArray actions;

    /**
     * 消息附件
     */
    private JSONArray attachments;

    /**
     * 消息所属的组
     */
    private JSONObject group;

    public Long getChatroomID() {
        return chatroomID;
    }

    public void setChatroomID(Long chatroomID) {
        this.chatroomID = chatroomID;
    }

    public JSONArray getChatroomIDs() {
        return chatroomIDs;
    }

    public void setChatroomIDs(JSONArray chatroomIDs) {
        this.chatroomIDs = chatroomIDs;
    }

    public JSONArray getReceiverKeys() {
        return receiverKeys;
    }

    public void setReceiverKeys(JSONArray receiverKeys) {
        this.receiverKeys = receiverKeys;
    }

    public String getLocalID() {
        return localID;
    }

    public void setLocalID(String localID) {
        this.localID = localID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public JSONArray getActions() {
        return actions;
    }

    public void setActions(JSONArray actions) {
        this.actions = actions;
    }

    public JSONArray getAttachments() {
        return attachments;
    }

    public void setAttachments(JSONArray attachments) {
        this.attachments = attachments;
    }

    public JSONObject getGroup() {
        return group;
    }

    public void setGroup(JSONObject group) {
        this.group = group;
    }
}