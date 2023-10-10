package com.blp.sentenceLib.constant;

public enum DataType {
    // 单值赋值标识
    SINGLE_VALUE_TYPE("1"),
    // 集合赋值标识
    COLLECTION_VALUE_TYPE("2"),
    // 标识与值的分隔符
    SPERATOR("$$$");

    private String value;
    DataType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
