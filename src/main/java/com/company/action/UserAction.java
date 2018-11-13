package com.company.action;

import com.company.Commons.Common;
import com.company.Commons.Const;
import com.company.Commons.ResponseCode;
import com.company.Commons.ServerResponse;
import com.company.dao.pojo.User;
import com.company.service.iservice.IUserService;
import com.company.service.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/user/")
public class UserAction {
    @Autowired
    IUserService userService;

    /**
     *
     * @param username
     * @param password
     * @param session
     * @return 登录功能
     */
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
        LoginVo loginVo = new LoginVo(username,password);
        ServerResponse<User> responseResult = userService.login(loginVo);
        if(responseResult.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,responseResult.getData());
        }
        return responseResult;
    }

    /**
     *
     * @param session
     * @return 登出功能
     */
    @RequestMapping(value = "logout.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createSuccessResponse();
    }

    /**
     *注册时查看用户邮箱是否可用
     * @param str
     * @param type
     * @return
     */
    @RequestMapping(value = "check_vaild.do",method = RequestMethod.POST)
    @ResponseBody
    public  ServerResponse<User> checkVaild(String str,String type){
        return userService.checkVaild(str,type);
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping(value = "registry.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> registry(User user){
        return userService.registry(user);
    }

    /**
     * 获取用户信息
     * @param session
     * @return
     */
    @RequestMapping(value = "get_user_info.do",method =RequestMethod.GET )
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
        //因为在登录的时候就已经存过session 所以可以直接使用
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user!=null){
            return ServerResponse.createSuccessResponse(Common.FINDBYIDSUCCESS,user);
        }else{
            return ServerResponse.createErrorMessageResponse(Common.FINDBYIDERROR);
        }
    }

    /**
     * 根据用户名获取预设问题
     * @param username
     * @return
     */
    @RequestMapping(value = "forget_get_question.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> forgetPasswordGetQuestion(String username){
        return userService.getQuestionByUsername(username);
    }

    /**
     * 根据用户名问题答案后台判断是否可以修改密码并且存入token令牌
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @RequestMapping(value = "forget_check_answer.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> forgetPasswordCheckAnswer(String username,String question,String answer){
        return  userService.checkAnswer(username,question,answer);
    }

    /**
     * 忘记密码更改密码 token令牌应用
     * @param username
     * @param password
     * @param forgetToken
     * @return
     */
    @RequestMapping(value = "forget_reset_password.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> forgetResetPassword(String username,String password,String forgetToken){
        return userService.forgetResetPassword(username,password,forgetToken);
    }

    /**
     * 登录状态下修改密码
     * @param session
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    @RequestMapping(value = "reset_password.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> resetPassword(HttpSession session,String passwordOld,String passwordNew){
        //校验是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createErrorMessageResponse("没有用户登录");
        }
        return userService.resetPassword(passwordOld,passwordNew,user);
    }

    /**
     * 登录状态下进行资料修改
     * @param user
     * @param session
     * @return
     */
    @RequestMapping(value = "update_information.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> updateUserInfomation(User user,HttpSession session){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser!=null){
            //更新时不允许修改uid和username 所以从session中读取
            user.setUsername(currentUser.getUsername());
            user.setId(currentUser.getId());
            ServerResponse responseResult= userService.updateInformation(user);
            //修改成功 更新session
            if(responseResult.isSuccess()){
                session.setAttribute(Const.CURRENT_USER,responseResult.getData());
            }
            return responseResult;
        }
        return ServerResponse.createErrorMessageResponse("没有用户登录");
    }


    @RequestMapping(value = "get_information.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> getInfomation(HttpSession session){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser!=null){

            return ServerResponse.createErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),"需要登录");
        }
        return userService.getInformation(currentUser.getId());
    }
}
