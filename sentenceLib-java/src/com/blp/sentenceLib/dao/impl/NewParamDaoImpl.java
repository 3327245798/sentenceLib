package com.blp.sentenceLib.dao.impl;


import com.blp.sentenceLib.dao.NewParamDao;
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
 * (NewParam)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-06 20:34:27
 */
@Repository
public class NewParamDaoImpl extends BaseDaoImpl<NewParam> implements NewParamDao {

    public NewParamDaoImpl() throws Exception {}

    @Override
    public List<NewParam> getToolInfoFirmwareInfoParamInfoByWordId(Connection connection, Long wordId) throws Exception {


        String sql = "SELECT p.* from bls_word w, blp_param p where w.id=? AND w.firmware_id = p.firmware_id";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setLong(1, wordId);

        ResultSet rs = pstm.executeQuery();
        List<NewParam> paramList = new ArrayList<>();
        while (rs.next()) {
            NewParam param = new NewParam();
            NewParam paramEntity = getNewParamEntity(rs);
            BeanUtils.copyProperties(paramEntity, param);
            paramList.add(param);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return paramList;
    }
//根据工具id和部件action与参数表params连表查询获取[输入或输出参数列表]
    @Override
    public List<NewParam> getInputParamsByToolIdAndApiAction(Connection connection, Long toolId, String apiAction,int inOutType) throws Exception {
        String sql = "SELECT p.* from blp_firmware f,blp_param p WHERE  f.tool_id=? AND f.action_api=? and p.in_out_type=? and f.id=p.firmware_id";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setLong(1, toolId);
        pstm.setString(2,apiAction);
        pstm.setInt(3,inOutType);

        ResultSet rs = pstm.executeQuery();
        List<NewParam> paramList = new ArrayList<>();
        while (rs.next()) {
            NewParam param = new NewParam();
            NewParam paramEntity = getNewParamEntity(rs);
            BeanUtils.copyProperties(paramEntity, param);
            paramList.add(param);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return paramList;
    }

    //这个方法是用来获取一些复杂的信息，这些信息存在于多个表中，参数表中的inouttype，cnname.name,固件表中的工具id，工具名称，固件id，固件名称
    private NewParam getNewParamEntity(ResultSet rs) throws SQLException {
        NewParam param = new NewParam();
        param.setId(rs.getLong("id"));
        param.setInOutType(rs.getInt("in_out_type"));
        param.setName(rs.getString("name"));
        param.setCnName(rs.getString("cn_name"));
        param.setDesc(rs.getString("desc"));
        param.setFirmwareId(rs.getLong("firmware_id"));
        param.setDefaultValue(rs.getString("default_value"));
        param.setRequired(rs.getInt("required"));
        param.setExistType(rs.getInt("exist_type"));
        param.setParentId(rs.getLong("parent_id"));
        param.setParmaPath(rs.getString("param_path"));
        param.setObjType(rs.getInt("obj_type"));

        return param;
    }
//根据固件id和类型获取输入或输出参数，1表示输入参数，2表示输出参数
    @Override
    public List<NewParam> getParamstByFirmwareIdAndType(Connection connection, Long firmwareId, Integer in_out_type) throws Exception {

        String sql ="SELECT * FROM blp_param WHERE firmware_id = ? and in_out_type=? ";

        PreparedStatement pstm = connection.prepareStatement(sql.toString());
        pstm.setLong(1, firmwareId);
        pstm.setInt(2, in_out_type);
        ResultSet rs = pstm.executeQuery();
        List<NewParam> paramList = new ArrayList<>();
        while (rs.next()) {
            NewParam param = new NewParam();
            NewParam paramEntity = getNewParamEntity(rs);
            BeanUtils.copyProperties(paramEntity, param);
            paramList.add(param);
        }
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        return paramList;

    }
}