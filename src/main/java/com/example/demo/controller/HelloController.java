package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.dto.Hello;
import com.example.demo.domain.entity.Test;
import com.example.demo.mapper.TestMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("hello测试接口")
@RestController
@RequestMapping("/v1")
public class HelloController {
	@Autowired
	TestMapper testMapper;
	
	@GetMapping("/hello")
	@ApiOperation("/hello")
	public Hello hello() {
		Hello hello = new Hello();
		
		Test test= testMapper.findTestById(2);
//		Test test= testMapper.findById(2);
		hello.setHelloId(test.id);
		hello.setHelloName(test.getName());
		return hello;
	}
	

}
