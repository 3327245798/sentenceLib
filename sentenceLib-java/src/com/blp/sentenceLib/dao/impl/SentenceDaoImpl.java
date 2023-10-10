package com.blp.sentenceLib.dao.impl;

import com.blp.sentenceLib.dao.SentenceDao;
import com.blp.sentenceLib.entity.Sentence;
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
 * (Unit)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-06 20:34:27
 */
@Repository
public class SentenceDaoImpl extends BaseDaoImpl<Sentence> implements SentenceDao {

    public SentenceDaoImpl() throws Exception {}


    @Override
    public List<Sentence> getAllSentences(Connection connection) throws Exception {


        String sql = "SELECT * FROM blp_sentence";

        PreparedStatement pstm = connection.prepareStatement(sql);


        ResultSet rs = pstm.executeQuery();
        List<Sentence> sentenceList = new ArrayList();
        while (rs.next()) {
            Sentence sentence = new Sentence();
            Sentence sentenceEntity = getSentenceEntity(rs);
            BeanUtils.copyProperties(sentenceEntity, sentence);
            sentenceList.add(sentence);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return sentenceList;
    }
    //根据类型(标准句型和非标准句型)获取所有句型
    @Override
    public List<Sentence> getAllSentencesByType(Connection connection, Integer type) throws Exception {

        String sql = "SELECT * FROM blp_sentence WHERE type=?";


        PreparedStatement pstm = connection.prepareStatement(sql);
       pstm.setInt(1, type);

        ResultSet RS = pstm.executeQuery();
        List<Sentence> sentenceList = new ArrayList();
        while (RS.next()) {
            Sentence sentence = new Sentence();
            Sentence sentenceEntity = getSentenceEntity(RS);
            BeanUtils.copyProperties(sentenceEntity, sentence);
            sentenceList.add(sentence);
        }
        if (RS != null) {
            RS.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return sentenceList;
    }
    /*句型界面搜索功能，不管是句型这一列，还是句型实例这一列，还是编辑者这一列包含用户输入的关键字，都要检索到 */
    @Override
    public List<Sentence> getAllSentencesByuserInputKeyword(Connection connection, String userInputKeyword) throws Exception {
        String[] keywords = userInputKeyword.split(" |,|、");
        String sql = "SELECT * FROM blp_sentence WHERE status=3";
        for (int i = 0; i < keywords.length; i++) {
            if(i == 0) {
                sql += " AND ";
            }
            sql += "(creator_name LIKE ? OR example LIKE ? OR description LIKE ?)";
            if(i<keywords.length-1) {
                sql += " OR ";
            }
        }
        PreparedStatement pstm = connection.prepareStatement(sql);
        int start = 0;
        for (int i = 0; i < keywords.length; i++) {
            pstm.setString(start + 1,"%"+keywords[i]+"%");
            pstm.setString(start + 2,"%"+keywords[i]+"%");
            pstm.setString(start + 3,"%"+keywords[i]+"%");
            start += 3;
        }
        ResultSet RS = pstm.executeQuery();
        List<Sentence> sentenceList = new ArrayList();
        while (RS.next()) {
            Sentence sentence = new Sentence();
            Sentence sentenceEntity = getSentenceEntity(RS);
            BeanUtils.copyProperties(sentenceEntity, sentence);
            sentenceList.add(sentence);
        }
        if (RS != null) {
            RS.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return sentenceList;
    }


    @Override
    public List<Sentence> getAllSentencesByCreatorName(Connection connection, String creatorName) throws Exception {
        String sql = "SELECT * FROM blp_sentence WHERE creator_name LIKE ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,"%"+creatorName+"%");
        ResultSet RS = pstm.executeQuery();
        List<Sentence> sentenceList = new ArrayList();
        while (RS.next()) {
            Sentence sentence = new Sentence();
            Sentence sentenceEntity = getSentenceEntity(RS);
            BeanUtils.copyProperties(sentenceEntity, sentence);
            sentenceList.add(sentence);
        }
        if (RS != null) {
            RS.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return sentenceList;
    }
    @Override
    public List<Sentence> getAllSentenceByStatus(Connection connection, Integer status) throws Exception {
        String sql = "SELECT * FROM blp_sentence WHERE status=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, status);


        ResultSet rs = pstm.executeQuery();
        List<Sentence> sentenceList = new ArrayList();
        while (rs.next()) {
            Sentence sentence = new Sentence();
            Sentence sentenceEntity = getSentenceEntity(rs);
            BeanUtils.copyProperties(sentenceEntity, sentence);
            sentenceList.add(sentence);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return sentenceList;
    }

    @Override
    public List<Sentence> getAllSentenceInChecking(Connection connection) throws Exception {
        String sql = "SELECT * FROM blp_sentence WHERE status=0";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        List<Sentence> sentenceList = new ArrayList();
        while (rs.next()) {
            Sentence sentence = new Sentence();
            Sentence sentenceEntity = getSentenceEntity(rs);
            BeanUtils.copyProperties(sentenceEntity, sentence);
            sentenceList.add(sentence);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return sentenceList;
    }

    //date(bw.create_time) = curdate()获取当天创建的句型
    @Override
    public List<Sentence> getAllSentenceInDay(Connection connection) throws Exception {
        String sql = "SELECT * FROM blp_sentence bs WHERE date(bs.create_time) = curdate()";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        List<Sentence> sentenceList = new ArrayList();
        while (rs.next()) {
            Sentence sentence = new Sentence();
            Sentence sentenceEntity = getSentenceEntity(rs);
            BeanUtils.copyProperties(sentenceEntity, sentence);
            sentenceList.add(sentence);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return sentenceList;
    }
    //DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(bw.create_time)";获取一周之内创建的句型
    @Override
    public List<Sentence> getAllSentenceInWeek(Connection connection) throws Exception {
        String sql = "SELECT * FROM blp_sentence bs WHERE DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(bs.create_time)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        List<Sentence> sentenceList = new ArrayList();
        while (rs.next()) {
            Sentence sentence = new Sentence();
            Sentence sentenceEntity = getSentenceEntity(rs);
            BeanUtils.copyProperties(sentenceEntity, sentence);
            sentenceList.add(sentence);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return sentenceList;
    }
    //DATE_SUB(CURDATE(), INTERVAL 1 MONTH) <= date(bw.create_time)";//获取一个月内创建的句型
    @Override
    public List<Sentence> getAllSentenceInMonth(Connection connection) throws Exception {
        String sql = "SELECT * FROM blp_sentence bs WHERE DATE_SUB(CURDATE(), INTERVAL 1 MONTH) <= date(bs.create_time)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        List<Sentence> sentenceList = new ArrayList();
        while (rs.next()) {
            Sentence sentence = new Sentence();
            Sentence sentenceEntity = getSentenceEntity(rs);
            BeanUtils.copyProperties(sentenceEntity, sentence);
            sentenceList.add(sentence);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return sentenceList;
    }
//根据sentenceId获取sentence对象本身，主要是为了获得sentence对象的固件代码属性（unit_text）
    @Override
    public Sentence getSentenceById(Connection connection, Long snetenceId) throws Exception {
        String sql = "SELECT * FROM blp_sentence WHERE id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setLong(1, snetenceId);

        ResultSet rs = pstm.executeQuery();
        Sentence sentence =null;
        while (rs.next()) {
            sentence = getSentenceEntity(rs);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return sentence;

    }

    private Sentence getSentenceEntity(ResultSet rs) throws SQLException {
        Sentence sentence = new Sentence();
        sentence.setId(rs.getLong("id"));

        sentence.setType(rs.getString("type"));
        sentence.setStatus(rs.getInt("status"));
        sentence.setName(rs.getString("name"));
        sentence.setSerialization(rs.getString("serialization"));
        sentence.setExample(rs.getString("example"));
        sentence.setDescription(rs.getString("description"));
        sentence.setCreatorName(rs.getString("creator_name"));
        sentence.setUpdateTime(rs.getTimestamp("update_time"));
        sentence.setCreatorId(rs.getLong("creator_id"));
        sentence.setCreateTime(rs.getTimestamp("create_time"));
        sentence.setSequence(rs.getString("sequence"));
        sentence.setToolIdAndAction(rs.getString("toolId_action"));
        sentence.setUnitText(rs.getString("unit_text"));
        sentence.setSentenceDescription(rs.getString("sentence_description"));
        return sentence;
    }
}