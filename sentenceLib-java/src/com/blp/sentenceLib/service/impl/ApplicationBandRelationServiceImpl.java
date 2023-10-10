package com.blp.sentenceLib.service.impl;

import com.fy.basejar.tool.ActionToolBase;
import com.blp.sentenceLib.dao.ApplicationBandRelationDao;
import com.blp.sentenceLib.entity.ApplicationBandRelation;
import com.blp.sentenceLib.entity.Band;
import com.blp.sentenceLib.service.ApplicationBandRelationService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

/**
 * (ApplicationBandRelation)表服务实现类
 *
 * @author makejava
 * @since 2022-02-28 17:02:28
 */
@Service
@NoArgsConstructor
public class ApplicationBandRelationServiceImpl implements ApplicationBandRelationService {

    @Autowired
    ApplicationBandRelationDao applicationBandRelationDao;

    @Override
    public void insertRelation(List<Band> bandList, long id) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        for (Band band : bandList) {
            ApplicationBandRelation applicationBandRelation = new ApplicationBandRelation();
            applicationBandRelation.setBandId(band.getBandId());
            applicationBandRelation.setBandName(band.getBandName());
            applicationBandRelation.setAppCaseId(id);
            applicationBandRelationDao.save(connection, applicationBandRelation);
        }
    }
}
