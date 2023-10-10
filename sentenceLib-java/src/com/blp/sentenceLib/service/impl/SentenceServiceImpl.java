package com.blp.sentenceLib.service.impl;

import com.fy.basejar.tool.ActionToolBase;
import com.blp.sentenceLib.dao.SentenceDao;
import com.blp.sentenceLib.entity.Sentence;
import com.blp.sentenceLib.service.SentenceService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

/**
 * (Sentence)表服务实现类
 *
 * @author makejava
 * @since 2022-05-07 10:25:24
 */
@Service
@NoArgsConstructor
public class SentenceServiceImpl implements SentenceService {

    @Autowired
    private SentenceDao sentenceDao;



    @Override
    public List<Sentence> getAllSentences() throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        return sentenceDao.getAllSentences(connection);
    }

    @Override
    public List<Sentence> getAllSentencesByType(Integer type) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        return sentenceDao.getAllSentencesByType(connection,type);
    }

    @Override
    public List<Sentence> getAllSentencesByuserInputKeyword(String userInputKeyword) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        return sentenceDao.getAllSentencesByuserInputKeyword(connection,userInputKeyword);
    }

    @Override
    public List<Sentence> getAllSentencesByCreatorName(String creatorName) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        return sentenceDao.getAllSentencesByCreatorName(connection,creatorName);
    }

    @Override
    public List<Sentence> getAllSentenceByStatus(Integer status) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        return sentenceDao.getAllSentenceByStatus(connection,status);
    }

    @Override
    public List<Sentence> getAllSentenceInChecking() throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        return sentenceDao.getAllSentenceInChecking(connection);
    }

    @Override
    public List<Sentence> getAllSentenceInDay() throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        return sentenceDao.getAllSentenceInDay(connection);

    }

    @Override
    public List<Sentence> getAllSentenceInWeek() throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        return sentenceDao.getAllSentenceInWeek(connection);
    }

    @Override
    public List<Sentence> getAllSentenceInMonth() throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        return sentenceDao.getAllSentenceInMonth(connection);
    }

    @Override
    public Sentence getSentenceById(Long sentenceId) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        return sentenceDao.getSentenceById(connection,sentenceId);
    }

    @Override
    public int updateSentence(Sentence sentence) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        int i = (int) sentenceDao.updateIgnoreNull(connection, sentence);
        return i;

    }

    @Override
    public void insertSentence(Sentence sentence) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        sentenceDao.save(connection,sentence);
    }

    @Override
    public int deleteSentence(Sentence sentence) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        int i = (int) sentenceDao.delete(connection,sentence);
        return i;
    }
}