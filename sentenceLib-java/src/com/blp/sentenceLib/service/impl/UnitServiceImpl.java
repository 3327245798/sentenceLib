package com.blp.sentenceLib.service.impl;

import com.fy.basejar.tool.ActionToolBase;
import com.blp.sentenceLib.dao.UnitDao;
import com.blp.sentenceLib.entity.Unit;
import com.blp.sentenceLib.service.UnitService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

/**
 * (Unit)表服务实现类
 *
 * @author makejava
 * @since 2022-05-06 20:34:27
 */
@Service
@NoArgsConstructor
public class UnitServiceImpl implements UnitService {

    @Autowired
    private UnitDao unitDao;

    @Override
    public List<Unit> getAllUnits() throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        return unitDao.getAllUnits(connection);
    }

    @Override
    public List<Unit> getAllUnitsByWordID(Long wordId) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        return unitDao.getAllUnitsByWordID(connection,wordId);
    }
    //根据工具id获取该工具的所有部件wz
    @Override
    public List<Unit> getUnitsByToolId(Long toolId) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        return unitDao.getUnitsByToolId(connection,toolId);
    }
   //根据固件id获取固件相关信息
    @Override
    public Unit getUnitById(Long unitId) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        return unitDao.getUnitById(connection,unitId);

    }

    @Override
    public Unit getUnitByToolIdAndUnitAction(Long toolId, String unitAction) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        return unitDao.getUnitByToolIdAndUnitAction(connection,toolId,unitAction);
    }

    @Override
    public int updateUnit(Unit unit) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        int i= (int) unitDao.updateIgnoreNull(connection,unit);
        return i;
    }

    @Override
    public void insertUnit(Unit unit) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        unitDao.save(connection,unit);

    }

    @Override
    public int deleteUnit(Unit unit) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        int i= (int) unitDao.delete(connection,unit);
        return i;

    }
}