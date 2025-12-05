package org.sangongchi.projectbyspringboot.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("org.sangongchi.projectbyspringboot.dao")
public class MyBatisPlusConfig {}
