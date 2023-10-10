package com.blp.sentenceLib.service;

import com.blp.sentenceLib.entity.Unit;

import java.util.List;

/**
 * (Unit)表服务接口
 *
 * @author makejava
 * @since 2022-05-06 20:34:27
 */
public interface UnitService {

    /**
     * 获取所有的部件
     * @return
     */
    List<Unit> getAllUnits() throws Exception;
    List<Unit> getAllUnitsByWordID(Long wordId) throws Exception;
    //根据工具id获取该工具的所有部件
    List<Unit> getUnitsByToolId(Long toolId) throws Exception;
    //根据固件id获取固件相关信息
    Unit getUnitById(Long unitId) throws Exception;
    //根据工具id和部件action获取固件相关信息
    Unit getUnitByToolIdAndUnitAction(Long toolId,String unitAction) throws Exception;

    int updateUnit(Unit unit) throws Exception;

    void insertUnit(Unit unit) throws Exception;

    int deleteUnit(Unit unit)throws Exception;
}