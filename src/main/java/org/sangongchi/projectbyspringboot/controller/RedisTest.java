package org.sangongchi.projectbyspringboot.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisTest {
	private final RedisTemplate<Object, Object> redisTemplate;

	public RedisTest(RedisTemplate<Object, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@GetMapping("/test")
	public String test() {
		redisTemplate.opsForSet().add("redis", "redis");
		return "redis test";
	}
}
