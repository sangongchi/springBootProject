package org.sangongchi.projectbyspringboot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "测试", description = "测试接口")
@RestController
@RequestMapping("/redis")
public class RedisTest {
	private final RedisTemplate<Object, Object> redisTemplate;

	public RedisTest(RedisTemplate<Object, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	@Operation(summary = "测试redis", description = "测试redis")
	@GetMapping("/test")
	public String test() {
		redisTemplate.opsForSet().add("redis", "redis");
		return "redis test";
	}
}
