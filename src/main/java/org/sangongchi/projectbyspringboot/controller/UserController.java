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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 用户管理 Controller
 * 
 * @author ProjectBySpringBoot
 */
@Tag(name = "用户管理", description = "用户相关的增删改查接口")
@RestController
@RequestMapping("/user")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@Operation(summary = "获取所有用户", description = "查询系统中所有用户列表")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "查询成功")
	})
	@GetMapping
	public R<List<User>> getAllUsers() {
		return R.ok(userService.getAllUsers());
	}

	@Operation(summary = "根据年龄查询用户", description = "根据指定年龄查询符合条件的用户列表")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "查询成功")
	})
	@GetMapping("/age/{age}")
	public R<List<User>> getAgeUsers(
			@Parameter(description = "用户年龄", required = true, example = "25")
			@PathVariable String age) {
		return R.ok(userService.getAgeUsers(age));
	}

	@Operation(summary = "根据ID查询用户", description = "根据用户ID查询用户详细信息")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "查询成功"),
		@ApiResponse(responseCode = "404", description = "用户不存在")
	})
	@GetMapping("/{id}")
	public R<User> getUserById(
			@Parameter(description = "用户ID", required = true, example = "1")
			@PathVariable Long id) {
		User user = userService.getUserById(id);
		return user != null ? R.ok(user) : R.fail(404, "用户不存在");
	}

	@Operation(summary = "新增用户", description = "创建新用户")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "添加成功"),
		@ApiResponse(responseCode = "500", description = "添加失败")
	})
	@PostMapping
	public R<String> addUser(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "用户信息", required = true)
			@RequestBody User user) {
		System.out.println(user);
		if (userService.addUser(user) > 0) {
			return R.ok("添加成功");
		}
		return R.fail(500, "添加失败");
	}

	@Operation(summary = "更新用户", description = "更新用户信息")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "更新成功"),
		@ApiResponse(responseCode = "500", description = "更新失败")
	})
	@PutMapping
	public R<String> updateUser(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "用户信息", required = true)
			@RequestBody User user) {
		if (userService.updateUser(user)) {
			return R.ok("更新成功");
		}
		return R.fail(500, "更新失败");
	}

	@Operation(summary = "删除用户", description = "根据用户ID删除用户")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "删除成功"),
		@ApiResponse(responseCode = "500", description = "删除失败")
	})
	@DeleteMapping("/{id}")
	public R<String> deleteUser(
			@Parameter(description = "用户ID", required = true, example = "1")
			@PathVariable Long id) {
		if(userService.deleteUser(id)){
			return R.ok("删除成功");
		}
		return R.fail(500, "删除失败");
	}
}
