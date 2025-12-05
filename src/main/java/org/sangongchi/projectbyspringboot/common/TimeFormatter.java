package org.sangongchi.projectbyspringboot.common;


import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeFormatter {
	private static String DEFAULT_PATTERN  = "yyyy-MM-dd HH:mm:ss";

	// 带有默认值的重载方法
	public static String format(Object source){
		return format(source,DEFAULT_PATTERN);
	}
	public static String format(Object source,String format){
		LocalDateTime dateTime = convertToLocalDateTime(source);
		System.out.println("格式时间"+dateTime);
		DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern(format);
		return customFormatter.format(dateTime);
	}

	private static LocalDateTime convertToLocalDateTime(Object source){
		if(source instanceof Long){
			return Instant.ofEpochMilli((Long) source)
					       .atZone(ZoneId.systemDefault())
					       .toLocalDateTime();
		}else if(source instanceof Date){
			return ((Date) source).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		}else if(source instanceof String){
			return parseStringToDateTime((String) source);
		}else if(source instanceof LocalDateTime){
			return (LocalDateTime) source;
		}

		throw new IllegalArgumentException("不支持输入的类型："+source.getClass());
	}

	/**
	 * 解析字符串为日期时间（需指定格式）
	 */
	private static LocalDateTime parseStringToDateTime(String dateStr) {
		// 常见格式自动识别
		String[] patterns = {
				"yyyy-MM-dd HH:mm:ss",
				"yyyy/MM/dd HH:mm",
				"yyyyMMdd'T'HHmmss",
				"yyyy-MM-dd"
		};

		for (String pattern : patterns) {
			try {
				return LocalDateTime.parse(dateStr,
						DateTimeFormatter.ofPattern(pattern));
			} catch (DateTimeException ignored) {}
		}
		throw new DateTimeException("无法解析的日期格式: " + dateStr);
	}


	public static void main(String[] args) {
		System.out.println("currentTimeMillis: "+System.currentTimeMillis());
		System.out.println("getTime: "+ new Date().getTime());
		System.out.println("format转换结果: "+TimeFormatter.format(new Date()));
		System.out.println("format转换结果1："+ TimeFormatter.format(System.currentTimeMillis()));
	}
}
