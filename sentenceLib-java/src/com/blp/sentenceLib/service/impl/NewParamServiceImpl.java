package com.blp.sentenceLib.service.impl;

import com.blp.sentenceLib.dao.FirmwareDao;
import com.blp.sentenceLib.dao.NewParamDao;
import com.blp.sentenceLib.entity.Firmware;
import com.blp.sentenceLib.entity.NewParam;
import com.blp.sentenceLib.service.FirmwareService;
import com.blp.sentenceLib.service.NewParamService;
import com.fy.basejar.tool.ActionToolBase;
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
public class NewParamServiceImpl implements NewParamService {

    @Autowired
    private NewParamDao newParamDao;


    @Override
    public List<NewParam> getParamstByFirmwareIdAndType(Long firmwareId, Integer in_out_type) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        return newParamDao.getParamstByFirmwareIdAndType(connection,firmwareId,in_out_type);
    }
//根据工具id和部件action与参数表连表查询获取输入或输出参数参数列表
    @Override
    public List<NewParam> getInputParamsByToolIdAndApiAction(Long toolId, String apiAction,int inOutType) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        return newParamDao.getInputParamsByToolIdAndApiAction(connection,toolId,apiAction, inOutType);
    }

    @Override
    public int updateNewParam(NewParam param) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        int i= (int) newParamDao.updateIgnoreNull(connection,param);
        return i;
    }

    @Override
    public void insertNewParam(NewParam param) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        newParamDao.save(connection,param);
    }

    @Override
    public int deleteNewParam(NewParam param) throws Exception {
        final Connection connection = ActionToolBase.getDBConnection();
        int i= (int) newParamDao.delete(connection,param);
        return i;
    }
}