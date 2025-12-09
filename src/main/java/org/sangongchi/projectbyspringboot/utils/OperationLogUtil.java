package org.sangongchi.projectbyspringboot.utils;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 操作日志工具类
 * 用于记录用户操作、API调用等操作日志
 * 
 * 使用说明：
 * 1. 在 logback-spring.xml 中配置了名为 "OPERATION" 的 Logger
 * 2. 操作日志会单独输出到 logs/operation.log 文件
 * 3. 可以通过 OperationLogUtil 记录各种操作日志
 * 
 * @author ProjectBySpringBoot
 */
@Component
public class OperationLogUtil {
    
    /**
     * 获取操作日志记录器
     * 注意：Logger 名称必须与 logback-spring.xml 中配置的 logger name 一致
     */
    private static final Logger operationLogger = LoggerFactory.getLogger("OPERATION");
    
    /**
     * 记录用户操作日志
     * 
     * @param userId 用户ID
     * @param operation 操作类型（如：登录、登出、新增、修改、删除等）
     * @param module 操作模块（如：用户管理、订单管理等）
     * @param description 操作描述
     * @param request 请求对象（可选，用于获取IP等信息）
     */
    public static void logUserOperation(String userId, String operation, String module, 
                                       String description, HttpServletRequest request) {
        StringBuilder logMsg = new StringBuilder();
        logMsg.append("[用户操作] ");
        logMsg.append("用户ID: ").append(userId).append(", ");
        logMsg.append("操作类型: ").append(operation).append(", ");
        logMsg.append("模块: ").append(module).append(", ");
        logMsg.append("描述: ").append(description);
        
        if (request != null) {
            logMsg.append(", IP: ").append(getClientIp(request));
            logMsg.append(", URL: ").append(request.getRequestURI());
            logMsg.append(", Method: ").append(request.getMethod());
        }
        
        operationLogger.info(logMsg.toString());
    }
    
    /**
     * 记录API调用日志
     * 
     * @param apiName API名称
     * @param method HTTP方法
     * @param url 请求URL
     * @param params 请求参数
     * @param responseTime 响应时间（毫秒）
     * @param status 响应状态（成功/失败）
     */
    public static void logApiCall(String apiName, String method, String url, 
                                 Map<String, Object> params, long responseTime, String status) {
        StringBuilder logMsg = new StringBuilder();
        logMsg.append("[API调用] ");
        logMsg.append("API名称: ").append(apiName).append(", ");
        logMsg.append("Method: ").append(method).append(", ");
        logMsg.append("URL: ").append(url).append(", ");
        
        if (params != null && !params.isEmpty()) {
            logMsg.append("参数: ").append(params).append(", ");
        }
        
        logMsg.append("响应时间: ").append(responseTime).append("ms, ");
        logMsg.append("状态: ").append(status);
        
        operationLogger.info(logMsg.toString());
    }
    
    /**
     * 记录业务操作日志
     * 
     * @param businessType 业务类型
     * @param businessId 业务ID
     * @param operation 操作类型
     * @param operator 操作人
     * @param details 操作详情
     */
    public static void logBusinessOperation(String businessType, String businessId, 
                                           String operation, String operator, String details) {
        StringBuilder logMsg = new StringBuilder();
        logMsg.append("[业务操作] ");
        logMsg.append("业务类型: ").append(businessType).append(", ");
        logMsg.append("业务ID: ").append(businessId).append(", ");
        logMsg.append("操作: ").append(operation).append(", ");
        logMsg.append("操作人: ").append(operator).append(", ");
        logMsg.append("详情: ").append(details);
        
        operationLogger.info(logMsg.toString());
    }
    
    /**
     * 记录简单操作日志
     * 
     * @param message 日志消息
     */
    public static void log(String message) {
        operationLogger.info("[操作日志] " + message);
    }
    
    /**
     * 记录带级别的操作日志
     * 
     * @param level 日志级别（INFO, WARN, ERROR）
     * @param message 日志消息
     */
    public static void log(String level, String message) {
        switch (level.toUpperCase()) {
            case "INFO":
                operationLogger.info("[操作日志] " + message);
                break;
            case "WARN":
                operationLogger.warn("[操作日志] " + message);
                break;
            case "ERROR":
                operationLogger.error("[操作日志] " + message);
                break;
            default:
                operationLogger.info("[操作日志] " + message);
        }
    }
    
    /**
     * 获取客户端IP地址
     */
    private static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}

