package com.company.service.impl;

import com.company.service.vo.LoginVo;
import commons.SpringTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.junit.Assert.*;

public class UserServiceImplTest extends SpringTestCase {
    @Autowired
    @Qualifier
    UserServiceImpl userService;

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
        LoginVo loginVo = new LoginVo();
    }

    @Test
    public void isExist() {
    }
}