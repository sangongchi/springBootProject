package org.sangongchi.projectbyspringboot.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.sangongchi.projectbyspringboot.model.MongoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name="mongo测试")
@RestController
@RequestMapping("/mongo")
public class MongoTest {
	@Autowired
	private MongoTemplate mongoTemplate;
	@GetMapping
	public List<MongoUser> getMongoUser() {
		return mongoTemplate.findAll(MongoUser.class);
	}
}
