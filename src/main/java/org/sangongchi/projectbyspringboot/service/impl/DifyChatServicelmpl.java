package org.sangongchi.projectbyspringboot.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.sangongchi.projectbyspringboot.service.DifyChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangpei
 * @date 2026/3/18
 */
@Service
public class DifyChatServicelmpl implements DifyChatService {
	@Autowired
	private WebClient webClient;

	//	@PostMapping(value = "/dify-chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@Override
	public SseEmitter chat(String prompt, String conversationId, String isStreaming) {
		String difyUrl = "https://api.dify.ai/v1/chat-messages"; // 示例
		String apiKey = "app-2wNteQaCl6Hm2prKcIVa5S7d";

		SseEmitter emitter = new SseEmitter(0L); // 永不超时
		ObjectMapper mapper = new ObjectMapper();


		Map<String, Object> body = new HashMap<>();
		body.put("query", prompt);
		body.put("response_mode", isStreaming.equals("1") ? "streaming" : "blocking");
		body.put("inputs", new HashMap<>()); // 必须是可变 Map
		body.put("conversation_id", conversationId);
		body.put("user", "sangongchi");
		webClient.post()
				.uri(difyUrl)
				.header("Authorization", "Bearer " + apiKey)
				.header("Accept", "text/event-stream")
				.bodyValue(body)
				.retrieve() //发送请求并获取响应
				.onStatus(HttpStatusCode::isError, resp ->
						                                   resp.bodyToMono(String.class).flatMap(error -> {
							                                   System.out.println("Dify Error: " + error);
							                                   return Mono.error(new RuntimeException(error));
						                                   })
				)
				.bodyToFlux(String.class) //将响应体转换为 Flux<String>，表示一个异步的字符串流。
				.map(chunk -> {
					if (chunk.startsWith("data:")) {
						return chunk.substring(5).trim();
					}
					return chunk;
				})
				.subscribe(row -> {
							try {
								// 尝试解析 JSON
								JsonNode node = mapper.readTree(row);
								String event = node.get("event").asText();
								String answer = node.get("answer") == null ? "" : node.get("answer").asText();
								String conversionId = node.get("conversation_id").asText();
								String taskId = node.get("task_id").asText();

								ObjectNode out = mapper.createObjectNode();
								out.put("type", event)
										.put("answer", answer)
										.put("conversionId", conversionId)
										.put("taskId", taskId);
								emitter.send(
										SseEmitter.event()
												.data(out.toString())
												.id(String.valueOf(System.currentTimeMillis()))
								);

							} catch (Exception e) {
								System.out.println("Dify Error: " + e.getMessage());
								try {
									emitter.send(SseEmitter.event()
											             .data("{\"type\":\"error\",\"msg\":\"json_parse_error\"}"));
								} catch (IOException ex) {
									throw new RuntimeException(ex);
								}

							}
						}, error -> {
							try {
								emitter.send(SseEmitter.event()
										             .data("{\"type\":\"error\",\"msg\":\"" + error.getMessage() + "\"}"));
							} catch (IOException ignored) {
							}
							emitter.complete();
						},
						emitter::complete
				);
		return emitter;
	}


}
