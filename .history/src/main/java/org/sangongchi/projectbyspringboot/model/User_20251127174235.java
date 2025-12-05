package org.sangongchi.projectbyspringboot.model;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

// MyBatis 默认按 setter 注入属性，Jackson 在返回 JSON 时也依赖 getter。
// 没有这些方法时，查询结果会映射成字段全是 null 的对象（或序列化成空对象），看起来就像“没返回数据”。

@TableName("user")
@Data
public class User {
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;
	private String name;
	private String password;
	private Integer age;

	@TableField(value = "create_time", fill = FieldFill.INSERT)
  private LocalDateTime createTime;

  @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateTime;
}
