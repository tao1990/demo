package com.example.demo.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.InitializingBean;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Table(name = "h_comment")
@Entity
public class Comment implements InitializingBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("id")
    @Column(name = "id")
    public Integer id;
	
	@JsonProperty("content")
    @Column(name = "content")
    public String content;
	
	@JsonProperty("status")
    @Column(name = "status")
    public Long status;
	
	@JsonProperty("nickname")
    @Column(name = "nickname")
    public String nickname;

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
//	@Column(name = "create_time", updatable = false)
//	@JsonProperty("create_time")
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//	@CreationTimestamp
//	public Timestamp createTime;
//
//	@Override
//	public void afterPropertiesSet() throws Exception {
//		// TODO Auto-generated method stub
//		
//	}
	
}
