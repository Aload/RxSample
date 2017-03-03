package com.autism.rxsample.tinker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;

import com.autism.rxsample.tinker.reporter.TinkerManager;
import com.autism.rxsample.tinker.reporter.TinkerResultReport;
import com.tencent.tinker.lib.tinker.TinkerApplicationHelper;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;


/**
 * optional, use dynamic configuration is better way
 * for native crash,
 * <p/>
 * Created by zhangshaowen on 16/7/3.
 * tinker's crash is caught by {@code LoadReporter.onLoadException}
 * use {@code TinkerApplicationHelper} api, no need to install tinker!
 */
public class TinkerUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "Tinker.SampleUncaughtExHandler";

    private final Thread.UncaughtExceptionHandler ueh;
    private static final long   QUICK_CRASH_ELAPSE  = 10 * 1000;
    public static final  int    MAX_CRASH_COUNT     = 3;
    private static final String DALVIK_XPOSED_CRASH = "Class ref in pre-verified class resolved to unexpected implementation";

    public TinkerUncaughtExceptionHandler() {
        ueh = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        TinkerLog.e(TAG, "uncaughtException:" + ex.getMessage());
        tinkerFastCrashProtect();
        tinkerPreVerifiedCrashHandler(ex);
        ueh.uncaughtException(thread, ex);
    }

    /**
     * Such as Xposed, if it try to load some class before we load from patch files.
     * With dalvik, it will crash with "Class ref in pre-verified class resolved to unexpected implementation".
     * With art, it may crash at some times. But we can't know the actual crash type.
     * If it use Xposed, we can just clean patch or mention user to uninstall it.
     */
    private void tinkerPreVerifiedCrashHandler(Throwable ex) {
        Throwable throwable = ex;
        boolean isXposed = false;
        while (throwable != null) {
            if (!isXposed) {
                isXposed = Utils.isXposedExists(throwable);
            }
            if (isXposed) {
                //method 1
                ApplicationLike applicationLike = TinkerManager.getTinkerApplicationLike();
                if (applicationLike == null || applicationLike.getApplication() == null) {
                    return;
                }

                if (!TinkerApplicationHelper.isTinkerLoadSuccess(applicationLike)) {
                    return;
                }
                boolean isCausedByXposed = false;
                //for art, we can't know the actually crash type
                //just ignore art
                if (throwable instanceof IllegalAccessError && throwable.getMessage().contains(DALVIK_XPOSED_CRASH)) {
                    //for dalvik, we know the actual crash type
                    isCausedByXposed = true;
                }

                if (isCausedByXposed) {
                    TinkerResultReport.onXposedCrash();
                    TinkerLog.e(TAG, "have xposed: just clean tinker");
                    //kill all other process to ensure that all process's code is the same.
                    ShareTinkerInternals.killAllOtherProcess(applicationLike.getApplication());

                    TinkerApplicationHelper.cleanPatch(applicationLike);
                    ShareTinkerInternals.setTinkerDisableWithSharedPreferences(applicationLike.getApplication());
                    return;
                }
            }
            throwable = throwable.getCause();
        }
    }

    /**
     * if tinker is load, and it crash more than MAX_CRASH_COUNT, then we just clean patch.
     */
    private boolean tinkerFastCrashProtect() {
        ApplicationLike applicationLike = TinkerManager.getTinkerApplicationLike();

        if (applicationLike == null || applicationLike.getApplication() == null) {
            return false;
        }
        if (!TinkerApplicationHelper.isTinkerLoadSuccess(applicationLike)) {
            return false;
        }

        final long elapsedTime = SystemClock.elapsedRealtime() - applicationLike.getApplicationStartElapsedTime();
        //this process may not install tinker, so we use TinkerApplicationHelper api
        if (elapsedTime < QUICK_CRASH_ELAPSE) {
            String currentVersion = TinkerApplicationHelper.getCurrentVersion(applicationLike);
            if (ShareTinkerInternals.isNullOrNil(currentVersion)) {
                return false;
            }

            SharedPreferences sp = applicationLike.getApplication().getSharedPreferences(ShareConstants.TINKER_SHARE_PREFERENCE_CONFIG, Context.MODE_MULTI_PROCESS);
            int fastCrashCount = sp.getInt(currentVersion, 0) + 1;
            if (fastCrashCount >= MAX_CRASH_COUNT) {
                TinkerResultReport.onFastCrashProtect();
                TinkerApplicationHelper.cleanPatch(applicationLike);
                TinkerLog.e(TAG, "tinker has fast crash more than %d, we just clean patch!", fastCrashCount);
                return true;
            } else {
                sp.edit().putInt(currentVersion, fastCrashCount).apply();
                TinkerLog.e(TAG, "tinker has fast crash %d times", fastCrashCount);
            }
        }

        return false;
    }
}
