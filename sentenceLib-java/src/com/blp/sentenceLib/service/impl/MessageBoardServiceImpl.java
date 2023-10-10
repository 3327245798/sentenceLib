package com.blp.sentenceLib.service.impl;

import com.fy.basejar.tool.ActionToolBase;
import com.blp.sentenceLib.dao.MessageBoardDao;
import com.blp.sentenceLib.entity.MessageBoard;
import com.blp.sentenceLib.service.MessageBoardService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (MessageBoard)表服务实现类
 *
 * @author makejava
 * @since 2022-02-28 17:02:45
 */
@Service
@NoArgsConstructor
public class MessageBoardServiceImpl implements MessageBoardService {

    @Autowired
    MessageBoardDao messageBoardDao;

    @Override
    public List<MessageBoard> pageConditionMessageBoardList(MessageBoard messageBoardQuery) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        // 全部messageBoardList
        List<MessageBoard> messageBoardList = messageBoardDao.pageConditionMessageBoardList(connection, messageBoardQuery);

        List<MessageBoard> level1List = messageBoardList.stream().filter(item -> {
            if (item.getIsDeleted().equals(1)) {
                item.setContent("该评论已被原作者删除");
            }
            return item.getParentId().equals(0L);
        }).collect(Collectors.toList());
        level1List.stream().forEach(l1->{
            l1.setChildren(getChildrenByParentId(l1.getId(),messageBoardList));
        });
        level1List=level1List.stream().sorted((item1,item2)-> (int) (item2.getTime().getTime()-item1.getTime().getTime())).collect(Collectors.toList());
        return level1List;
    }

    private List<MessageBoard> getChildrenByParentId(Long id, List<MessageBoard> messageBoardList) {

        List<MessageBoard> collect = messageBoardList.stream().filter(item -> {
            return item.getParentId().equals(id);
        }).map(l2->{
            // 递归获取孩子列表
            l2.setChildren(getChildrenByParentId(l2.getId(),messageBoardList));
            return l2;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public void insertMessageBoard(MessageBoard messageBoard) throws Exception{
        final Connection connection = ActionToolBase.getDBConnection();
        messageBoardDao.save(connection, messageBoard);
    }

    @Override
    public int deleteMessageBoard(MessageBoard messageBoard) throws Exception{
        final Connection connection = ActionToolBase.getDBConnection();
        // 逻辑删除
        int i = (int) messageBoardDao.updateIgnoreNull(connection, messageBoard);
//        int i = (int) messageBoardDao.delete(connection, messageBoard);
        return i;
    }

    @Override
    public List<MessageBoard> getMessageBoardByApplicationCaseId(Long applicationCaseId) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        // 全部messageBoardList
        List<MessageBoard> messageBoardList = messageBoardDao.getMessageBoardByApplicationCaseId(connection, applicationCaseId);

        List<MessageBoard> level1List = messageBoardList.stream().filter(item -> {
            if (item.getIsDeleted().equals(1)) {
                item.setContent("该评论已被原作者删除");
            }
            return item.getParentId().equals(0L);
        }).collect(Collectors.toList());
        level1List.stream().forEach(l1->{
            l1.setChildren(getChildrenByParentId(l1.getId(),messageBoardList));
        });
        level1List=level1List.stream().sorted((item1,item2)-> (int) (item2.getTime().getTime()-item1.getTime().getTime())).collect(Collectors.toList());
        return level1List;
    }
}
