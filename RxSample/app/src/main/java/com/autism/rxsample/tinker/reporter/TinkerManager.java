package com.autism.rxsample.tinker.reporter;

import com.autism.rxsample.tinker.TinkerResultService;
import com.autism.rxsample.tinker.TinkerUncaughtExceptionHandler;
import com.autism.rxsample.tinker.UpgradePatchRetry;
import com.tencent.tinker.lib.listener.PatchListener;
import com.tencent.tinker.lib.patch.AbstractPatch;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.LoadReporter;
import com.tencent.tinker.lib.reporter.PatchReporter;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.loader.app.ApplicationLike;


/**
 * @author autism
 *         Tinker管理器
 */
public class TinkerManager {
    private static final String TAG = "Tinker";

    private static ApplicationLike mApplicationLike;
    private static TinkerUncaughtExceptionHandler mUncaughtExceptionHandler;
    private static boolean isInstalled = false;

    public static void setTinkerApplicationLike(ApplicationLike appLike) {
        mApplicationLike = appLike;
    }

    public static ApplicationLike getTinkerApplicationLike() {
        return mApplicationLike;
    }

    public static void initFastCrashProtect() {
        if (mUncaughtExceptionHandler == null) {
            mUncaughtExceptionHandler = new TinkerUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(mUncaughtExceptionHandler);
        }
    }

    public static void setUpgradeRetryEnable(boolean enable) {
        UpgradePatchRetry.getInstance(mApplicationLike.getApplication()).setRetryEnable(enable);
    }

    /**
     * tinker安装封装
     *
     * @param appLike
     */
    public static void installTinker(ApplicationLike appLike) {
        if (isInstalled) {
            TinkerLog.w(TAG, "install tinker, but has installed, ignore");
            return;
        }
        LoadReporter loadReporter = new TinkerLoadReporter(appLike.getApplication());
        PatchReporter patchReporter = new TinkerPatchReporter(appLike.getApplication());
        PatchListener patchListener = new TinkerPatchListener(appLike.getApplication());
        AbstractPatch upgradePatchProcessor = new UpgradePatch();

        TinkerInstaller.install(appLike, loadReporter, patchReporter, patchListener,
                TinkerResultService.class, upgradePatchProcessor);
        isInstalled = true;
    }
}
