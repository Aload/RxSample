package com.autism.rxsample.rxlistener;

/**
 * Author：Autism on 2017/3/2 14:31
 * Used:
 */
public interface IBaseRXListener {
    String getUrl();

    int getRawSSl();

    void getException(Exception mEx);
}
