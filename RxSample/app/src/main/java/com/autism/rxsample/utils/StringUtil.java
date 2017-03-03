package com.autism.rxsample.utils;

import android.text.TextUtils;

/**
 * Author：Autism on 2017/3/2 13:55
 * Used:
 */
public class StringUtil {
    public static boolean isNullOrEmpty(String mString) {
        if (TextUtils.isEmpty(mString)) {
            return true;
        } else return false;
    }

}
