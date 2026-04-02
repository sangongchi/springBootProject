package org.sangongchi.projectbyspringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // 允许Spring Boot Admin相关端点
                .requestMatchers("/instances", "/actuator/**", "/admin/**").permitAll()
//				// 其他actuator端点需要认证
//				.requestMatchers("/actuator/**").authenticated()
                // 其他所有请求无需认证
                .anyRequest().permitAll()
            )
            .csrf(csrf -> csrf
                // 禁用CSRF保护，便于Spring Boot Admin注册
                .disable()
            );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // 在内存中创建用户，实际生产环境应使用数据库或其他认证方式
        UserDetails actuatorUser = User.builder()
            .username("actuator")
            .password("{noop}actuator123") // {noop}表示不使用密码加密
            .roles("ACTUATOR")
            .build();

        return new InMemoryUserDetailsManager(actuatorUser);
    }
}
