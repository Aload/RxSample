package com.autism.rxsample.home.presenter;

import com.autism.rxsample.home.model.BaseBean;
import com.autism.rxsample.home.model.HomeBannerBean;
import com.autism.rxsample.home.model.HomeReq;
import com.autism.rxsample.presenter.BasePresenterIml;
import com.autism.rxsample.presenter.BaseSubscribe;
import com.autism.rxsample.rxlistener.IRXSubcribListener;

import retrofit2.Retrofit;
import rx.Subscriber;

/**
 * Author：Autism on 2017/3/2 15:37
 * Used:
 */
public class HomeApiPresenterImp extends BasePresenterIml implements IRXSubcribListener<HomeBannerBean> {
    @Override
    protected void handleNetRes(Retrofit retrofit) {
        Subscriber<BaseBean<HomeBannerBean>> mSubcrib = new BaseSubscribe<>(this);
        HomeReq.getHomeInstance(retrofit).getHomeBanner(mSubcrib);
    }

    @Override
    public void getException(Exception mEx) {

    }

    @Override
    public void _onNext(HomeBannerBean mData) {

    }

    @Override
    public void _onError() {

    }
}
