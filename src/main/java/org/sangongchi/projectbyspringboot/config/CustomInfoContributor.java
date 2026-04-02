package org.sangongchi.projectbyspringboot.config;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Object> appInfo = new HashMap<>();
        appInfo.put("name", "ProjectBySpringBoot");
        appInfo.put("version", "0.0.1-SNAPSHOT");
        appInfo.put("description", "Spring Boot Project");
        appInfo.put("java.version", System.getProperty("java.version"));
        appInfo.put("spring-boot.version", org.springframework.boot.SpringBootVersion.getVersion());

        builder.withDetail("app", appInfo);
    }
}
