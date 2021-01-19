package com.jack.project.root.utils;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author: chenqc
 * @date: 2020/10/23
 */
public class HttpUtils {
    public static RequestBody getRequestBody(Object bean){
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(bean));

    }}
