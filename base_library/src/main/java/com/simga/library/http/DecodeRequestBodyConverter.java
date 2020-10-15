package com.simga.library.http;

import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

public class DecodeRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    DecodeRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public RequestBody convert(T value) {
        String toJson = GsonUtils.toJson(value);
        return RequestBody.create(MEDIA_TYPE, toJson);
    }
}
