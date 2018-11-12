package com.company.service.impl;

import com.company.dao.UserDao;
import com.company.dao.pojo.User;
import com.company.service.iservice.UserService;
import commons.SpringTestCase;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserServiceImplTest extends SpringTestCase {
    @Autowired
            @Qualifier("userService")
    private UserService userService;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Test
    public void findById() {
        System.out.println(userService);
        User user = userService.findById(1);
        System.out.println("$$$$$$$$$$$$$$$$$"+user);
    }

    @Test
    public void save() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void findByPage() {

    }

    @Test
    public void findAll() {
        List<User>users=new ArrayList<User>();
        users=userService.findAll();
        for(User user:users){
            logger.info("*******"+user);
        }
    }

    @Test
    public void findByName() {
    }

    @Test
    public void sessionCache() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);//Mybatis将UserDao接口与user-mapper.xml结合成一个代理对象，赋值给userDao
        User user1 = userDao.findById(1,(int)(Math.random()*1000));
        logger.debug("***************"+user1);
        User user2 = userDao.findById(1,(int)(Math.random()*1000));
        logger.debug("$$$$$$$$$$$$$$$"+user2);
        sqlSession.close();
    }
}
