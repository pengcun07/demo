package com.pc.demo.mybatis.mapper;

import java.util.List;

import com.pc.demo.mybatis.pojo.Demo;

public interface DemoMapper {
  
    int deleteByPrimaryKey(Long id);

    int insert(Demo record);

    int insertSelective(Demo record);

    Demo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Demo record);

    int updateByPrimaryKey(Demo record);

    List<Demo> selectAll();
}