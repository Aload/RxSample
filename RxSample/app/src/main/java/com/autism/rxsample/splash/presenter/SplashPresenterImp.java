package com.autism.rxsample.splash.presenter;

import android.Manifest;

import com.autism.rxsample.BaseApplication;
import com.autism.rxsample.presenter.BasePresenterIml;
import com.autism.rxsample.utils.rxutils.RxPermissionListener;
import com.autism.rxsample.utils.rxutils.RxPermissionsUtils;


import retrofit2.Retrofit;

/**
 * Author：Autism on 2017/3/6 14:48
 * Used:
 */
public class SplashPresenterImp extends BasePresenterIml implements RxPermissionListener {
    @Override
    protected void handleNetRes(Retrofit retrofit) {
        RxPermissionsUtils.getPermissionInstance().requestSinglePremission(BaseApplication.getmContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE, this);
    }

    @Override
    public void getException(Exception mEx) {

    }

    @Override
    public void handlePermissionSuccess() {

    }

    @Override
    public void handlePermissionFailed() {

    }
}
