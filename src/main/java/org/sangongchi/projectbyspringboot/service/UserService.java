package org.sangongchi.projectbyspringboot.service;

import org.sangongchi.projectbyspringboot.model.User;

import java.util.List;

public interface UserService {
	/**
	 * 获取所有用户
	 * @return 用户列表
	 */
	List<User> getAllUsers();

	/**
	 * 根据年龄查询用户
	 * @param age 年龄，不能为null
	 * @return 用户列表
	 */
	List<User> getAgeUsers(String age);

	/**
	 * 根据用户ID获取用户信息
	 * @param id 用户ID，不能为null
	 * @return 用户对象，如果不存在返回null
	 * @throws IllegalArgumentException 当id为null时抛出
	 */
	User getUserById(Long id);

	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	Integer addUser(User user);

	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	Boolean updateUser(User user);

	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	Boolean deleteUser(Long id);
}
