package org.sangongchi.projectbyspringboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.sangongchi.projectbyspringboot.dao")
public class ProjectBySpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectBySpringBootApplication.class, args);
	}

}
