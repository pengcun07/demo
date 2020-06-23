package com.pc.demo.mybatis;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.pc.demo.mybatis.mapper.DemoMapper;
import com.pc.demo.mybatis.pojo.Demo;

public class MybatisMain {

  public static void main(String[] args) throws Exception {
    String resource = "mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    try (SqlSession session = sqlSessionFactory.openSession()) {
      
      DemoMapper mapper = session.getMapper(DemoMapper.class);
      Demo demo = mapper.selectByPrimaryKey(1L);
      System.out.println(demo);

      Demo demo2 = session.selectOne("selectByPrimaryKey", 1L);
      System.out.println(demo2);
    }
  }

}
