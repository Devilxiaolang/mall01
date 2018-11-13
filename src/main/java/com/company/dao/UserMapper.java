package com.company.dao;

import com.company.dao.pojo.User;
import com.company.service.vo.LoginVo;

public interface UserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int login(LoginVo loginVo);

    int isExist(String username);
}