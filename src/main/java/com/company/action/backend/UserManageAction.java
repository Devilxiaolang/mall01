package com.company.action.backend;

import com.company.Commons.Const;
import com.company.Commons.ServerResponse;
import com.company.dao.pojo.User;
import com.company.service.iservice.IUserService;
import com.company.service.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/")
public class UserManageAction {
    @Autowired
    @Qualifier("userService")
    IUserService userService;
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
        LoginVo loginVo = new LoginVo(username,password);
        ServerResponse responseResult = userService.login(loginVo);
        if(responseResult.isSuccess()){
            User user = (User) responseResult.getData();
            if(user.getRole()== Const.Role.Role_ADMIN){
                session.setAttribute(Const.CURRENT_USER,user);
            }else {
                return ServerResponse.createErrorResponse("没有权限无法登录");
            }
        }
        return responseResult;
    }

}
