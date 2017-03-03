package com.autism.rxsample.rxlistener;

/**
 * Author：Autism on 2017/3/2 16:13
 * Used:
 */
public interface IRXSubcribListener<T> {
    void _onNext(T mData);

    void _onError();
}
