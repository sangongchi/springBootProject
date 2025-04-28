package org.example.springbootdemo.common;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BaseResponse<T> {
  /**
   * 状态码
   */
  private int code;

  /**
   * 响应消息
   */
  private String msg;

  /**
   * 响应数据
   */
  private T data;

  /**
   * 时间戳
   */
  private long timestamp = System.currentTimeMillis();
}