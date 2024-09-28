package com.crater.craterlogin.bean.response;

public record Status(boolean isSuccess, String errorMessage, String errorDetail) {

    public static Status SUCCESS = new Status(true, null, null);
}
