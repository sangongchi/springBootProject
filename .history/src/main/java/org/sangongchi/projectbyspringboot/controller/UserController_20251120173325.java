package org.sangongchi.projectbyspringboot.controller;

import java.util.List;

import org.sangongchi.projectbyspringboot.model.User;
import org.sangongchi.projectbyspringboot.service.UserService;
import org.sangongchi.projectbyspringboot.utils.R;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@GetMapping("/age/{age}")
	public R<List<User>> getAgeUsers(@PathVariable String age) {
		return R.ok(userService.getAgeUsers(age));
	}

	@GetMapping("/{id}")
	public R<User> getUserById(@PathVariable Long id) {
		User user = userService.getUserById(id);
		return user != null ? R.ok(user) : R.fail(404, "用户不存在");
	}

	@PostMapping
	public R<String> addUser(@RequestBody User user) {
		System.out.println(user);
		if (userService.addUser(user) > 0) {
			return R.ok("添加成功");
		}
		return R.fail(500, "添加失败");
	}

	@PutMapping
	public R<String> updateUser(@RequestBody User user) {
		if (userService.updateUser(user)) {
			return R.ok("更新成功");
		}
		return R.fail(500, "更新失败");
	}

	@DeleteMapping("/{id}")
	public R<String> deleteUser(@PathVariable Long id) {
		if(userService.deleteUser(id)){
			return R.ok("删除成功");
		}
		return R.fail(500, "删除失败");
	}
}
