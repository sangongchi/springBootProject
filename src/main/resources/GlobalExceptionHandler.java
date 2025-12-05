package org.sangongchi.projectbyspringboot.common;

import org.sangongchi.projectbyspringboot.utils.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(value = Exception.class)
	public R<String> handleException(Exception e) {
		return R.fail(500,e.getMessage());
	}
}
