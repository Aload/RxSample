package com.autism.rxsample;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.autism.rxsample.tinker.Log.TinkerLogImp;
import com.autism.rxsample.tinker.reporter.TinkerManager;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Author：Autism on 2017/3/2 14:10
 * Used:
 */
@DefaultLifeCycle(application = ".TinkerSampleApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false)
public class BaseApplication extends DefaultApplicationLike {

    private static Context mContext;
    private static BaseApplication mApplication;

    public BaseApplication(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        mContext = base;
        mApplication = this;
        initTinker();
    }

    public static Context getmContext() {
        if (null != mContext) return mContext;
        else return null;
    }

    public static BaseApplication getmApplication() {
        if (null != mApplication) return mApplication;
        else return null;
    }

    /**
     * 初始化Tinker
     */
    private void initTinker() {
        TinkerManager.setTinkerApplicationLike(this);
        TinkerManager.initFastCrashProtect();
        TinkerManager.setUpgradeRetryEnable(true);
        TinkerInstaller.setLogIml(new TinkerLogImp());
        TinkerManager.installTinker(this);
        Tinker.with(getApplication());
    }
}
