package com.blp.sentenceLib.dao.impl;

import com.blp.sentenceLib.dao.AnalyseDao;
import com.blp.sentenceLib.entity.Analyse;
import com.fy.toolhelper.db.BaseDaoImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AnalyseDaoImpl extends BaseDaoImpl<Analyse> implements AnalyseDao {
    public AnalyseDaoImpl() throws Exception {
    }

    @Override
    public List<Analyse> pageConditionAnalyseList(Connection connection, Analyse analyseQuery, int pageSize, int currentPage) throws SQLException{
        int start = pageSize * (currentPage - 1);
        StringBuilder sql = new StringBuilder("SELECT * FROM blp_analyse WHERE 1 = 1 ");

        if (!StringUtils.isEmpty(analyseQuery.getContent())) {
            sql.append(" and content like '%").append(analyseQuery.getContent()).append("%'");
        }
        if (!StringUtils.isEmpty(analyseQuery.getStatus())) {
            sql.append(" and status = ").append(analyseQuery.getStatus());
        }
        if (!StringUtils.isEmpty(analyseQuery.getDeveloperId())) {
            sql.append(" and developer_id = ").append(analyseQuery.getDeveloperId());
        }
        if (!StringUtils.isEmpty(analyseQuery.getDeveloperName())) {
            sql.append(" and developer_name = ").append(analyseQuery.getDeveloperName());
        }

        sql.append(" limit ? , ?");

        PreparedStatement pstm = connection.prepareStatement(sql.toString());
        if(pageSize == -1 && currentPage == -1){
            pstm.setInt(1, 0);
            int total = pageConditionAnalyseListTotal(connection, analyseQuery);
            pstm.setInt(2, total);
        }else{
            pstm.setInt(1, start);
            pstm.setInt(2, pageSize);
        }
        ResultSet rs = pstm.executeQuery();
        List<Analyse> analyseList = new ArrayList<>();
        while (rs.next()) {
            Analyse analyse = new Analyse();
            Analyse analyseEntity = getAnalyseEntity(rs);
            BeanUtils.copyProperties(analyseEntity, analyse);
            analyseList.add(analyse);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return analyseList;
    }


    @Override
    public int pageConditionAnalyseListTotal(Connection connection, Analyse analyseQuery) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM blp_analyse WHERE 1 = 1 ");

        if (!StringUtils.isEmpty(analyseQuery.getContent())) {
            sql.append(" and content like '%").append(analyseQuery.getContent()).append("%'");
        }
        if (!StringUtils.isEmpty(analyseQuery.getStatus())) {
            sql.append(" and status = ").append(analyseQuery.getStatus());
        }
        if (!StringUtils.isEmpty(analyseQuery.getDeveloperId())) {
            sql.append(" and developer_id = ").append(analyseQuery.getDeveloperId());
        }
        if (!StringUtils.isEmpty(analyseQuery.getDeveloperName())) {
            sql.append(" and developer_name = ").append(analyseQuery.getDeveloperName());
        }

        PreparedStatement pstm = connection.prepareStatement(sql.toString());
        ResultSet rs = pstm.executeQuery();
        int count = -1;
        while (rs.next()) {
            count = rs.getInt(1);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return count;
    }
    //根据草稿剧本id获取分析剧本,wz修改，将草稿剧本的id等于分析剧本的draft_chapter_id,
    //一对多的关系，一条草稿剧本语句对应多条分析剧本语句
    @Override
    public List<Analyse> getAnalyseByDraftChapterId(Connection connection, Long draftChapterId) throws SQLException {

        List<Analyse> analyList=new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM blp_analyse where draft_chapter_id=?");
        PreparedStatement pstm = connection.prepareStatement(sql.toString());
        pstm.setLong(1, draftChapterId);
        ResultSet rs = pstm.executeQuery();

        while (rs.next()) {

            Analyse analyse=new Analyse();
            Analyse analyseEntity = getAnalyseEntity(rs);
            BeanUtils.copyProperties(analyseEntity, analyse);
            analyList.add(analyse);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return analyList;
    }

    @Override
    public Analyse getAnalyseById(Connection connection, Long analyseId) throws SQLException {
        Analyse analyse = null;

        StringBuilder sql = new StringBuilder("SELECT * FROM blp_analyse WHERE id = ?");
        PreparedStatement pstm = connection.prepareStatement(sql.toString());
        pstm.setLong(1, analyseId);
        ResultSet rs = pstm.executeQuery();

        while (rs.next()) {
            analyse = getAnalyseEntity(rs);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return analyse;
    }

    private Analyse getAnalyseEntity(ResultSet rs) throws SQLException {
        Analyse analyse = new Analyse();
        analyse.setId(rs.getLong("id"));
        analyse.setContent(rs.getString("content"));
        analyse.setSerialization(rs.getString("serialization"));
        analyse.setStatus(rs.getString("status"));
        analyse.setDeveloperId(rs.getLong("developer_id"));
        analyse.setDeveloperName(rs.getString("developer_name"));
        analyse.setCreateTime(rs.getTimestamp("create_time"));
        analyse.setUpdateTime(rs.getTimestamp("update_time"));
        analyse.setDraftChapterId(rs.getLong("draft_chapter_id"));
        analyse.setSentenceId(rs.getLong("sentence_id"));

        return analyse;
    }

}