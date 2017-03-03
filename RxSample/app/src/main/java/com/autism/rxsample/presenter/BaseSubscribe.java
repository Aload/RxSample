package com.autism.rxsample.presenter;


import android.app.Activity;

import com.autism.rxsample.common.Constance;
import com.autism.rxsample.home.model.BaseBean;
import com.autism.rxsample.rxlistener.IRXSubcribListener;

import rx.Subscriber;

/**
 * Author：Autism on 2017/3/2 16:01
 * Used:
 */
public class BaseSubscribe<T> extends Subscriber<BaseBean<T>> {
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
        mSubListener._onError(Constance.RX_ERROR_CODE);
    }

    @Override
    public void onNext(BaseBean<T> t) {
        mSubListener._onNext(t.getmData());
        switch (t.getCode()) {
            case Constance.NET_ERROR_CODE:
                mSubListener._onError(Constance.NET_ERROR_CODE);
                break;
            case Constance.SERVER_ERROR_CODE:
                mSubListener._onError(Constance.SERVER_ERROR_CODE);
                break;
            case Constance.PARAMTER_ERROR_CODE:
                mSubListener._onError(Constance.PARAMTER_ERROR_CODE);
                break;
            case Constance.NORMAL_CODE:
                mSubListener._onNext(t.getmData());
                break;
            default:
                new Throwable("無效代碼").printStackTrace();
                break;
        }
    }
}
