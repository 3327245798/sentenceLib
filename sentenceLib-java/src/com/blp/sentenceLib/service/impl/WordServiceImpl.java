package com.blp.sentenceLib.service.impl;

import com.fy.basejar.tool.ActionToolBase;
import com.blp.sentenceLib.dao.WordDao;

import com.blp.sentenceLib.entity.Word;
import com.blp.sentenceLib.service.WordService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;
/**
        * (WordServiceImpl)表服务实现类
        *
        * @author makewanzhghe
        * @since 2022-07-27 10:25:24
        */
@Service
@NoArgsConstructor
public class WordServiceImpl implements WordService {

    @Autowired
    private WordDao wordDao;
//获取所有词汇，绑定固件与不帮绑定固件的都要获取到
    @Override
    public List<Word> getAllWords() throws Exception {
        final Connection connection=ActionToolBase.getDBConnection();
        return wordDao.getAllWords(connection);
    }

    @Override
    public List<Word> getAllWordsByStatus(Integer status) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        return wordDao.getAllWordsByStatus(connection,status);

    }

    @Override
    public List<Word> getPublicWordByUnitType(Integer unitType) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        return wordDao.getPublicWordByUnitType(connection,unitType);
    }

    @Override
    public List<Word> getAllWordsByCreatorName(String creatorName) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        return wordDao.getAllWordsByCreatorName(connection,creatorName);

    }

    @Override
    public List<Word> getAllWordsInMonth() throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        return wordDao.getAllWordsInMonth(connection);
    }

    @Override
    public List<Word> getAllWordsInWeek() throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        return wordDao.getAllWordsInWeek(connection);
    }

    @Override
    public List<Word> getAllWordsInDay() throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        return wordDao.getAllWordsInDay(connection);
    }
    /////////////////////////////////////////////////////////////////////////////////////
    /*这里是获取未绑定固件的原始词汇*/
    @Override
    public List<Word> getAllWordsNoUnit() throws Exception {
        final Connection connection=ActionToolBase.getDBConnection();
        return wordDao.getAllWordsNoUnit(connection);
    }

    @Override
    public List<Word> getAllWordsNoUnitByStatus(Integer status) throws Exception {
        final Connection connection=ActionToolBase.getDBConnection();
        return wordDao.getAllWordsNoUnitByStatus(connection,status);
    }

    @Override
    public List<Word> getAllWordsNoUnitByCreatorName(String creatorName) throws Exception {
        final Connection connection=ActionToolBase.getDBConnection();
        return wordDao.getAllWordsNoUnitByCreatorName(connection,creatorName);

    }

    @Override
    public List<Word> getAllWordsNoUnitInMonth() throws Exception {
        final Connection connection=ActionToolBase.getDBConnection();
        return wordDao.getAllWordsNoUnitInMonth(connection);

    }

    @Override
    public List<Word> getAllWordsNoUnitInWeek() throws Exception {
        final Connection connection=ActionToolBase.getDBConnection();
        return wordDao.getAllWordsNoUnitInWeek(connection);
    }

    @Override
    public List<Word> getAllWordsNoUnitInDay() throws Exception {
        final Connection connection=ActionToolBase.getDBConnection();
        return wordDao.getAllWordsNoUnitInWeekInDay(connection);
    }

    @Override
    public int updateWord(Word word) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        int i = (int) wordDao.updateIgnoreNull(connection, word);
        return i;

    }

    @Override
    public void insertWord(Word word) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        wordDao.save(connection,word);

    }

    @Override
    public int deleteWord(Word word) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        int i = (int) wordDao.delete(connection,word);
        return i;
    }
}
