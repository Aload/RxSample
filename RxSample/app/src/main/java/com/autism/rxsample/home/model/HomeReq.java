package com.autism.rxsample.home.model;

import com.autism.rxsample.home.api.HomeApi;
import com.autism.rxsample.utils.rxutils.RxThreadUtils;

import retrofit2.Retrofit;
import rx.Subscriber;

/**
 * Author：Autism on 2017/3/2 15:38
 * Used:
 */
public class HomeReq {
    private static HomeReq mHomeReq;
    private HomeApi mHomeApi;

    private HomeReq(Retrofit mRetrofit) {
        mHomeApi = mRetrofit.create(HomeApi.class);
    }

    public static HomeReq getHomeInstance(Retrofit mRetrofit) {
        if (null == mHomeReq) synchronized (HomeReq.class) {
            if (null == mHomeReq)
                mHomeReq = new HomeReq(mRetrofit);
        }
        return mHomeReq;
    }

    public void getHomeBanner(Subscriber<BaseBean<HomeBannerBean>> mSubscribe) {
        mHomeApi.getHomeBanner().asObservable()
                .cache()
                .compose(RxThreadUtils.<BaseBean<HomeBannerBean>>ioMain())
                .subscribe(mSubscribe);
    }
}
