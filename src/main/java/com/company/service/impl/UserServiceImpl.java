package com.company.service.impl;

import com.company.Commons.Common;
import com.company.Commons.Const;
import com.company.Commons.ServerResponse;
import com.company.dao.UserMapper;
import com.company.dao.pojo.User;
import com.company.service.iservice.IUserService;
import com.company.service.vo.LoginVo;
import com.company.util.MD5Util;
import com.company.util.TokenCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    @Qualifier("userMapper")
    UserMapper userMapper;

    @Override
    public String deleteByPrimaryKey(Integer id) {
        return (userMapper.deleteByPrimaryKey(id) == 1) ? Common.SUCCESS : Common.ERROR;
    }

    @Override
    public String insert(User record) {
        return (userMapper.insert(record) == 1) ? Common.SUCCESS : Common.ERROR;
    }

    @Override
    public String insertSelective(User record) {
        return (userMapper.insertSelective(record) == 1) ? Common.SUCCESS : Common.ERROR;
    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public String updateByPrimaryKeySelective(User record) {
        return (userMapper.updateByPrimaryKeySelective(record) == 1) ? Common.SUCCESS : Common.ERROR;
    }

    @Override
    public String updateByPrimaryKey(User record) {
        return (userMapper.updateByPrimaryKey(record) == 1) ? Common.SUCCESS : Common.ERROR;
    }

    @Override
    public ServerResponse<User> login(LoginVo loginVo) {
        int resultCount = userMapper.isExist(loginVo.getUsername());
        if (resultCount == 0) {
            return ServerResponse.createErrorMessageResponse(Common.ISEXISTSUCCESS);
        }
        loginVo.setPassword(MD5Util.MD5EncodeUtf8(loginVo.getPassword()));
        User user = userMapper.login(loginVo);
        if (user == null) {
            return ServerResponse.createErrorMessageResponse(Common.LOGINERROR);
        } else {
            user.setPassword(StringUtils.EMPTY);
            return ServerResponse.createSuccessResponse(Common.LOGINSUCCESS, user);
        }
    }

    @Override
    public ServerResponse<User> checkVaild(String str, String type) {
        if (StringUtils.isNotBlank(type)) {
            if (Const.VaildType.USERNAME.equals(type)) {
                int resultCount = userMapper.isExist(str);
                if (resultCount > 0) {
                    return ServerResponse.createErrorMessageResponse(Common.ISEXISTERROR);
                }
            }
            if (Const.VaildType.EMAIL.equals(type)) {
                int resultCount = userMapper.checkEmail(str);
                if (resultCount > 0) {
                    return ServerResponse.createErrorMessageResponse(Common.EMAILERROR);
                }
            }
        } else {
            return ServerResponse.createErrorMessageResponse(Common.PARAMERROR);
        }
        return ServerResponse.createSuccessMessageResponse(Common.SUCCESS);
    }

    @Override
    public ServerResponse<User> registry(User user) {
        ServerResponse vaildResponse = this.checkVaild(user.getUsername(), Const.VaildType.USERNAME);
        if (!vaildResponse.isSuccess()) {
            return ServerResponse.createErrorMessageResponse(Common.ISEXISTERROR);
        }
        vaildResponse = this.checkVaild(user.getEmail(), Const.VaildType.EMAIL);
        if (!vaildResponse.isSuccess()) {
            return ServerResponse.createErrorMessageResponse(Common.EMAILERROR);
        }
        user.setRole(Const.Role.ROLE_USER);
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userMapper.insertSelective(user);
        if (resultCount == 0) {
            return ServerResponse.createErrorMessageResponse(Common.REGISTRYERROR);
        } else {
            return ServerResponse.createSuccessMessageResponse(Common.REGISTRYSUCCESS);
        }
    }

    @Override
    public ServerResponse<User> getQuestionByUsername(String username) {
        ServerResponse validResponse = this.checkVaild(username, Const.VaildType.USERNAME);
        if (validResponse.isSuccess()) {
            return ServerResponse.createErrorMessageResponse(Common.ISEXISTSUCCESS);
        }
        String question = userMapper.secectQuestionByUsername(username);
        if (StringUtils.isNotBlank(question)) {
            return ServerResponse.createSuccessMessageResponse(question);
        }
        return ServerResponse.createErrorMessageResponse(Common.NOQUESTION);
    }

    @Override
    public ServerResponse<User> checkAnswer(String username, String question, String answer) {
        int resultCount = userMapper.checkAnswer(username, question, answer);
        if (resultCount > 0) {
            //如果提交了正确答案，通过UUID算法生成唯一的token值
            String forgetToken = UUID.randomUUID().toString();
            //通过GUAVA缓存储存token令牌
            TokenCache.setKey("token_" + username, forgetToken);
            //将Token令牌存入ServerResponse中并且返回
            return ServerResponse.createSuccessMessageResponse(forgetToken);
        }
        return ServerResponse.createErrorMessageResponse(Common.ANSWERFALSE);
    }

    @Override
    public ServerResponse<User> forgetResetPassword(String username, String password, String forgetToken) {
        //1-校验令牌是否存在
        if (StringUtils.isBlank(forgetToken)) {
            return ServerResponse.createErrorMessageResponse("参数错误需要提交token令牌");
        }
        //2-校验用户名是否存在，如果不存在直接返回，否则token_可以直接获得forgetToken不安全
        ServerResponse voildResponse = this.checkVaild(username, Const.VaildType.USERNAME);
        if (voildResponse.isSuccess()) {
            return ServerResponse.createErrorMessageResponse(Common.ISEXISTSUCCESS);
        }
        //3-从guava缓存中获取Token令牌进行非空校验  TokenCache.TOKEN_PREFIX+username 存入时命名的key
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
        if (StringUtils.isBlank(token)) {
            return ServerResponse.createErrorMessageResponse("token无效或者过期");
        }
        //4-对比token应用两种方式比较
        if (StringUtils.equals(token, forgetToken)) {
            String md5Password = MD5Util.MD5EncodeUtf8(password);
            int rowCount = userMapper.updatePasswordByUsername(username, md5Password);
            if (rowCount > 0) {
                return ServerResponse.createSuccessMessageResponse("修改密码成功");
            } else {
                return ServerResponse.createErrorMessageResponse("修改失败");
            }
        } else {
            return ServerResponse.createErrorMessageResponse("token无效重新获取");
        }
    }

    @Override
    public ServerResponse<User> resetPassword(String passwordOld, String passwordNew, User user) {
        int resultCount = userMapper.checkPsassword(MD5Util.MD5EncodeUtf8(passwordOld), user.getId());
        if (resultCount == 0) {
            return ServerResponse.createErrorMessageResponse("密码错误");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount > 0) {
            return ServerResponse.createSuccessMessageResponse("更新密码成功");
        } else {
            return ServerResponse.createErrorMessageResponse("更新密码失败");
        }
    }

    @Override
    public ServerResponse<User> updateInformation(User user) {
        //不能更改username 校验email
        User userBefore = userMapper.selectByPrimaryKey(user.getId());
        if (!(user.getEmail().equals(userBefore.getEmail()))) {
            if (userMapper.checkEmail(user.getEmail()) > 0) {
                return ServerResponse.createErrorMessageResponse("邮箱不能重复");
            }
        }
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount > 0) {
            return ServerResponse.createSuccessResponse("更新成功", user);
        }
        return ServerResponse.createErrorMessageResponse("更改失败");
    }

    @Override
    public ServerResponse<User> getInformation(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            return ServerResponse.createErrorMessageResponse("找不到用户");
        }
        //清空密码
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createSuccessResponse(user);
    }
}
