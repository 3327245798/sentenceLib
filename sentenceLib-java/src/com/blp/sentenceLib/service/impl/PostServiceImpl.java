package com.blp.sentenceLib.service.impl;

import com.blp.sentenceLib.dao.PostDao;
import com.blp.sentenceLib.entity.Post;
import com.blp.sentenceLib.service.PostService;
import com.fy.basejar.tool.ActionToolBase;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

/**
 * (Draft)表服务实现类
 *
 * @author makejava
 * @since 2022-02-28 17:00:42
 */
@Service
@NoArgsConstructor
public class PostServiceImpl implements PostService {

    @Autowired
    PostDao postDao;

    @Override
    public List<Post> getAllPost() throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        return postDao.getAllPosts(connection);

    }

    @Override
    public Post getPostById(Long postId) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        return postDao.getPostById(connection,postId);
    }

    @Override
    public int updatePost(Post post) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        int i = (int) postDao.updateIgnoreNull(connection, post);
        return i;
    }
    @Override
    public void insertPost(Post post) throws Exception{
        final Connection connection = ActionToolBase.getDBConnection();
        postDao.save(connection, post);
    }
    @Override
    public int deletePost(Post post) throws Exception{
        final Connection connection = ActionToolBase.getDBConnection();
        int i = (int) postDao.delete(connection, post);
        return i;
    }


}
