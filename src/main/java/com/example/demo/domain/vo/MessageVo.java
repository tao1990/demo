package com.example.demo.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MessageVo {
	
	@JsonProperty("nickname")
	public String nickname;

	@JsonProperty("avatar_url")
	public String avatarUrl;
	
	@JsonProperty("message")
	public String message;

}
