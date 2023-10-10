package com.blp.sentenceLib.dao.impl;

import com.blp.sentenceLib.dao.ApplicationBandRelationDao;
import com.blp.sentenceLib.entity.ApplicationBandRelation;
import com.fy.toolhelper.db.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class ApplicationBandRelationDaoImpl extends BaseDaoImpl<ApplicationBandRelation> implements ApplicationBandRelationDao {
    public ApplicationBandRelationDaoImpl() throws Exception {
    }
}
