package com.autism.rxsample.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.autism.rxsample.presenter.BasePresenterIml;
import com.autism.rxsample.utils.eventbus.EventBusUtils;

/**
 * Author：Autism on 2017/3/6 14:16
 * Used:
 */
public abstract class BaseAct extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initVariable();
        getPresenter().onInit();
    }

    protected BasePresenterIml getPresenter() {
        return null;
    }

    //初始化相关的参数
    protected abstract void initVariable();

    //初始化view
    protected abstract void initView();

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBusUtils.onRegisterEventBus(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtils.onUnRegisterEnventBus(this);
    }
}
