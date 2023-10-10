package com.blp.sentenceLib.dao;

import com.blp.sentenceLib.entity.Post;
import com.fy.toolhelper.db.IBaseDao;

import java.sql.Connection;
import java.util.List;

/**
 * (Word)表数据库访问层
 *
 * @author makewangzhe
 * @since 2022-07-27 10:25:24
 */
public interface PostDao extends IBaseDao<Post> {
    //获取帖子
    List<Post> getAllPosts(Connection connection) throws Exception;
    Post getPostById(Connection connection,Long postId) throws Exception;
   //重写save方法
    void save(Connection connection, Post post) throws Exception;


}