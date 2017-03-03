package com.autism.rxsample.rxlistener;

/**
 * Author：Autism on 2017/3/2 14:31
 * Used: 基础的配置
 */
public interface IBaseRXListener {
    //域名配置
    String getUrl();

    //ssl证书
    int getRawSSl();

    //异常获取
    void getException(Exception mEx);
}
