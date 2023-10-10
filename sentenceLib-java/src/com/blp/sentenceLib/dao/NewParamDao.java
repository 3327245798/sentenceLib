package com.blp.sentenceLib.dao;

import com.blp.sentenceLib.entity.NewParam;
import com.fy.toolhelper.db.IBaseDao;

import java.sql.Connection;
import java.util.List;

/**
 * (Unit)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-06 20:34:27
 */
public interface NewParamDao extends IBaseDao<NewParam> {


    //根据固件id和输入输出类型获取输入或输出参数
    List<NewParam> getParamstByFirmwareIdAndType(Connection connection, Long firmwareId, Integer in_out_type) throws Exception;

    /*根据词汇id获取firmware表中的工具id,工具名称，部件id,部件名称及参数表中的参数信息 ，
    输入输出参数类型，是否必填，参数中英文名称，父参数id*/
    List<NewParam> getToolInfoFirmwareInfoParamInfoByWordId(Connection connection, Long wordId)throws  Exception;
/*根据工具id和部件action与参数表（params）链表查询获取输入参数*/
    List<NewParam>  getInputParamsByToolIdAndApiAction(Connection connection, Long toolId,String apiAction,int inOutType)throws  Exception;
}