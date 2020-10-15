package com.simga.library.http.bean;

import com.google.gson.annotations.Expose;


/**
 * 返回结果的基类bean
 */
public class BaseResult<T> {

    @Expose
    public int code = -999999;

    @Expose
    public String message;

    @Expose
    public T data;

    public boolean ok() {
        return code == 0;
    }

}
