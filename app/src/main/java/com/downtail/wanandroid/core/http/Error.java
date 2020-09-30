package com.downtail.wanandroid.core.http;

public enum Error {

    SUCCESS(0, "操作成功"),
    NO_DATA(-2, "暂无数据"),
    FILE_NOT_EXIST(-1, "找不到文件");
    private int errCode;
    private String errMsg;

    Error(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    @Override
    public String toString() {
        return "errCode " + this.errCode + ", errMsg: " + errMsg;
    }
}
