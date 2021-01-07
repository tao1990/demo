package com.example.demo.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CommentVo {
	
	@JsonProperty("video_id")
	public String videoId;

	@JsonProperty("comment")
	public String comment;

	@JsonProperty("nickname")
	public String nickname;
}
