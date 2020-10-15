package com.simga.library.http;

import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class DecodeResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final TypeAdapter<T> adapter;

    DecodeResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    //处理后端返回的data是空串的问题
    private final static String errorStr = "\"data\":\"\"";
    private final static String normalStr = "\"data\":null";

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        if (response.contains(errorStr)) {
            response = response.replace(errorStr, normalStr);
        }
        return adapter.fromJson(response);
    }
}
