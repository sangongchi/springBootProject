package org.sangongchi.projectbyspringboot.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.api.OpenAiApi;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Spring AI 多服务商配置
 * @author yangpei
 * @date 2026/4/16
 */
@Configuration
public class AiChatConfig {

	// ================ Kimi 配置 ================
	@Value("${ai.kimi.api-key}")
	private String kimiApiKey;

	@Value("${ai.kimi.base-url}")
	private String kimiBaseUrl;

	@Value("${ai.kimi.model:moonshot-v1-8k}")
	private String kimiChatModel;


	// ================ 阿里云通义千问配置 ================
	@Value("${ai.qwen.api-key}")
	private String qwenApiKey;

	@Value("${ai.qwen.base-url}")
	private String qwenBaseUrl;

	@Value("${ai.qwen.model:qwen-plus}")
	private String qwenChatModel;

	@Value("${ai.qwen.embedding-model:text-embedding-v3}")
	private String qwenEmbeddingModel;

	// ================ DeepSeek 配置（Spring AI 自动配置） ================
	// DeepSeek 的配置由 Spring AI 根据 spring.ai.openai.* 自动配置
	// 这里不需要手动定义，Spring AI 会自动创建 openAiChatModel bean

	// 注解用于在依赖注入完成后执行初始化方法
	@PostConstruct
	public void init() {
		System.out.println("=== Kimi API Key: " + kimiApiKey);
		System.out.println("=== Kimi Base URL: " + kimiBaseUrl);
		System.out.println("=== Qwen API Key: " + qwenApiKey);
		System.out.println("=== Qwen Base URL: " + qwenBaseUrl);
	}

	@Bean
	public OpenAiApi kimiOpenAiApi() {
		return OpenAiApi.builder()
				       .baseUrl(kimiBaseUrl)
				       .apiKey(kimiApiKey)
				       .build();
	}

	@Bean("kimiChatModel")
	public ChatModel kimiChatModel() {
		return OpenAiChatModel.builder()
				       .openAiApi(kimiOpenAiApi())
				       .defaultOptions(OpenAiChatOptions.builder()
						                       .model(kimiChatModel)
						                       .build())
				       .build();
	}

	/**
	 * Kimi ChatClient.Builder
	 */
	@Bean
	public ChatClient.Builder kimiChatClientBuilder(@Qualifier("kimiChatModel") ChatModel kimiChatModel) {
		return ChatClient.builder(kimiChatModel);
	}

	@Bean
	public OpenAiApi qwenOpenAiApi() {
		return OpenAiApi.builder()
				       .baseUrl(qwenBaseUrl)
				       .apiKey(qwenApiKey)
				       .build();
	}

	@Bean("qwenChatModel")
	public ChatModel qwenChatModel() {
		return OpenAiChatModel.builder()
				       .openAiApi(qwenOpenAiApi())
				       .defaultOptions(OpenAiChatOptions.builder()
						                       .model(qwenChatModel)
						                       .build())
				       .build();
	}

	/**
	 * Qwen ChatClient.Builder
	 */
	@Bean
	public ChatClient.Builder qwenChatClientBuilder(@Qualifier("qwenChatModel") ChatModel qwenChatModel) {
		return ChatClient.builder(qwenChatModel);
	}

	@Bean
	public OpenAiApi qwenEmbeddingApi() {
		return OpenAiApi.builder()
				       .baseUrl(qwenBaseUrl)
				       .apiKey(qwenApiKey)
				       .build();
	}

	@Bean("qwenEmbeddingModel")
	public OpenAiEmbeddingModel qwenEmbeddingModel() {
		return new OpenAiEmbeddingModel(qwenOpenAiApi());
	}

	// ================ DeepSeek 配置（使用 @Primary 让它成为默认的 ChatModel） ================
	@Value("${spring.ai.openai.api-key}")
	private String deepSeekApiKey;

	@Value("${spring.ai.openai.base-url}")
	private String deepSeekBaseUrl;

	@Value("${spring.ai.openai.chat.options.model:deepseek-chat}")
	private String deepSeekChatModel;

	@Bean
	public OpenAiApi deepSeekOpenAiApi() {
		return OpenAiApi.builder()
				       .baseUrl(deepSeekBaseUrl)
				       .apiKey(deepSeekApiKey)
				       .build();
	}

	/**
	 * DeepSeek ChatModel - 使用 @Primary 使其成为默认的 ChatModel
	 * 当去掉 @Qualifier 时，Spring 会自动选择这个 bean
	 */
	@Primary
	@Bean("openAiChatModel")
	public ChatModel openAiChatModel() {
		return OpenAiChatModel.builder()
				       .openAiApi(deepSeekOpenAiApi())
				       .defaultOptions(OpenAiChatOptions.builder()
						                       .model(deepSeekChatModel)
						                       .build())
				       .build();
	}

	@Bean("deepSeekEmbeddingModel")
	public OpenAiEmbeddingModel deepSeekEmbeddingModel() {
		return new OpenAiEmbeddingModel(deepSeekOpenAiApi());
	}
}

