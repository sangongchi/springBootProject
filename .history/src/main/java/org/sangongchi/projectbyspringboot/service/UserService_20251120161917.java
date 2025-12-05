package org.sangongchi.projectbyspringboot.service;

import org.sangongchi.projectbyspringboot.dao.UserMapper;
import org.sangongchi.projectbyspringboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;

	public List<User> getAllUsers() {
		return userMapper.selectList();
	}

	public User getUserById(Long id){
		return userMapper.selectById(id);
	}
}
