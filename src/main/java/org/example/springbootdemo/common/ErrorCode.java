package org.example.springbootdemo.common;

import lombok.Getter;

// 错误枚举
@Getter
public enum ErrorCode {
    PARAM_ERROR(400100, "参数错误"),
    AUTH_FAILED(401001, "认证失败"),
    FORBIDDEN(403001, "权限不足"),
    DATA_NOT_FOUND(404001, "数据不存在");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}