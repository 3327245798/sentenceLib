package com.blp.sentenceLib.dao.impl;

import com.blp.sentenceLib.dao.WordDao;
import com.blp.sentenceLib.dao.WordVersionDao;
import com.blp.sentenceLib.entity.Word;
import com.blp.sentenceLib.entity.WordVersion;
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
 * (WordDaoVersionImpl)表数据库访问层的实现
 *
 * @author makewangzhe
 * @since 2022-08-04 10:34:27
 */
@Repository
public class WordDaoVersionImpl extends BaseDaoImpl<WordVersion> implements WordVersionDao {

    public WordDaoVersionImpl() throws Exception {
    }

    @Override
    public List<WordVersion> getAllWordVersionByWordId(Connection connection, Long wordId) throws Exception {
        String sql = "SELECT * FROM blp_word_version WHERE word_id=? ORDER BY version DESC";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setLong(1, wordId);

        ResultSet rs = pstm.executeQuery();
        List<WordVersion> wordVersionList = new ArrayList();
        while (rs.next()) {
            WordVersion wordVersion = new WordVersion();
            WordVersion wordVersionEntity = getWordVersionEntity(rs);
            BeanUtils.copyProperties(wordVersionEntity, wordVersion);
            wordVersionList.add(wordVersion);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return wordVersionList;

    }


    private WordVersion getWordVersionEntity(ResultSet rs) throws SQLException {
        WordVersion wordVersion = new WordVersion();
        wordVersion.setId(rs.getLong("id"));
        wordVersion.setWord_id(rs.getLong("word_id"));
        wordVersion.setCreateTime(rs.getTimestamp("create_time"));
        wordVersion.setDescription(rs.getString("description"));
        wordVersion.setCreatorId(rs.getLong("creator_id"));
        wordVersion.setCreatorName(rs.getString("creator_name"));
        wordVersion.setVersion(rs.getLong("version"));
        wordVersion.setName(rs.getString("name"));
        return wordVersion;

    }

}
