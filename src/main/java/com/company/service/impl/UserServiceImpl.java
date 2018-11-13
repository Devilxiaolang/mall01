package com.company.service.impl;

import com.company.Commons.Common;
import com.company.dao.UserMapper;
import com.company.dao.pojo.User;
import com.company.service.iservice.IUserService;
import com.company.service.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    @Qualifier("userMapper")
    UserMapper userMapper;

    @Override
    public String deleteByPrimaryKey(Integer id) {
        return (userMapper.deleteByPrimaryKey(id)==1)?Common.SUCCESS:Common.ERROR;
    }

    @Override
    public String insert(User record) {
        return (userMapper.insert(record)==1)?Common.SUCCESS:Common.ERROR;
    }

    @Override
    public String insertSelective(User record) {
        return (userMapper.insertSelective(record)==1)?Common.SUCCESS:Common.ERROR;
    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public String updateByPrimaryKeySelective(User record) {
        return (userMapper.updateByPrimaryKeySelective(record)==1)?Common.SUCCESS:Common.ERROR;
    }

    @Override
    public String updateByPrimaryKey(User record) {
        return (userMapper.updateByPrimaryKey(record)==1)?Common.SUCCESS:Common.ERROR;
    }

    @Override
    public boolean login(LoginVo loginVo) {
        return (userMapper.login(loginVo)==1)?true:false;
    }

    @Override
    public boolean isExist(String username) {
        return (userMapper.isExist(username)==1)?true:false;
    }
}
