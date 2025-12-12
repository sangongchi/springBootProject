package org.sangongchi.projectbyspringboot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sangongchi.projectbyspringboot.utils.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Tag(name = "测试", description = "测试接口")
@Controller
@RequestMapping("/hello")
public class HelloController {
	@Operation(summary = "测试", description = "hello world")
	@GetMapping("/word")
	@ResponseBody
	public R<Object> hello(){
		System.out.println("hello-1--");
		return R.ok("success hello-world");
	}
}
