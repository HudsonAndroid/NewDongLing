package com.hudson.donglingmusic.module.data.requestParam;

/**
 * Created by Hudson on 2020/2/29.
 */
public interface IRequestParam {
    int TYPE_POST = 0;
    int TYPE_GET = 1;
    String getUrl();
    int getType();
}
