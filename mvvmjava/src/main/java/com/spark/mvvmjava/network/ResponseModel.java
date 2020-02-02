package com.spark.mvvmjava.network;

import java.io.Serializable;

/*************************************************************************************************
 * 日期：2020/1/15 10:29
 * 作者：李加蒙
 * 邮箱：1829870839@qq.com
 * 描述：
 ************************************************************************************************/
public class ResponseModel<T> implements Serializable {
    public static final int ResultSuccess = 0;

    private T data;
    private int errorCode;
    private String errorMsg;


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isSuccess(){
        return ResultSuccess == errorCode;
    }
}
