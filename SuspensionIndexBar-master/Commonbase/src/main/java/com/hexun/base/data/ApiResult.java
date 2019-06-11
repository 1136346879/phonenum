package com.hexun.base.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author zhangpeiyuan
 * @date 2017/10/18
 */

public class ApiResult<T> {

    @SerializedName(
            value = "data",
            alternate = {
                    "articles",
                    "user"
            })
    public T data;

    private int status;

    private String msg;

    private long serverTime;


    public boolean isSuccess() {
        return status == 0;
    }


    public String getErrorMessage() {
        return msg;
    }


    public int getstatus() {
        return status;
    }

}
