package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.domain.entity.Test;

@Mapper
public interface TestMapper {
	@Select("select * from test where id=#{id}")
	Test findById(Integer id);
	
	Test findTestById(@Param("id") Integer id);
}
