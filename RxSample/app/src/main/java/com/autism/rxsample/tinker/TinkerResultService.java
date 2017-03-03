
package com.autism.rxsample.tinker;

import com.autism.rxsample.utils.SharedPreferenceUtil;
import com.tencent.tinker.lib.service.DefaultTinkerResultService;
import com.tencent.tinker.lib.service.PatchResult;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.lib.util.TinkerServiceInternals;

import java.io.File;


/**
 * @author autism
 *         加载服务
 */
public class TinkerResultService extends DefaultTinkerResultService {

    private static final String TAG = "Tinker";

    @Override
    public void onPatchResult(final PatchResult result) {
        if (result == null) {
            TinkerLog.e(TAG, "TinkerResultService received null result!!!!");
            return;
        }
        TinkerLog.d(TAG, "TinkerResultService receive result:" + result.toString());

        TinkerServiceInternals.killTinkerPatchServiceProcess(getApplicationContext());

        if (result.isSuccess) {
            File patchFile = new File(result.rawPatchFilePath);
            if (patchFile.exists() && patchFile.delete()) {
                TinkerLog.d(TAG, "补丁删除成功");
            }
            String fileName = patchFile.getName();//文件名字就是MD5值
            SharedPreferenceUtil.saveValue("tinker_patch", "sample_patch", fileName);
            if (checkIfNeedKill(result)) {
                if (Utils.isBackground()) {
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }
            TinkerLog.d(TAG, "补丁修复成功");
        } else {
            TinkerLog.d(TAG, "补丁修复失败");
        }
    }
}
