package com.blp.sentenceLib.dao.impl;

import com.blp.sentenceLib.dao.UnitDao;
import com.blp.sentenceLib.entity.Unit;
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
public class UnitDaoImpl extends BaseDaoImpl<Unit> implements UnitDao {

    public UnitDaoImpl() throws Exception {}

    @Override
    public List<Unit> getAllUnits(Connection connection) throws Exception {
        String sql = "SELECT * FROM blp_unit";
        PreparedStatement pstm = connection.prepareStatement(sql);


        ResultSet rs = pstm.executeQuery();
        List<Unit> unitList = new ArrayList<>();
        while (rs.next()) {
            Unit unit = new Unit();
            Unit unitEntity = getUnitEntity(rs);
            BeanUtils.copyProperties(unitEntity, unit);
            unitList.add(unit);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return unitList;
    }

    @Override
    public List<Unit> getAllUnitsByWordID(Connection connection,Long wordId) throws Exception {

        String sql = "SELECT * FROM blp_unit WHERE word_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setLong(1, wordId);

        ResultSet rs = pstm.executeQuery();
        List<Unit> unitList = new ArrayList<>();
        while (rs.next()) {
            Unit unit = new Unit();
            Unit unitEntity = getUnitEntity(rs);
            BeanUtils.copyProperties(unitEntity, unit);
            unitList.add(unit);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return unitList;
    }
//根据工具id获取该工具的所有部件
    @Override
    public List<Unit> getUnitsByToolId(Connection connection, Long toolId) throws Exception {

        String sql = "SELECT * FROM blp_unit WHERE tool_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setLong(1, toolId);

        ResultSet rs = pstm.executeQuery();
        List<Unit> unitList = new ArrayList<>();
        while (rs.next()) {
            Unit unit = new Unit();
            Unit unitEntity = getUnitEntity(rs);
            BeanUtils.copyProperties(unitEntity, unit);
            unitList.add(unit);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return unitList;
    }

    @Override
    public Unit getUnitById(Connection connection, Long unitId) throws Exception {
        Unit unit = null;
        StringBuilder sql = new StringBuilder("SELECT * FROM blp_unit WHERE id = ? ");

        PreparedStatement pstm = connection.prepareStatement(sql.toString());
        pstm.setLong(1, unitId);
        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {
            unit = getUnitEntity(rs);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return unit;

    }
//根据工具id和部件action获取Unit实体
    @Override
    public Unit getUnitByToolIdAndUnitAction(Connection connection, Long toolId, String unitAction) throws Exception {
        Unit unit = null;
        StringBuilder sql = new StringBuilder("SELECT * FROM blp_unit WHERE tool_id = ? and action=? ");

        PreparedStatement pstm = connection.prepareStatement(sql.toString());
        pstm.setLong(1, toolId);
        pstm.setString(2, unitAction);
        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {
            unit = getUnitEntity(rs);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return unit;

    }

    private Unit getUnitEntity(ResultSet rs) throws SQLException {
        Unit unit = new Unit();
        unit.setId(rs.getLong("id"));
        unit.setToolName(rs.getString("tool_name"));
        unit.setToolId(rs.getLong("tool_id"));
        unit.setAction(rs.getString("action"));
        unit.setName(rs.getString("name"));
        unit.setClazz(rs.getString("clazz"));
        unit.setParams(rs.getString("params"));
        unit.setResult(rs.getString("result"));
        unit.setUnitType(rs.getInt("unit_type"));
        unit.setWordId(rs.getLong("word_id"));
        return unit;
    }

}