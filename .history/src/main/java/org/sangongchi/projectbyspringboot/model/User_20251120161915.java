package org.sangongchi.projectbyspringboot.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user")
@Data
public class User {
	private Integer id;
	private String name;
	private String password;
	private Integer age;
}
