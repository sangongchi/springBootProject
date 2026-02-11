package org.sangongchi.projectbyspringboot.practice.fun;

import java.util.function.Function;

public class LambdaFun {
	public static void main(String[] args){
		MathOperation add = (a,b)-> a + b;
		System.out.println(add.operation(5,6));

		Function<String,Integer> lengthFun = s -> s.length();
		System.out.println("字符串长度" + lengthFun.apply("Hello"));
	}
}

interface MathOperation { int operation(int a, int b); }
