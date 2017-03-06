package com.autism.rxsample.splash;

import com.autism.rxsample.common.base.BaseAct;
import com.autism.rxsample.presenter.BasePresenterIml;
import com.autism.rxsample.splash.presenter.SplashPresenterImp;

/**
 * Author：Autism on 2017/3/6 14:14
 * Used:
 */
public class SplashAct extends BaseAct {


    @Override
    protected void initVariable() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected BasePresenterIml getPresenter() {
        return new SplashPresenterImp();
    }
}
