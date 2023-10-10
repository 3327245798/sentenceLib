package com.blp.sentenceLib.dao.impl;

import com.blp.sentenceLib.dao.FirmwareDao;
import com.blp.sentenceLib.entity.Firmware;
import com.blp.sentenceLib.entity.NewParam;
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
 * (Firmware)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-06 20:34:27
 */
@Repository
public class FirmwareDaoImpl extends BaseDaoImpl<Firmware> implements FirmwareDao {

    public FirmwareDaoImpl() throws Exception {}


    @Override
    public List<Firmware> getAllFirmwares(Connection connection) throws Exception {
        String sql = "SELECT * FROM blp_firmware";
        PreparedStatement pstm = connection.prepareStatement(sql);


        ResultSet rs = pstm.executeQuery();
        List<Firmware> firmwareList = new ArrayList<>();
        while (rs.next()) {
            Firmware firmware = new Firmware();
            Firmware firmwareEntity = getFirmwareEntity(rs);
            BeanUtils.copyProperties(firmwareEntity, firmware);
            firmwareList.add(firmware);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return firmwareList;

    }


//根据工具id获取他的部件
    @Override
    public List<Firmware> getFirmwaresByToolId(Connection connection, Long toolId) throws Exception {

        String sql = "SELECT * FROM blp_firmware WHERE tool_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setLong(1, toolId);

        ResultSet rs = pstm.executeQuery();
        List<Firmware> firmwareList = new ArrayList<>();
        while (rs.next()) {
            Firmware firmware = new Firmware();
            Firmware firmwareEntity = getFirmwareEntity(rs);
            BeanUtils.copyProperties(firmwareEntity, firmware);
            firmwareList.add(firmware);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return firmwareList;
    }

    @Override
    public Firmware getFirmwareInfoByWordId(Connection connection, Long wordId) throws Exception {
        String sql = "SELECT f.* from bls_word w, blp_firmware f where w.id=? AND w.firmware_id = f.id";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setLong(1, wordId);

        ResultSet rs = pstm.executeQuery();
        Firmware firmware = null;
        if (rs.next()) {
            firmware = getFirmwareEntity(rs);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return firmware;
    }




    private Firmware getFirmwareEntity(ResultSet rs) throws SQLException {
        Firmware firmware = new Firmware();
        firmware.setId(rs.getLong("id"));
        firmware.setFirmwareName(rs.getString("firmware_name"));
        firmware.setToolId(rs.getLong("tool_id"));
        firmware.setToolName(rs.getString("tool_name"));
        firmware.setActionApi(rs.getString("action_api"));
        firmware.setFirmwareType(rs.getInt("firmware_type"));
        firmware.setFirmwareDesc(rs.getString("firmware_desc"));
        firmware.setBaseURL(rs.getString("base_url"));
        firmware.setRequestMethod(rs.getInt("request_method"));
        firmware.setCertification(rs.getString("certification"));
        firmware.setMaster(rs.getString("master"));
        firmware.setEmail(rs.getString("email"));
        firmware.setFirmwareCode(rs.getString("firmware_code"));

        return firmware;
    }
}