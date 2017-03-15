package com.autism.rxsample.api;

import com.autism.rxsample.BaseApplication;
import com.autism.rxsample.rxlistener.IBaseRXListener;
import com.autism.rxsample.utils.IOUtil;
import com.autism.rxsample.utils.StringUtil;
import com.autism.rxsample.utils.rxutils.HostVerifyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author：Autism on 2017/3/2 13:37
 * Used: 网络管理类
 */
public class RequestManger {
    private static final int DEFAULT_TIMEOUT = 10;
    private Retrofit mRetrofit;
    private static RequestManger mManager;

    public static RequestManger getRequestManager() {
        if (null == mManager) synchronized (RequestManger.class) {
            if (null == mManager)
                mManager = new RequestManger();
        }
        return mManager;
    }

    /**
     * 获取retrofit对象
     *
     * @param mListener
     * @return
     */
    public Retrofit getRetrofit(IBaseRXListener mListener) {
        String url = mListener.getUrl();
        if (null == mRetrofit || !url.equals(mRetrofit.baseUrl().toString())) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)//拦截器处理通用Header
                    .addInterceptor(loggingInterceptor) //日志处理
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

            //可以区别http,https
            if (!StringUtil.isNullOrEmpty(url) && url.toLowerCase().contains("https:")) {
                try {
                    builder.sslSocketFactory(getSSLSocketFactory(new int[]{mListener.getRawSSl()}));
                    builder.hostnameVerifier(new HostVerifyUtils(new String[]{url}));
                } catch (Exception e) {
                    mListener.getException(e);
                }
            }

            OkHttpClient client = builder.build();
            mRetrofit = new Retrofit.Builder()
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(url)
                    .build();
        }
        return mRetrofit;
    }

    /**
     * 获取ssl
     *
     * @param certificates
     * @return
     * @throws Exception
     */
    private static SSLSocketFactory getSSLSocketFactory(int[] certificates) throws Exception {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);

        for (int i = 0; i < certificates.length; i++) {
            InputStream certificate = BaseApplication.getmContext().getResources().openRawResource(certificates[i]);
            keyStore.setCertificateEntry(String.valueOf(i), certificateFactory.generateCertificate(certificate));
            IOUtil.closeStream(certificate);
        }
        SSLContext sslContext = SSLContext.getInstance("TLS");
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
        return sslContext.getSocketFactory();
    }

    /**
     * 添加头部信息
     */
    private static Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            String token = "";

            Request newRequest = request.newBuilder()
                    .addHeader("AC-X-TYPE", "1")
                    .addHeader("AC-X-TOKEN", token)
                    .addHeader("AC-X-VERSION", "")
                    .addHeader("Accept", "application/json")
                    .build();

            return chain.proceed(newRequest);
        }
    };

    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
        }
    }).setLevel(HttpLoggingInterceptor.Level.BODY);

}
