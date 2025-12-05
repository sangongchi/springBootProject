package org.sangongchi.projectbyspringboot.utils;

import lombok.Data;
import org.sangongchi.projectbyspringboot.common.TimeFormatter;

// 基础响应
@Data
public class R<T> {
	private int code;
	private String msg;
	private T data;
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
