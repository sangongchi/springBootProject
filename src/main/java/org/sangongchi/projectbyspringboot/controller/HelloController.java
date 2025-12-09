package org.sangongchi.projectbyspringboot.controller;

import org.sangongchi.projectbyspringboot.utils.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/hello")
public class HelloController {
	@GetMapping("/word")
	@ResponseBody
	public R<Object> hello(){
		System.out.println("hello-1--");
		return R.ok("success hello-world");
	}
}
