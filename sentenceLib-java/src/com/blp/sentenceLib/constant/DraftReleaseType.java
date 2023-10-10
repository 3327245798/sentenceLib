package com.blp.sentenceLib.constant;

public enum DraftReleaseType {
    // 已发布
    RELEASED_DRAFT(0),
    // 未发布
    NOT_RELEASED_DRAFT(1);

    private Integer value;
    DraftReleaseType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
