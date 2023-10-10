package com.blp.sentenceLib.service;

import com.blp.sentenceLib.entity.Post;

import java.util.List;

/**
 * (Post)表服务接口
 *
 * @author makejava
 * @since 2022-02-28 17:00:42
 */
public interface PostService {

    List<Post> getAllPost() throws Exception;
    Post getPostById(Long postId) throws Exception;


    void insertPost(Post post) throws Exception;

    int updatePost(Post post) throws Exception;

    int deletePost(Post post) throws Exception;




}
