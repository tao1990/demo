package com.example.demo.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Hello {
	
	@ApiModelProperty("hello_id")
	@JsonProperty("hello_id")
	public Integer helloId;
	
	@ApiModelProperty("hello_name")
	@JsonProperty("hello_name")
	public String helloName;
	
	

}
