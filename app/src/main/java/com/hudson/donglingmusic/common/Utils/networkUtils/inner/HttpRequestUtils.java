package com.hudson.donglingmusic.common.Utils.networkUtils.inner;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Hudson on 2020/2/29.
 */
public class HttpRequestUtils {
    private static volatile OkHttpClient sClient = null;

    public static OkHttpClient getClient(){
        if(sClient == null){
            synchronized (HttpRequestUtils.class){
                if(sClient == null){
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(@NonNull Chain chain) throws IOException {
                            Request newRequest = chain.request().newBuilder()
                                    .removeHeader("User-Agent")
                                    .addHeader("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
                                    .build() ;
                            return chain.proceed(newRequest);
                        }
                    });
                    sClient = builder.build();
                }
            }
        }
        return sClient;
    }

    public static Response post(String url,String jsonParam) throws IOException {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(@NonNull Chain chain) throws IOException {
//                Request newRequest = chain.request().newBuilder()
//                        .removeHeader("User-Agent")
//                        .addHeader("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
//                        .build() ;
//                return chain.proceed(newRequest);
//            }
//        }) ;
//        OkHttpClient client = builder.build();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                jsonParam);
        return getClient().newCall(new Request.Builder()
                .url(url)
                .post(body)
                .build()).execute();
    }

    public static Response get(String url) throws IOException {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(@NonNull Chain chain) throws IOException {
//                Request newRequest = chain.request().newBuilder()
//                        .removeHeader("User-Agent")
//                        .addHeader("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
//                        .build() ;
//                return chain.proceed(newRequest);
//            }
//        }) ;
//        OkHttpClient client = builder.build();
        return getClient().newCall(new Request.Builder().url(url).build()).execute();
    }
}
