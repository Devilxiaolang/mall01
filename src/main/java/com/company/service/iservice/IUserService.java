package com.company.service.iservice;

import com.company.dao.pojo.User;
import com.company.service.vo.LoginVo;

public interface IUserService {
    /**
     *
     * @param id
     * @return success or error
     * 根据主键删除用户
     */
    String deleteByPrimaryKey(Integer id);

    /**
     *
     * @param record
     * @return success or error
     * 新增用户
     */
    String insert(User record);

    /**
     *
     * @param record
     * @return success or error
     * 动态sql 根据字段进行增加
     */
    String insertSelective(User record);

    /**
     *
     * @param id
     * @return User
     * findByID
     */
    User selectByPrimaryKey(Integer id);

    /**
     *
     * @param record
     * @return success or error
     * 动态sql 根据字段修改
     */
    String updateByPrimaryKeySelective(User record);

    /**
     *
     * @param record
     * @return success or error
     * 根据user全部修改
     */
    String updateByPrimaryKey(User record);

    /**
     *
     * @param loginVo
     * @return true or false
     * 用户登录
     */
    boolean login(LoginVo loginVo);

    /**
     *
     * @param username
     * @return true or false
     * 判断用户是否存在
     */
    boolean isExist(String username);
}
