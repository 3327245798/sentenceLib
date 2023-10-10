package com.blp.sentenceLib.dao;

import com.blp.sentenceLib.entity.Firmware;

import com.blp.sentenceLib.entity.NewParam;
import com.fy.toolhelper.db.IBaseDao;

import java.sql.Connection;
import java.util.List;

/**
 * ()表数据库访问层
 *
 * @author makejava
 * @since 2022-05-06 20:34:27
 */
public interface FirmwareDao extends IBaseDao<Firmware> {

//获取所有的固件，不管是工具类型的还是WebService类型的都获取到
    List<Firmware> getAllFirmwares(Connection connection) throws Exception;

    //根据工具id获取该工具的所有部件
    List<Firmware> getFirmwaresByToolId(Connection connection, Long toolId)throws Exception;
    //根据词汇id获取固件信息
    Firmware getFirmwareInfoByWordId(Connection connection, Long wordId)throws Exception;



}