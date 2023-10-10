package com.blp.sentenceLib.dao;

import com.blp.sentenceLib.entity.Unit;
import com.fy.toolhelper.db.IBaseDao;

import java.sql.Connection;
import java.util.List;

/**
 * (Unit)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-06 20:34:27
 */
public interface UnitDao extends IBaseDao<Unit> {


    List<Unit> getAllUnits(Connection connection) throws Exception;
    List<Unit> getAllUnitsByWordID(Connection connection,Long wordId) throws Exception;
    //根据工具id获取该工具的所有部件
    List<Unit> getUnitsByToolId(Connection connection,Long toolId)throws Exception;
    //根据固件id获取该固件相关信息，主要是为了获取参数
    Unit getUnitById(Connection connection,Long unitId) throws Exception;
    //根据工具id和固件action获取固件信息
    Unit getUnitByToolIdAndUnitAction(Connection connection,Long toolId,String unitAction) throws Exception;

}