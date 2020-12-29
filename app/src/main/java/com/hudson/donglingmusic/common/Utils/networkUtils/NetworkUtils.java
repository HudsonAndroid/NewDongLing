package com.hudson.donglingmusic.common.Utils.networkUtils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hudson.donglingmusic.common.Utils.jsonUtils.JsonUtils;
import com.hudson.donglingmusic.common.Utils.networkUtils.exceptions.NetWorkInvalidException;
import com.hudson.donglingmusic.common.Utils.networkUtils.exceptions.RequestException;
import com.hudson.donglingmusic.common.Utils.networkUtils.inner.HttpRequestUtils;
import com.hudson.donglingmusic.module.data.requestParam.IRequestParam;

import java.io.IOException;
import java.net.UnknownHostException;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Hudson on 2020/2/29.
 */
public class NetworkUtils {
    private static final String TAG = "NetworkUtils";

    public static <T> T requestData(@NonNull IRequestParam param, @NonNull Class<T> tClass){
        try {
            int type = param.getType();
            Response response;
            String url = param.getUrl();
            if(type == IRequestParam.TYPE_GET){
                response = HttpRequestUtils.get(url);
            }else{
                response = HttpRequestUtils.post(url, JsonUtils.toJsonString(param));
            }
            ResponseBody body = response.body();
            String result = null;
            if(body != null){
                result = body.string();
            }
//            Thread.dumpStack();
//            Log.e("hudson","请求数据了");
            Log.d(TAG,"request url:"+url+"，the result is : "+result);
            if(response.isSuccessful()){
                if(body != null){
                    T t = JsonUtils.fromJson(tClass, result);
                    if(t == null){
                        throw new RuntimeException("sorry,result cannot parse to "+tClass.getSimpleName()+";the result is"+result);
                    }
                    return t;
                }
            }else{
                int code = response.code();
                throw new RequestException(code,result);
            }
        }catch (UnknownHostException e){
            throw new NetWorkInvalidException();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
