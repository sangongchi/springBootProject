package org.sangongchi.projectbyspringboot.practice.optional;

import java.util.Optional;

public class optionalDemo {
	public static void main(String[] args){
		Optional<String> name = Optional.ofNullable(null);
		String result = name.orElse("默认值");
		System.out.println(result);
		System.out.println(Optional.empty().orElse("haha"));


	}
}
