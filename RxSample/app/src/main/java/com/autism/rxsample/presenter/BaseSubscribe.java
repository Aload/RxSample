package com.autism.rxsample.presenter;


import android.app.Activity;

import com.autism.rxsample.home.model.BaseBean;
import com.autism.rxsample.rxlistener.IRXSubcribListener;

import rx.Subscriber;

/**
 * Author：Autism on 2017/3/2 16:01
 * Used:
 */
public class BaseSubscribe<T> extends Subscriber<BaseBean<T>> {
    private Activity mActivity;
    private IRXSubcribListener<T> mSubListener;

    public BaseSubscribe(IRXSubcribListener<T> mSubListener) {
        super();
        this.mSubListener = mSubListener;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        mSubListener._onError();
    }

    @Override
    public void onNext(BaseBean<T> t) {
        mSubListener._onNext(t.getmData());
    }
}
