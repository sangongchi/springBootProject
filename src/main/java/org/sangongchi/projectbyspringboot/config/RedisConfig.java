package org.sangongchi.projectbyspringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(factory);

		// key 使用String 序列化
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());

		// value 使用Jackson2JsonRedisSerializer 序列化
		 template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
		 template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));

		 template.afterPropertiesSet();
		 return template;

	}
}
