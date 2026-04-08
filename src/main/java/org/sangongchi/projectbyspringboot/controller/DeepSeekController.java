package org.sangongchi.projectbyspringboot.controller;


import org.sangongchi.projectbyspringboot.utils.LogUtils;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author yangpei
 * @date 2026/4/8
 */
@RestController
@RequestMapping("/deepseek")
public class DeepSeekController {
	private final OpenAiChatModel chatModel;
	private final Environment environment;
	private final OpenAiEmbeddingModel embeddingModel;

	@Value("${spring.ai.openai.base-url}")
	private String baseUrl;

	public DeepSeekController(OpenAiChatModel chatModel, Environment environment, OpenAiEmbeddingModel embeddingModel) {
		this.chatModel = chatModel;
		this.environment = environment;
		this.embeddingModel = embeddingModel;
	}

	@GetMapping("/chat")
	public String chat(@RequestParam String prompt) {
		try {
			String deepSeekApiKey  = System.getenv("DEEPSEEK_API_KEY");
			String deepSeekApiKey1 = environment.getProperty("DEEPSEEK_API_KEY");
			String baseUrlFromEnv = environment.getProperty("spring.ai.openai.base-url");
			LogUtils.info("Base URL from env: {}", baseUrlFromEnv);
			LogUtils.info("Base URL from field: {}", baseUrl);
			LogUtils.info("API Key from system env: {}", deepSeekApiKey != null ? "****" : "null");
			LogUtils.info("API Key from config: {}", deepSeekApiKey1 != null ? "****" : "null");
			LogUtils.info("Prompt: {}", prompt);

			String result = chatModel.call(prompt);
			LogUtils.info("Chat result: {}", result);
			return result;
		} catch (Exception e) {
			LogUtils.error("Error in chat: {}", e.getMessage());
			e.printStackTrace();
			return "Error: " + e.getMessage();
		}
	}

	@GetMapping(value = "/stream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> stream(@RequestParam String prompt) {
		return chatModel.stream(prompt);
	}

	@GetMapping("/embed")
	public List<Double> embed(@RequestParam String text) {
		try {
			LogUtils.info("Embedding text: {}", text);
			// 调用嵌入模型获取响应
			float[] embedding = embeddingModel.embed(text);
			// 转换float[]为List<Double>
			List<Double> embeddings = new java.util.ArrayList<>();
			for (float value : embedding) {
				embeddings.add((double) value);
			}
			LogUtils.info("Embedding result size: {}", embeddings.size());
			return embeddings;
		} catch (Exception e) {
			LogUtils.error("Error in embedding: {}", e.getMessage());
			e.printStackTrace();
			return List.of();
		}
	}
}
