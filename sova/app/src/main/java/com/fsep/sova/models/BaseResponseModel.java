package com.fsep.sova.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponseModel<T> {

    private ResponseInfo responseInfo;

    private T data;

    public ResponseInfo getResponseInfo() {
        return responseInfo;
    }

    public T getData() {
        return data;
    }
}
