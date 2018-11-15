package com.company.dao;

import com.company.dao.pojo.User;
import com.company.service.vo.LoginVo;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int isExist(String username);

    User login(LoginVo loginVo);

    int checkEmail(String str);

    String secectQuestionByUsername(String username);

    int checkAnswer(@Param("username") String username,@Param("question") String question, @Param("answer") String answer);

    int updatePasswordByUsername(@Param("username") String username, @Param("password") String md5Password);

    int checkPsassword(@Param("password") String md5EncodeUtf8, @Param("userId") Integer id);
}