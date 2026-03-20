package org.sangongchi.projectbyspringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

/**
 * @author yangpei
 * @date 2026/3/17
 */
@Configuration
public class WebClientConfig {
	@Bean
	public WebClient webClient() {
		return WebClient.builder()
				   .clientConnector(new ReactorClientHttpConnector(
					   HttpClient.create()
						   .compress(true) // 开启gzip压缩
						   .keepAlive(true)
						   .responseTimeout(Duration.ofSeconds(120)) // SSE 是长连接，默认 30 秒超时会断
					   ))
				   .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				   .build();
	}
}
