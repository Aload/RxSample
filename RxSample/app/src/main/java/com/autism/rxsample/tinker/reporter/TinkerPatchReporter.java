package com.autism.rxsample.tinker.reporter;

import android.content.Context;
import android.content.Intent;

import com.autism.rxsample.tinker.UpgradePatchRetry;
import com.tencent.tinker.lib.reporter.DefaultPatchReporter;
import com.tencent.tinker.loader.shareutil.SharePatchInfo;

import java.io.File;

/**
 * @author autism
 * @used
 */
public class TinkerPatchReporter extends DefaultPatchReporter {
    public TinkerPatchReporter(Context context) {
        super(context);
    }

    @Override
    public void onPatchServiceStart(Intent intent) {
        super.onPatchServiceStart(intent);
        TinkerResultReport.onApplyPatchServiceStart();
        UpgradePatchRetry.getInstance(context).onPatchServiceStart(intent);
    }

    @Override
    public void onPatchDexOptFail(File patchFile, File dexFile, String optDirectory, String dexName, Throwable t) {
        super.onPatchDexOptFail(patchFile, dexFile, optDirectory, dexName, t);
        TinkerResultReport.onApplyDexOptFail(t);
    }

    @Override
    public void onPatchException(File patchFile, Throwable e) {
        super.onPatchException(patchFile, e);
        TinkerResultReport.onApplyCrash(e);
    }

    @Override
    public void onPatchInfoCorrupted(File patchFile, String oldVersion, String newVersion) {
        super.onPatchInfoCorrupted(patchFile, oldVersion, newVersion);
        TinkerResultReport.onApplyInfoCorrupted();
    }

    @Override
    public void onPatchPackageCheckFail(File patchFile, int errorCode) {
        super.onPatchPackageCheckFail(patchFile, errorCode);
        TinkerResultReport.onApplyPackageCheckFail(errorCode);
    }

    @Override
    public void onPatchResult(File patchFile, boolean success, long cost) {
        super.onPatchResult(patchFile, success, cost);
        TinkerResultReport.onApplied(cost, success);
        UpgradePatchRetry.getInstance(context).onPatchServiceResult();
    }

    @Override
    public void onPatchTypeExtractFail(File patchFile, File extractTo, String filename, int fileType) {
        super.onPatchTypeExtractFail(patchFile, extractTo, filename, fileType);
        TinkerResultReport.onApplyExtractFail(fileType);
    }

    @Override
    public void onPatchVersionCheckFail(File patchFile, SharePatchInfo oldPatchInfo, String patchFileVersion) {
        super.onPatchVersionCheckFail(patchFile, oldPatchInfo, patchFileVersion);
        TinkerResultReport.onApplyVersionCheckFail();
    }
}
