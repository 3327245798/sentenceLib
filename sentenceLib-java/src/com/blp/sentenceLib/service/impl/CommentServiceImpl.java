package com.blp.sentenceLib.service.impl;

import com.blp.sentenceLib.dao.CommentDao;
import com.blp.sentenceLib.dao.PostDao;
import com.blp.sentenceLib.entity.Comment;
import com.blp.sentenceLib.entity.Post;
import com.blp.sentenceLib.service.CommentService;
import com.blp.sentenceLib.service.PostService;
import com.fy.basejar.tool.ActionToolBase;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

/**
 * (CommentServiceImpl)表服务实现类
 *
 * @author makewz
 * @since 2022-10-17 17:00:42
 */
@Service
@NoArgsConstructor
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentDao commentDao;

    @Override
    public List<Comment> getCommentByPostId(Long postId) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        return commentDao.getCommentsByPostId(connection,postId);

    }

    @Override
    public void insertComment(Comment comment) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        commentDao.save(connection, comment);
    }

    @Override
    public int updateComment(Comment comment) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        int i=(int)commentDao.updateIgnoreNull(connection,comment);
        return i;
    }

    @Override
    public int deleteComment(Comment comment) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        int i=(int)commentDao.delete(connection,comment);
        return i;
    }
}
