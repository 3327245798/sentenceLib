package com.blp.sentenceLib.service;

import com.blp.sentenceLib.entity.MessageBoard;

import java.util.List;

/**
 * (MessageBoard)表服务接口
 *
 * @author makejava
 * @since 2022-02-28 17:02:45
 */
public interface MessageBoardService {

    List<MessageBoard> pageConditionMessageBoardList(MessageBoard messageBoardQuery) throws Exception;

    void insertMessageBoard(MessageBoard messageBoard) throws Exception;

    int deleteMessageBoard(MessageBoard messageBoard) throws Exception;

    List<MessageBoard> getMessageBoardByApplicationCaseId(Long applicationCaseId) throws Exception;
}
