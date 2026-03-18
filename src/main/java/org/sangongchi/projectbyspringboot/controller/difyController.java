package org.sangongchi.projectbyspringboot.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangpei
 * @date 2026/3/17
 */
@RestController

public class difyController {


	@Autowired
	private WebClient webClient;  // @Autowired 注解用于自动注入 Spring 容器中的 Bean

	@PostMapping(value = "/dify-chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> difyChat(
			@RequestParam String prompt,
			@RequestParam(required = false, defaultValue = "") String conversationId,
			@RequestParam(required = false, defaultValue = "1") String isStreaming) {
		String difyUrl = "https://api.dify.ai/v1/chat-messages"; // 示例
		String apiKey = "app-2wNteQaCl6Hm2prKcIVa5S7d";

		Map<String, Object> body = new HashMap<>();
		body.put("query", prompt);
		body.put("response_mode", isStreaming.equals("1") ? "streaming" : "blocking");
		body.put("inputs", new HashMap<>()); // 必须是可变 Map
		body.put("conversation_id", conversationId);
		body.put("user", "sangongchi");
		return webClient.post()
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
				       .map(row -> {
					       try {
						       // 尝试解析 JSON
						       JsonNode node = new ObjectMapper().readTree(row);
						       String event = node.get("event").asText();
						       String answer = node.get("answer") == null ? "" : node.get("answer").asText();
						       String conversionId = node.get("conversation_id").asText();
						       String taskId = node.get("task_id").asText();

						       ObjectNode out = new ObjectMapper().createObjectNode();
						       out.put("type", event)
								       .put("answer", answer)
								       .put("conversionId", conversionId)
								       .put("taskId", taskId);
						       return out.toString();

					       } catch (Exception e) {
						       System.out.println("Dify Error: " + e.getMessage());
						       return "{\"type\":\"error\",\"answer\":\"\"}";
					       }
				       });

	}

	@PostMapping(value = "/dify-workflow", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> difyWorkFlow(
			@RequestBody ReBody reBody,
			@RequestParam(required = false, defaultValue = "1") String isStreaming) {
		System.out.println(reBody);
		String difyUrl = "https://api.dify.ai/v1/workflows/run";
		String apiKey = "app-IQ9C47wyke2DP4MPomDVvC9d";


		Map<String, Object> body = new HashMap<>();
		body.put("response_mode", isStreaming.equals("1") ? "streaming" : "blocking");
		body.put("inputs", reBody);
		body.put("user", "sangongchi");

		ObjectMapper mapper = new ObjectMapper();
		return webClient.post()
				       .uri(difyUrl)
				       .header("Authorization", "Bearer " + apiKey)
				       .header("Accept", "text/event-stream")
				       .bodyValue(body)
				       .retrieve()
				       .onStatus(HttpStatusCode::isError, resp ->
						                                          resp.bodyToMono(String.class).flatMap(error -> {
							                                          System.out.println("Dify Error: " + error);
							                                          return Mono.error(new RuntimeException(error));
						                                          })
				       )
				       .bodyToFlux(String.class)
				       // 替换data:开头
				       .map(raw -> raw.startsWith("data:") ? raw.substring(5).trim() : raw)
				       .map(row -> {
					       try {
						       JsonNode node = mapper.readTree(row);
						       ObjectNode out = new ObjectMapper().createObjectNode();
						       if (node.path("event").isMissingNode()) {
							       out.put("event", "");
							       out.put("content", row);
							       out.put("workFlowRunId", "");
							       out.put("taskId", "");
						       }
						       String event = node.path("event").asText();
						       String workFlowRunId = node.path("workflow_run_id").asText();
						       String taskId = node.path("task_id").asText();
						       JsonNode content = node.path("data");
						       out.put("event", event)
								       .put("workFlowRunId", workFlowRunId)
								       .put("taskId", taskId);
						       out.set("content", content);
						       return out.toString();
					       } catch (Exception e) {
						       System.out.println("Dify Error: " + e.getMessage());
						       return "{\"type\":\"error\",\"content\":\"\"}";
					       }
				       });


	}

	@Data
	public static class ReBody {
		private String text;
		private Img img;

		@Data
		public static class Img {
			private String type;
			private String transfer_method;
			private String url;
			private String upload_file_id;
		}
	}


}
