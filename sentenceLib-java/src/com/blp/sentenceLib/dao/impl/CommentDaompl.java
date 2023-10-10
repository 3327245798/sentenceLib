package com.blp.sentenceLib.dao.impl;

import com.blp.sentenceLib.dao.CommentDao;
import com.blp.sentenceLib.dao.PostDao;
import com.blp.sentenceLib.entity.Comment;
import com.blp.sentenceLib.entity.Post;
import com.fy.toolhelper.db.BaseDaoImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * (PostDaoImpl)表数据库访问层的实现
 *
 * @author makewangzhe
 * @since 2022-07-27 20:34:27
 */
@Repository
public class CommentDaompl extends BaseDaoImpl<Comment> implements CommentDao {

    public CommentDaompl() throws Exception {}


    private Comment getCommentEntity(ResultSet rs) throws SQLException {
        Comment comment=new Comment();
        comment.setId(rs.getLong("id"));
        comment.setPostId(rs.getLong("post_id"));
        comment.setContent(rs.getString("content"));//评论内容
        comment.setCommentorName(rs.getString("commentor_name"));
        comment.setCommentTime(rs.getTimestamp("comment_time"));
        comment.setAvatar( rs.getBytes("avatar"));

        return comment;


    }
//有错解决没写pstm
    @Override
    public List<Comment> getCommentsByPostId(Connection connection, Long postId) throws Exception {
        String sql = "SELECT * FROM blp_comment where post_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = null;
        Comment commentEntity = null;
        pstm.setLong(1, postId);
        rs = pstm.executeQuery();
        List<Comment> commentList = new ArrayList<>();
        while (rs.next()) {
            Comment comment = new Comment();
            commentEntity = getCommentEntity(rs);
            BeanUtils.copyProperties(commentEntity, comment);
            commentList.add(comment);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return commentList;
    }
}

