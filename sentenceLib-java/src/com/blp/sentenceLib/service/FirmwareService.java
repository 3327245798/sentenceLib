package com.blp.sentenceLib.service;

import com.blp.sentenceLib.entity.Firmware;
import com.blp.sentenceLib.entity.Unit;

import java.util.List;

/**
 * (Unit)表服务接口
 *
 * @author makejava
 * @since 2022-05-06 20:34:27
 */
public interface FirmwareService {

    /**
     * 获取所有的部件
     * @return
     */
    List<Firmware> getAllFirmwares() throws Exception;

    //根据工具id获取该工具的所有部件
    List<Firmware> getFirmwaresByToolId(Long toolId) throws Exception;
    /**根据词汇id获取firmware表中的工具id,工具名称，部件id,部件名称及参数表中的参数信息 ，
    输入输出参数类型，是否必填，参数中英文名称，父参数id*/
    Firmware getToolInfoFirmwareInfoParamInfoByWordId(Long wordId) throws Exception;

    int updateFirmware(Firmware firmware) throws Exception;

    void insertFirmware(Firmware firmware) throws Exception;

    int deleteFirmware(Firmware firmware)throws Exception;
}