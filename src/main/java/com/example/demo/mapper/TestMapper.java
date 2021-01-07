package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.domain.entity.Video;

@Mapper
public interface TestMapper {
	@Select("select * from test where id=#{id}")
	Video findById(Integer id);
	
	Video findTestById(@Param("id") Integer id);
	
	List<Video> findAllTest();

}
