package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.entity.Video;
import com.example.demo.domain.vo.CommentVo;
import com.example.demo.mapper.TestMapper;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1")
public class CommentController {
	@Autowired
	TestMapper testMapper;
	
	private static  Logger loggerHello = LoggerFactory.getLogger("hello");
	
	@GetMapping("/commit")
	@ApiOperation("/commit")
	public void commit() {
		CommentVo commentVo = new CommentVo();
		Video test= testMapper.findTestById(2);

	}
	
	

}
