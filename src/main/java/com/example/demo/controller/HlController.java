package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.demo.domain.vo.MessageVo;
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
@CrossOrigin
public class HlController {
	@Autowired
	private RedisUtil redisUtil;

	
	
	private static  Logger loggerHello = LoggerFactory.getLogger("hl");
	
	@PostMapping("/send")
	@ApiOperation("/send")
	public Integer send(@RequestBody(required = true) MessageVo form) {
		Integer res = 500;
		try {
			String json = JSON.toJSONString(form);
		    redisUtil.lPush("hl", json);
		    redisUtil.incr("hl_usend");
		    res=200;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	    return res;
	}
	
	
	@GetMapping("/history")
	@ApiOperation("/history")
	public List<MessageVo> history() {
		List<MessageVo> resList = new ArrayList<MessageVo>();

			List<String> list= redisUtil.getList("hl",0,10);
			for(String s:list) {
				try {
					MessageVo vo = (MessageVo) JSONArray.parseObject(s, MessageVo.class);
					resList.add(vo);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
			
	    return resList;
	}
	
	
	@GetMapping("/updateTime")
	@ApiOperation("/updateTime")
	public String updateTime(@RequestParam(name="time") String time) {
	    return redisUtil.set("hl_now_time", time);
	}

}
