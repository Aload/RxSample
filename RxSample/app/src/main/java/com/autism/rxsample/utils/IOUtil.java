package com.autism.rxsample.utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Author：Autism on 2017/3/2 14:18
 * Used:
 */
public class IOUtil {
    public static void closeStream(InputStream mInputStream) {
        if (null != mInputStream)
            try {
                mInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
