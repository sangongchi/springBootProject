package org.sangongchi.projectbyspringboot.controller.springAi;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * 支持上下文记忆的聊天控制器
 * @author yangpei
 * @date 2026/4/16
 */
@RestController
@RequestMapping("/chat")
public class ChatController {

	private final ChatClient kimiChatClient;
	private final ChatClient qwenChatClient;

	// 内存中的对话历史存储
	private final InMemoryChatMemory chatMemory = new InMemoryChatMemory();

	public ChatController(ChatClient.Builder kimiChatClientBuilder, ChatClient.Builder qwenChatClientBuilder) {
		// 配置 Kimi ChatClient，添加记忆功能
		this.kimiChatClient = kimiChatClientBuilder
				.defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory))
				.build();

		// 配置 Qwen ChatClient，添加记忆功能
		this.qwenChatClient = qwenChatClientBuilder
				.defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory))
				.build();
	}

	/**
	 * Kimi 聊天接口（支持上下文）
	 * @param conversationId 会话ID（用于区分不同对话上下文）
	 * @param prompt 提示词
	 * @param stream 是否流式返回（默认 false）
	 */
	@GetMapping("/kimi")
	public ResponseEntity<Flux<String>> kimiChat(
			@RequestParam String conversationId,
			@RequestParam String prompt,
			@RequestParam(defaultValue = "false") boolean stream) {

		if (stream) {
			// 流式响应
			Flux<String> flux = kimiChatClient.prompt()
					.advisors(advisor -> advisor.param(MessageChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, conversationId))
					.user(prompt)
					.stream()
					.content();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.TEXT_EVENT_STREAM);
			return ResponseEntity.ok().headers(headers).body(flux);
		} else {
			// 非流式响应
			String response = kimiChatClient.prompt()
					.advisors(advisor -> advisor.param(MessageChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, conversationId))
					.user(prompt)
					.call()
					.content();

			return ResponseEntity.ok().body(Flux.just(response));
		}
	}

	/**
	 * Qwen 聊天接口（支持上下文）
	 * @param conversationId 会话ID（用于区分不同对话上下文）
	 * @param prompt 提示词
	 */
	@GetMapping("/qwen")
	public String qwenChat(
			@RequestParam String conversationId,
			@RequestParam String prompt) {
		return qwenChatClient.prompt()
				.advisors(advisor -> advisor.param(MessageChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, conversationId))
				.user(prompt)
				.call()
				.content();
	}

	/**
	 * Qwen 流式聊天接口（支持上下文）
	 * @param conversationId 会话ID（用于区分不同对话上下文）
	 * @param prompt 提示词
	 */
	@GetMapping(value = "/qwen/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> qwenStreamChat(
			@RequestParam String conversationId,
			@RequestParam String prompt) {
		return qwenChatClient.prompt()
				.advisors(advisor -> advisor.param(MessageChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, conversationId))
				.user(prompt)
				.stream()
				.content();
	}
}
