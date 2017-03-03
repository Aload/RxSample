package com.autism.rxsample.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.autism.rxsample.BaseApplication;

/**
 * Author：Autism on 2017/3/3 11:29
 * Used:
 */
public class SharedPreferenceUtil {

    public static synchronized void saveValue(String fileName, String key, Object value) {
        if (!StringUtil.isNullOrEmpty(key)) {
            SharedPreferences sharedPreferences = BaseApplication.getmContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (value != null) {
                if (value instanceof String) {
                    editor.putString(key, String.valueOf(value));
                } else if (value instanceof Integer) {
                    editor.putInt(key, (Integer) value);
                } else if (value instanceof Boolean) {
                    editor.putBoolean(key, (Boolean) value);
                } else if (value instanceof Float) {
                    editor.putFloat(key, (Float) value);
                } else if (value instanceof Long) {
                    editor.putLong(key, (Long) value);
                }
                editor.apply();
            }
        }
    }
}
