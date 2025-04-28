package org.example.springbootdemo.utils;

import org.example.springbootdemo.common.BaseResponse;
import org.example.springbootdemo.common.ErrorCode;
import org.example.springbootdemo.common.PageResponse;
import org.springframework.data.domain.Page;

public class ResponseUtils {

  private static final int SUCCESS_CODE = 200;
  private static final String SUCCESS_MESSAGE = "success";

  /**
   * 成功响应
   */
  public static <T> BaseResponse<T> success(T data) {
    return new BaseResponse<T>()
        .setCode(SUCCESS_CODE)
        .setMsg(SUCCESS_MESSAGE)
        .setData(data);
  }

  /**
   * 成功响应（无数据）
   */
  public static <T> BaseResponse<T> success() {
    return new BaseResponse<T>()
        .setCode(SUCCESS_CODE)
        .setMsg(SUCCESS_MESSAGE);
  }

  /**
   * 分页成功响应
   */
  public static <T> PageResponse<T> success(Page<T> page) {
    PageResponse<T> response = new PageResponse<>();
    response.setTotal(page.getTotalElements())
        .setLimit(page.getSize())
        .setCurPage(page.getNumber() + 1)
        .setTotalPages(page.getTotalPages())
        .setCode(SUCCESS_CODE)
        .setMsg(SUCCESS_MESSAGE)
        .setData(page.getContent());
    return response;
  }

  /**
   * 错误响应
   */
  public static <T> BaseResponse<T> error(int code, String message) {
    return new BaseResponse<T>()
        .setCode(code)
        .setMsg(message);
  }

  /**
   * 错误响应（使用错误码枚举）
   */
  public static <T> BaseResponse<T> error(ErrorCode errorCode) {
    return error(errorCode.getCode(), errorCode.getMessage());
  }

  /**
   * 参数错误响应
   */
  public static <T> BaseResponse<T> paramError(String message) {
    return error(ErrorCode.PARAM_ERROR.getCode(), message);
  }
}