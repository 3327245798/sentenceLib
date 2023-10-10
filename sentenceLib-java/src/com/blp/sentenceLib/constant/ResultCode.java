package com.blp.sentenceLib.constant;
/**
 * @description:
 * 自定义响应状态码
 * @author xiaoliang
 * @date 2021/6/30 10:17
 * @version 1.0
 */
public interface ResultCode {

    // 调试状态码
    Integer DEBUG_SUCCESS = 10000;
    Integer DEBUG_ERROR_BANDNAME = 10001;
    Integer DEBUG_ERROR_USERNAME = 10002;
    Integer DEBUG_ERROR_TOOLNAME = 10003;
    Integer DEBUG_ERROR_UNITNAME = 10004;
    Integer DEBUG_ERROR_PARAMNAME = 10005;
    Integer DEBUG_ERROR_PARAMTYPENAME = 10006;
    Integer DEBUG_ERROR_PERMISSION = 10007;

    // 运行状态码
    Integer RUN_SUCCESS = 20000;
    Integer RUN_ERROR_BANDNAME = 20001;
    Integer RUN_ERROR_USERNAME = 20002;
    Integer RUN_ERROR_TOOLNAME = 20003;
    Integer RUN_ERROR_UNITNAME = 20004;
    Integer RUN_ERROR_PARAMNAME = 20005;
    Integer RUN_ERROR_PARAMTYPENAME = 20006;
    Integer RUN_ERROR_PERMISSION = 20005;

    // 状态码
    Integer SUCCESS = 200;
    Integer ERROR = 400;
}
