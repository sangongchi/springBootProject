package org.sangongchi.projectbyspringboot.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
	@Override
	public void insertFill(MetaObject metaObject) {
		/*
			Date.class 同 LocalDateTime.class 类型不一致，所以要注意User.java中的定义
			this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
		*/
		log.info("开始插入填充...");
		this.strictInsertFill(metaObject, "createTime", Timestamp.class, new Timestamp(System.currentTimeMillis()));
//		this.strictInsertFill(metaObject, "createTime", Long.class, System.currentTimeMillis());
		this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		log.info("开始更新填充...");
		this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
	}
}
