package org.sangongchi.projectbyspringboot.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

// MyBatis 默认按 setter 注入属性，Jackson 在返回 JSON 时也依赖 getter。
// 没有这些方法时，查询结果会映射成字段全是 null 的对象（或序列化成空对象），看起来就像"没返回数据"。

/**
 * 用户实体类
 * 
 * @author ProjectBySpringBoot
 */
@Schema(description = "用户信息")
@TableName("user")
@Data
public class User {
	
	@Schema(description = "用户ID", example = "1")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	@Schema(description = "用户名", example = "张三", required = true)
	private String name;
	
	@Schema(description = "密码", example = "123456", required = true)
	private String password;
	
	@Schema(description = "年龄", example = "25")
	private Integer age;

	/*
		LocalDateTime 定义对应数据表类型字段 dateTime
		LocalDate 定义对应数据表类型字段 date
	*/
	@Schema(description = "创建时间", example = "2024-01-15 10:30:00")
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private Timestamp createTime;

	@Schema(description = "更新时间", example = "2024-01-15 10:30:00")
	@TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;
}
