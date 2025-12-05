package org.sangongchi.projectbyspringboot.controller;

import java.util.List;

import org.sangongchi.projectbyspringboot.model.User;
import org.sangongchi.projectbyspringboot.service.UserService;
import org.sangongchi.projectbyspringboot.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public R<List<User>> getAllUsers() {
		return R.ok(userService.getAllUsers());
	}

	@GetMapping("/{id}")
	public R<User> getUserById(@PathVariable Long id) {
		User user = userService.getUserById(id);
		return user != null ? R.ok(user) : R.fail(404, "用户不存在");
	}
}
