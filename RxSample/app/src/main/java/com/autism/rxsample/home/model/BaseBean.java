package com.autism.rxsample.home.model;

/**
 * Author：Autism on 2017/3/2 15:55
 * Used:
 */
public class BaseBean<T> {
    private int code;
    private T mData;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getmData() {
        return mData;
    }

    public void setmData(T mData) {
        this.mData = mData;
    }
}
