package com.autism.rxsample.presenter;

import rx.Subscription;

/**
 * Author：Autism on 2017/3/2 14:52
 * Used:
 */
public interface IBasePresenter {
    //初始化时候调用,在BaseAct调用即可
    void onInit();

    void unSubscribe();

    void addSubscrebe(Subscription subscription);
}
