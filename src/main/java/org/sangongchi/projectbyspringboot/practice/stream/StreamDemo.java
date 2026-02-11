package org.sangongchi.projectbyspringboot.practice.stream;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class StreamDemo {
	public static void main(String[] args){
		List<String> names = Arrays.asList("Alice","bob","san");
		names.stream().filter(n-> n.length()>3).map(String::toUpperCase).forEach(System.out::println);

		// 方法引用
		Consumer<String> printer = System.out::println;
		printer.accept("test test");

	}
	public String getVal(String val) {
		System.out.println(val.length());
		return;
	}
}
