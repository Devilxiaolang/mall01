package com.company.service.iservice;

import com.company.Commons.ServerResponse;
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
    ServerResponse<User> login(LoginVo loginVo);

    ServerResponse<User> checkVaild(String str, String type);

    ServerResponse<User> registry(User user);

    ServerResponse<User> getQuestionByUsername(String username);

    ServerResponse<User> checkAnswer(String username, String question, String answer);

    ServerResponse<User> forgetResetPassword(String username, String password, String forgetToken);

    ServerResponse<User> resetPassword(String passwordOld, String passwordNew,User user);

    ServerResponse<User> updateInformation(User user);

    ServerResponse<User> getInformation(Integer id);

    ServerResponse checkAdminRole(User user);
}
