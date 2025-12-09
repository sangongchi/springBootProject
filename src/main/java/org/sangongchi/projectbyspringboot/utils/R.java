package org.sangongchi.projectbyspringboot.utils;

import lombok.Data;
import org.sangongchi.projectbyspringboot.common.TimeFormatter;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 统一响应结果封装类
 * 
 * @param <T> 响应数据类型
 * @author ProjectBySpringBoot
 */
@Schema(description = "统一响应结果")
@Data
public class R<T> {
	
	@Schema(description = "响应状态码", example = "200")
	private int code;
	
	@Schema(description = "响应消息", example = "success")
	private String msg;
	
	@Schema(description = "响应数据")
	private T data;
	
	@Schema(description = "响应时间戳", example = "2024-01-15 10:30:00")
	private String timestamp = TimeFormatter.format(System.currentTimeMillis());

	public static <T> R<T> ok(T data) {
		R<T> result = new R<T>();
		result.code = 200;
		result.msg = "success";
		result.data =data;
		result.timestamp = TimeFormatter.format(System.currentTimeMillis());
		return result;
	}
	public static <T> R<T> fail(int code, String msg) {
		R<T> result = new R<T>();
		result.code = code;
		result.msg = msg;
		return result;
	}
}
