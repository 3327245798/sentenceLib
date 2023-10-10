package com.blp.sentenceLib.dao.impl;

import com.blp.sentenceLib.dao.PostDao;

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
public class PostIDaompl extends BaseDaoImpl<Post> implements PostDao {

    public PostIDaompl() throws Exception {}
   ///想将诶就前端传值为空的问题，就是post帖子实体，即时不穿封面，也能插入数据库
   /* public void save(Connection connection) throws Exception {

    }*/

    @Override
    public List<Post> getAllPosts(Connection connection) throws Exception {

        String sql="SELECT * FROM blp_post  ";
        PreparedStatement pstm = connection.prepareStatement(sql);


        ResultSet rs = pstm.executeQuery();
        List<Post> postList = new ArrayList();
        while (rs.next()) {
            Post post = new Post();
            Post postEntity = getPostEntity(rs);
            BeanUtils.copyProperties(postEntity, post);
            post.setImg(null);
            postList.add(post);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return postList;

    }

    @Override
    public Post getPostById(Connection connection, Long postId) {
        String sql="SELECT * FROM blp_post where id=?";
        PreparedStatement pstm=null;
        ResultSet rs=null;
        Post postEntity=null;
        try {
            pstm= connection.prepareStatement(sql);
            pstm.setLong(1,postId);
            rs = pstm.executeQuery();
            if (rs.next()) {
                postEntity = getPostEntity(rs);
            }
            return postEntity;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {

                if (pstm != null) {
                    pstm.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Post getPostEntity(ResultSet rs) throws SQLException {
        Post post=new Post();
        post.setId(rs.getLong("id"));
        post.setTitle(rs.getString("title"));
        post.setContent(rs.getString("content"));
        post.setCreatorName(rs.getString("creator_name"));
        post.setCreateTime(rs.getTimestamp("create_time"));
        post.setLikeCount(rs.getInt("like_count"));
        post.setDislikeCount(rs.getInt("dislike_count"));
        post.setCommentCount(rs.getInt("comment_count"));
        post.setCollectionCount(rs.getInt("collection_count"));
        post.setImg( rs.getBytes("img"));

        return post;


    }

    }

