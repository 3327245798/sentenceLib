package com.blp.sentenceLib.service;
import com.blp.sentenceLib.entity.NewParam;

import java.util.List;

/**
 * (NewParam)表服务接口
 *
 * @author makewz
 * @since 2022-11-15 20:34:27
 */
public interface NewParamService {


    //根据固件id和输入输出类型获取输入或输出参数
    List<NewParam> getParamstByFirmwareIdAndType(Long firmwareId, Integer in_out_type) throws Exception;
    //根据工具id和部件action获取输入参数列表
    List<NewParam> getInputParamsByToolIdAndApiAction(Long toolId, String apiAction,int inOutType) throws Exception;

    int updateNewParam(NewParam param) throws Exception;

    void insertNewParam(NewParam param) throws Exception;

    int deleteNewParam(NewParam param)throws Exception;
}