package com.autism.rxsample.tinker.reporter;

import android.content.Context;
import android.os.Looper;
import android.os.MessageQueue;

import com.autism.rxsample.tinker.UpgradePatchRetry;
import com.tencent.tinker.lib.reporter.DefaultLoadReporter;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;

import java.io.File;

/**
 * @author autism
 * @used tinker加载回调
 */
public class TinkerLoadReporter extends DefaultLoadReporter {
    private final static String TAG = "Tinker.TinkerLoadReporter";

    public TinkerLoadReporter(Context context) {
        super(context);
    }

    @Override
    public void onLoadPatchListenerReceiveFail(final File patchFile, int errorCode) {
        super.onLoadPatchListenerReceiveFail(patchFile, errorCode);
        TinkerResultReport.onTryApplyFail(errorCode);
    }

    @Override
    public void onLoadResult(File patchDirectory, int loadCode, long cost) {
        super.onLoadResult(patchDirectory, loadCode, cost);
        switch (loadCode) {
            case ShareConstants.ERROR_LOAD_OK:
                TinkerResultReport.onLoaded(cost);
                break;
        }
        Looper.getMainLooper().myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                UpgradePatchRetry.getInstance(context).onPatchRetryLoad();
                return false;
            }
        });
    }

    @Override
    public void onLoadException(Throwable e, int errorCode) {
        super.onLoadException(e, errorCode);
        switch (errorCode) {
            case ShareConstants.ERROR_LOAD_EXCEPTION_UNCAUGHT:
                String uncaughtString = SharePatchFileUtil.checkTinkerLastUncaughtCrash(context);
                if (!ShareTinkerInternals.isNullOrNil(uncaughtString)) {
                    File laseCrashFile = SharePatchFileUtil.getPatchLastCrashFile(context);
                    SharePatchFileUtil.safeDeleteFile(laseCrashFile);
                    TinkerLog.e(TAG, "tinker uncaught real exception:" + uncaughtString);
                }
                break;
        }
        TinkerResultReport.onLoadException(e, errorCode);
    }

    @Override
    public void onLoadFileMd5Mismatch(File file, int fileType) {
        super.onLoadFileMd5Mismatch(file, fileType);
        TinkerResultReport.onLoadFileMisMatch(fileType);
    }

    /**
     * try to recover patch oat file
     *
     * @param file
     * @param fileType
     * @param isDirectory
     */
    @Override
    public void onLoadFileNotFound(File file, int fileType, boolean isDirectory) {
        TinkerLog.i(TAG, "patch loadReporter onLoadFileNotFound: patch file not found: %s, fileType:%d, isDirectory:%b",
                file.getAbsolutePath(), fileType, isDirectory);

        if (fileType == ShareConstants.TYPE_DEX_OPT) {
            Tinker tinker = Tinker.with(context);
            if (tinker.isMainProcess()) {
                File patchVersionFile = tinker.getTinkerLoadResultIfPresent().patchVersionFile;
                if (patchVersionFile != null) {
                    if (UpgradePatchRetry.getInstance(context).onPatchListenerCheck(SharePatchFileUtil.getMD5(patchVersionFile))) {
                        TinkerLog.i(TAG, "try to repair oat file on patch process");
                        TinkerInstaller.onReceiveUpgradePatch(context, patchVersionFile.getAbsolutePath());
                    } else {
                        TinkerLog.i(TAG, "repair retry exceed must max time, just clean");
                        checkAndCleanPatch();
                    }
                }
            }
        } else {
            checkAndCleanPatch();
        }
        TinkerResultReport.onLoadFileNotFound(fileType);
    }

    @Override
    public void onLoadPackageCheckFail(File patchFile, int errorCode) {
        super.onLoadPackageCheckFail(patchFile, errorCode);
        TinkerResultReport.onLoadPackageCheckFail(errorCode);
    }

    @Override
    public void onLoadPatchInfoCorrupted(String oldVersion, String newVersion, File patchInfoFile) {
        super.onLoadPatchInfoCorrupted(oldVersion, newVersion, patchInfoFile);
        TinkerResultReport.onLoadInfoCorrupted();
    }

    @Override
    public void onLoadPatchVersionChanged(String oldVersion, String newVersion, File patchDirectoryFile, String currentPatchName) {
        super.onLoadPatchVersionChanged(oldVersion, newVersion, patchDirectoryFile, currentPatchName);
    }

}
