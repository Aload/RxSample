package com.autism.rxsample.utils.rxutils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Author：Autism on 2017/3/2 14:02
 * Used:
 */
public class HostVerifyUtils implements HostnameVerifier {
    private String[] mHostUrl;

    public HostVerifyUtils(String[] mVerifyName) {
        this.mHostUrl = mVerifyName;
    }

    @Override
    public boolean verify(String hostname, SSLSession session) {
        if (null != mHostUrl || mHostUrl.length != 0) {
            for (String mUrl : mHostUrl) {
                if (mUrl.equals(hostname)) {
                    return true;
                }
            }
        }
        return false;
    }
}
