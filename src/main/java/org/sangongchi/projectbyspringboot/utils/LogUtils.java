package org.sangongchi.projectbyspringboot.utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * @author yangpei
 * @date 2026/4/2
 */
public class LogUtils {
	// 全局 Marker
	public static final Marker OPERATION = MarkerFactory.getMarker("OPERATION");
	public static final Marker SQL = MarkerFactory.getMarker("SQL");
	private static final Logger logger = LoggerFactory.getLogger("GLOBAL");

	// 普通日志
	public static void info(String msg, Object... args) {
		logger.info(msg, args);
	}

	public static void warn(String msg, Object... args) {
		logger.warn(msg, args);
	}

	public static void error(String msg, Object... args) {
		logger.error(msg, args);
	}

	// 操作日志
	public static void operation(String msg, Object... args) {
		Logger operationLogger = LoggerFactory.getLogger("OPERATION");
		operationLogger.info(msg,args);
//		logger.info(OPERATION, msg, args);
	}

	// SQL 日志
	public static void sql(String msg, Object... args) {
		logger.debug(SQL, msg, args);
	}
}
