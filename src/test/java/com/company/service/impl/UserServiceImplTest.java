package com.company.service.impl;

import com.company.Commons.Common;
import com.company.Commons.ServerResponse;
import com.company.dao.pojo.User;
import com.company.service.iservice.IUserService;
import com.company.service.vo.LoginVo;
import com.company.util.TokenCache;
import commons.SpringTestCase;
import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.junit.Assert.*;

public class UserServiceImplTest extends SpringTestCase {
    @Autowired
    @Qualifier("userService")
    IUserService userService;

    @Test
    public void deleteByPrimaryKey() {
    }

    @Test
    public void insert() {
    }

    @Test
    public void insertSelective() {
    }

    @Test
    public void selectByPrimaryKey() {
    }

    @Test
    public void updateByPrimaryKeySelective() {
    }

    @Test
    public void updateByPrimaryKey() {
    }

    @Test
    public void login() {
        LoginVo loginVo = new LoginVo("test","test");
        ServerResponse responseResult = userService.login(loginVo);
        System.out.println(responseResult.getMsg());
    }

    @Test
    public void isExist() {
    }

    @Test
    public void checkVaild() {
        ServerResponse responseResult=userService.checkVaild("admin","username");
        TestCase.assertTrue(responseResult.getStatus()==1);
    }

    @Test
    public void registry() {
        User user = new User();
        user.setUsername("test2");
        user.setPassword("test");
        user.setEmail("admin@emall.com");
        ServerResponse responseResult = userService.registry(user);
        TestCase.assertTrue(!responseResult.isSuccess());
    }

    @Test
    public void getQuestionByUsername() {
        ServerResponse responseResult = userService.getQuestionByUsername("admin");
        TestCase.assertTrue(responseResult.isSuccess());
        System.out.println(responseResult.getMsg());
    }

    @Test
    public void checkAnswer() {
        ServerResponse responseResult = userService.checkAnswer("admin","问题","答案");
        TestCase.assertTrue(responseResult.isSuccess());
        System.out.println(responseResult.getMsg());
    }

    @Test
    public void forgetResetPassword() {
        ServerResponse responseResult=userService.checkAnswer("admin","问题","答案");
        TestCase.assertTrue(responseResult.isSuccess());
        System.out.println(responseResult.getMsg());
        ServerResponse serverResponse=userService.forgetResetPassword("admin","admin",responseResult.getMsg());
        TestCase.assertTrue(serverResponse.isSuccess());
        System.out.println(serverResponse.getMsg());
    }

    @Test
    public void resetPassword() {
    }

    @Test
    public void updateInformation() {
    }

    @Test
    public void getInformation() {
    }
}