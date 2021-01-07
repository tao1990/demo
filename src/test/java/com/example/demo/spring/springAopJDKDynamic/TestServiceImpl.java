package com.example.demo.spring.springAopJDKDynamic;

public class TestServiceImpl implements TestI {

	@Override
	public int add(){
		System.out.println("开始执行add...");  
		return 0;
	}
}
