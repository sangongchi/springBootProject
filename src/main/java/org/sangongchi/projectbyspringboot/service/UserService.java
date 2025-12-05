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
		return userMapper.selectList(null);
	}

	public List<User> getAgeUsers(String age){
		return userMapper.selectAgeUsers(Integer.valueOf(age));
	}

	public User getUserById(Long id){
		return userMapper.selectById(id);
	}
	// 新增接口
	public Integer addUser(User user){
		return userMapper.insert(user);
	}

	//更新接口
	public Boolean updateUser(User user){
		return userMapper.updateById(user)>0;
	}

	//删除接口
	public Boolean deleteUser(Long id){
		return userMapper.deleteById(id) > 0;
	}
}
