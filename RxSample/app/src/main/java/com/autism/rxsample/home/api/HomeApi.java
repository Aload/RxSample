package com.autism.rxsample.home.api;

import com.autism.rxsample.home.model.BaseBean;
import com.autism.rxsample.home.model.HomeBannerBean;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Author：Autism on 2017/3/2 15:46
 * Used:
 */
public interface HomeApi {
    @GET("/recommend/tabimage")
    Observable<BaseBean<HomeBannerBean>> getHomeBanner();
}
