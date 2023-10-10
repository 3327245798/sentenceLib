package com.blp.sentenceLib.dao;

import com.blp.sentenceLib.entity.Comment;
import com.blp.sentenceLib.entity.Post;
import com.fy.toolhelper.db.IBaseDao;

import java.sql.Connection;
import java.util.List;

/**
 * (Comment)表数据库访问层
 *
 * @author makewangzhe
 * @since 2022-07-27 10:25:24
 */
public interface CommentDao extends IBaseDao<Comment> {
    //获取帖子
    List<Comment> getCommentsByPostId(Connection connection,Long postId) throws Exception;


}