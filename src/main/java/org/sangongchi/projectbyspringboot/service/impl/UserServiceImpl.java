package org.sangongchi.projectbyspringboot.service.impl;

import org.sangongchi.projectbyspringboot.dao.UserMapper;
import org.sangongchi.projectbyspringboot.model.User;
import org.sangongchi.projectbyspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public List<User> getAllUsers() {
		return userMapper.selectList(null);
	}

	@Override
	public List<User> getAgeUsers(String age){
		return userMapper.selectAgeUsers(Integer.valueOf(age));
	}

	@Override
	public User getUserById(Long id){
		return userMapper.selectById(id);
	}
	// 新增接口
	@Override
	public Integer addUser(User user){
		return userMapper.insert(user);
	}

	//更新接口
	@Override
	public Boolean updateUser(User user){
		return userMapper.updateById(user)>0;
	}

	//删除接口
	@Override
	public Boolean deleteUser(Long id){
		return userMapper.deleteById(id) > 0;
	}
}
