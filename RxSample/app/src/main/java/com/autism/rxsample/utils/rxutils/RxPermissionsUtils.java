package com.autism.rxsample.utils.rxutils;

import android.app.Activity;
import android.content.Context;

import com.autism.rxsample.BaseApplication;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

/**
 * Author：Autism on 2017/3/6 14:54
 * Used:
 */
public class RxPermissionsUtils {

    public static RxPermissionsUtils mPermission;

    public static RxPermissionsUtils getPermissionInstance() {
        if (null == mPermission) {
            synchronized (RxPermissionsUtils.class) {
                if (null == mPermission) {
                    mPermission = new RxPermissionsUtils();
                }
            }
        }
        return mPermission;
    }

    /**
     * 单个权限申请
     *
     * @param permission
     * @param permissionListener
     */
    public void requestSinglePremission(Context mActivity, String permission, final RxPermissionListener permissionListener) {
        RxPermissions.getInstance(mActivity).request(permission).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean grand) {
                if (grand) {
                    permissionListener.handlePermissionSuccess();
                } else {
                    permissionListener.handlePermissionFailed();
                }
            }
        });
    }
}
