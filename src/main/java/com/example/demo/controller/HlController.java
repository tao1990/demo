package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.example.demo.domain.vo.MessageVo;
import com.example.demo.mapper.TestMapper;
import com.example.demo.utils.RedisUtil;

import io.swagger.annotations.ApiOperation;






/*
 * 1.send post 方法
 * 2.connect socket
 * 3.server push update socket
 * 4.get chat history get方法
 * 
 */




@RestController
@RequestMapping("/v1")
public class HlController {
	@Autowired
	TestMapper testMapper;
	@Autowired
	private RedisUtil redisUtil;

	
	
	private static  Logger loggerHello = LoggerFactory.getLogger("hl");
	
	@PostMapping("/send")
	@ApiOperation("/send")
	public void send(@RequestBody(required = true) MessageVo form) {
		
		String json = JSON.toJSONString(form);
		// 将验证码存入Redis
//	    redisUtil.set("ttt", "111");
	    redisUtil.lPush("hl", json);

	}
	
	
	
	
	
	
	

}
