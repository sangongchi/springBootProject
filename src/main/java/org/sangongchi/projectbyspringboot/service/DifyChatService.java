package org.sangongchi.projectbyspringboot.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author yangpei
 * @date 2026/3/18
 */
public interface DifyChatService {
	SseEmitter chat(String prompt, String conversationId, String isStreaming);
}
