package com.blp.sentenceLib.exception;

import com.blp.sentenceLib.constant.ResultCodeEnum;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BLPException extends Exception{
    private Integer code;
    private String message;
    private String detail;

    public BLPException(String 要判断的主体丢失){
        super();
    }
    public BLPException(ResultCodeEnum resultCodeEnum){
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMsg();
    }

    public BLPException(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public BLPException(Integer code, String message, String detail){
        this.code = code;
        this.message = message;
        this.detail = detail;
    }
}
