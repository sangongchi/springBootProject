package org.sangongchi.projectbyspringboot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.sangongchi.projectbyspringboot.model.User;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
	List<User> selectAgeUsers(@Param("age") Integer age);
}
