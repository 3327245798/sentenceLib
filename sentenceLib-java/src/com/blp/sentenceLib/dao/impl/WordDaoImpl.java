package com.blp.sentenceLib.dao.impl;

import com.blp.sentenceLib.dao.WordDao;
import com.blp.sentenceLib.entity.Word;
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
 * (WordDaoImpl)表数据库访问层的实现
 *
 * @author makewangzhe
 * @since 2022-07-27 20:34:27
 */
@Repository
public class WordDaoImpl  extends BaseDaoImpl<Word> implements WordDao {

    public WordDaoImpl() throws Exception {}
   //词汇库页面使用这些接口（展示的词汇都是已经绑定好固件的）
   @Override
    public List<Word> getAllWords(Connection connection) throws Exception {


        String sql = "select f.firmware_name,f.firmware_code,f.firmware_type,w.*  from blp_firmware f,bls_word w WHERE f.id=w.firmware_id";

        PreparedStatement pstm = connection.prepareStatement(sql);


        ResultSet rs = pstm.executeQuery();
        List<Word> wordList = new ArrayList();
        while (rs.next()) {
            Word word = new Word();
            Word wordEntity = getWordEntity(rs);
            BeanUtils.copyProperties(wordEntity, word);
            wordList.add(word);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return wordList;

    }

    @Override
    public List<Word> getAllWordsByStatus(Connection connection, Integer status) throws Exception {
        //String sql = "SELECT bw.*, bu.tool_name, bu.`name` unit_name FROM bls_word bw,blp_unit bu where bw.firmware_id=bu.id and status=?";
        String sql = "select f.firmware_name,f.firmware_code,f.firmware_type,w.*  from blp_firmware f,bls_word w WHERE f.id=w.firmware_id and status=?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, status);

        ResultSet rs = pstm.executeQuery();
        List<Word> wordList = new ArrayList();
        while (rs.next()) {
            Word word = new Word();
            Word wordEntity = getWordEntity(rs);
            BeanUtils.copyProperties(wordEntity, word);
            wordList.add(word);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return wordList;

    }

    @Override
    public List<Word> getPublicWordByUnitType(Connection connection, Integer unitType) throws Exception {
        String sql = "select f.firmware_name,f.firmware_code,f.firmware_type,w.*  from blp_firmware f,bls_word w WHERE f.id=w.firmware_id and firmware_type=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, unitType);

        ResultSet rs = pstm.executeQuery();
        List<Word> wordList = new ArrayList();
        while (rs.next()) {
            Word word = new Word();
            Word wordEntity = getWordEntity(rs);
            BeanUtils.copyProperties(wordEntity, word);
            wordList.add(word);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return wordList;

    }
     //人名必须写全才能检索到
    @Override
    public List<Word> getAllWordsByCreatorName(Connection connection, String creatorName) throws Exception {

        String sql = "SELECT blp_firmware.firmware_name,f.firmware_code,,blp_firmware.firmware_type,tmp.* FROM (SELECT * FROM bls_word WHERE creator_name LIKE ?) AS " +
                "tmp,blp_firmware WHERE tmp.firmware_id=blp_firmware.id";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,"%"+creatorName+"%");

        ResultSet rs = pstm.executeQuery();
        List<Word> wordList = new ArrayList();
        while (rs.next()) {
            Word word = new Word();
            Word wordEntity = getWordEntity(rs);
            BeanUtils.copyProperties(wordEntity, word);
            wordList.add(word);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return wordList;

    }
    //一月之内创建的词汇
    @Override
    public List<Word> getAllWordsInMonth(Connection connection) throws Exception {
        //String sql="SELECT bw.*, bu.tool_name, bu.`name` unit_name FROM bls_word bw,blp_unit bu where bw.firmware_id=bu.id and DATE_SUB(CURDATE(), INTERVAL 1 MONTH) <= date(bw.create_time)";
        String sql="select f.firmware_name,f.firmware_code,f.firmware_type,w.*  from blp_firmware f,bls_word w WHERE f.id=w.firmware_id and DATE_SUB(CURDATE(), INTERVAL 1 MONTH) <= date(w.create_time)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        List<Word> wordList = new ArrayList();
        while (rs.next()) {
            Word word = new Word();
            Word wordEntity = getWordEntity(rs);
            BeanUtils.copyProperties(wordEntity, word);
            wordList.add(word);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return wordList;
    }
//一周之内创建的词汇
    @Override
    public List<Word> getAllWordsInWeek(Connection connection) throws Exception {
        //String sql="SELECT bw.*, bu.tool_name, bu.`name` unit_name FROM bls_word bw,blp_unit bu where bw.firmware_id=bu.id and DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(bw.create_time)";
        String sql=" select f.firmware_name,f.firmware_code,f.firmware_type,w.*  from blp_firmware f,bls_word w WHERE f.id=w.firmware_id and DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(w.create_time)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        List<Word> wordList = new ArrayList();
        while (rs.next()) {
            Word word = new Word();
            Word wordEntity = getWordEntity(rs);
            BeanUtils.copyProperties(wordEntity, word);
            wordList.add(word);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return wordList;

    }
    //一天之内创建的词汇，date(create_time) = curdate();
    @Override
    public List<Word> getAllWordsInDay(Connection connection) throws Exception {
        //String sql="SELECT bw.*, bu.tool_name, bu.`name` unit_name FROM bls_word bw,blp_unit bu where bw.firmware_id=bu.id and date(bw.create_time) = curdate()";
        String sql=" select f.firmware_name,f.firmware_code,f.firmware_type,w.*  from blp_firmware f,bls_word w WHERE f.id=w.firmware_id and date(w.create_time) = curdate()";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        List<Word> wordList = new ArrayList();
        while (rs.next()) {
            Word word = new Word();
            Word wordEntity = getWordEntity(rs);
            BeanUtils.copyProperties(wordEntity, word);
            wordList.add(word);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return wordList;
    }

    //////////////////这里是还未绑定固件的原始词汇////////////////////////////////
    //创建词汇页面使用这些接口
    //获取所有词汇，未绑定固件的也能获取到，即数据里面所有的词汇
    @Override
    public List<Word> getAllWordsNoUnit(Connection connection) throws Exception {

        String sql = "SELECT * FROM bls_word ";

        PreparedStatement pstm = connection.prepareStatement(sql);


        ResultSet rs = pstm.executeQuery();
        List<Word> wordList = new ArrayList();
        while (rs.next()) {
            Word word = new Word();
            Word wordEntity = getWordEntityNoUnit(rs);
            BeanUtils.copyProperties(wordEntity, word);
            wordList.add(word);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return wordList;

    }

    @Override
    public List<Word> getAllWordsNoUnitByCreatorName(Connection connection,String creatorName) throws Exception {

        String sql = "SELECT * FROM bls_word WHERE creator_name LIKE ?"  ;
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,"%"+creatorName+"%");

        ResultSet rs = pstm.executeQuery();
        List<Word> wordList = new ArrayList();
        while (rs.next()) {
            Word word = new Word();
            Word wordEntity = getWordEntityNoUnit(rs);
            BeanUtils.copyProperties(wordEntity, word);
            wordList.add(word);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return wordList;
    }

    @Override
    public List<Word> getAllWordsNoUnitByStatus(Connection connection, Integer status) throws Exception {
        String sql = "SELECT * FROM bls_word WHERE status=?"  ;
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, status);

        ResultSet rs = pstm.executeQuery();
        List<Word> wordList = new ArrayList();
        while (rs.next()) {
            Word word = new Word();
            Word wordEntity = getWordEntityNoUnit(rs);
            BeanUtils.copyProperties(wordEntity, word);
            wordList.add(word);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return wordList;

    }

    @Override
    public List<Word> getAllWordsNoUnitInMonth(Connection connection) throws Exception {

        String sql = "SELECT * FROM bls_word bw WHERE DATE_SUB(CURDATE(), INTERVAL 1 MONTH) <= date(bw.create_time)";
        PreparedStatement pstm = connection.prepareStatement(sql);


        ResultSet rs = pstm.executeQuery();
        List<Word> wordList = new ArrayList();
        while (rs.next()) {
            Word word = new Word();
            Word wordEntity = getWordEntityNoUnit(rs);
            BeanUtils.copyProperties(wordEntity, word);
            wordList.add(word);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return wordList;


    }

    @Override
    public List<Word> getAllWordsNoUnitInWeek(Connection connection) throws Exception {

        String sql = "SELECT * FROM bls_word bw WHERE DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(bw.create_time)";
        PreparedStatement pstm = connection.prepareStatement(sql);


        ResultSet rs = pstm.executeQuery();
        List<Word> wordList = new ArrayList();
        while (rs.next()) {
            Word word = new Word();
            Word wordEntity = getWordEntityNoUnit(rs);
            BeanUtils.copyProperties(wordEntity, word);
            wordList.add(word);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return wordList;

    }

    @Override
    public List<Word> getAllWordsNoUnitInWeekInDay(Connection connection) throws Exception {
        String sql = "SELECT * FROM bls_word bw WHERE date(bw.create_time) = curdate()";
        PreparedStatement pstm = connection.prepareStatement(sql);


        ResultSet rs = pstm.executeQuery();
        List<Word> wordList = new ArrayList();
        while (rs.next()) {
            Word word = new Word();
            Word wordEntity = getWordEntityNoUnit(rs);
            BeanUtils.copyProperties(wordEntity, word);
            wordList.add(word);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return wordList;
    }


    private Word getWordEntity(ResultSet rs) throws SQLException {
        Word word=new Word();
        word.setId(rs.getLong("id"));
        word.setName(rs.getString("name"));
        word.setCreatorId(rs.getLong("creator_id"));
        word.setCreatorName(rs.getString("creator_name"));
        word.setStatus(rs.getInt("status"));
        //word.setBandObjId(rs.getLong("band_obj_id"));数据库这个字段已经删除了
        word.setDescription(rs.getString("description"));
        word.setCreateTime(rs.getTimestamp("create_time"));
        word.setUpdateTime(rs.getTimestamp("update_time"));
        word.setMeaning(rs.getString("meaning"));
        word.setType(rs.getInt("type"));
        //业务属性
       /* word.setToolName(rs.getString("tool_name"));
        word.setUnitName(rs.getString("unit_name"));

        word.setToolId(rs.getLong("tool_id"));
        word.setUnitId(rs.getLong("unit_id"));
        word.setUnitType(rs.getInt("unit_type"));*/
        //业务属性
        word.setFirmwareName(rs.getString("firmware_name"));
        word.setFirmwareType(rs.getInt("firmware_type"));
        word.setFirmwareCode(rs.getString("firmware_code"));

        return word;


    }
//获取未绑定固件的原始词汇（绑定和为 绑定的都可以获取到）
    private Word getWordEntityNoUnit(ResultSet rs) throws SQLException {
        Word word=new Word();
        word.setId(rs.getLong("id"));
        word.setName(rs.getString("name"));
        word.setCreatorId(rs.getLong("creator_id"));
        word.setCreatorName(rs.getString("creator_name"));
        word.setStatus(rs.getInt("status"));
        //word.setBandObjId(rs.getLong("band_obj_id"));数据库这个字段已经删除了
        word.setDescription(rs.getString("description"));
        word.setCreateTime(rs.getTimestamp("create_time"));
        word.setUpdateTime(rs.getTimestamp("update_time"));
        word.setMeaning(rs.getString("meaning"));
        word.setType(rs.getInt("type"));

        return word;


    }

}
