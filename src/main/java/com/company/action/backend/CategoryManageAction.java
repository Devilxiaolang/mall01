package com.company.action.backend;

import com.company.Commons.Const;
import com.company.Commons.ResponseCode;
import com.company.Commons.ServerResponse;
import com.company.dao.pojo.User;
import com.company.service.iservice.ICategoryService;
import com.company.service.iservice.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/category/")
public class CategoryManageAction {
    @Autowired
    @Qualifier("userService")
    IUserService userService;

    @Autowired
    @Qualifier("categoryService")
    ICategoryService categoryService;

    /**
     * 具有管理员权限添加品类
     * @param session
     * @param categoryName
     * @param parentId
     * @return
     */
    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpSession session, String categoryName, @RequestParam(value = "parentId",defaultValue = "0") int parentId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        System.out.println(categoryName);
        if(user==null){
            return ServerResponse.createErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),"用户需要登录");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            return categoryService.addCategory(categoryName,parentId);
        }else{
            return ServerResponse.createErrorMessageResponse("没有权限");
        }
    }

    /**
     * 根据权限更改品类名称
     * @param session
     * @param categoryId
     * @param categoryName
     * @return
     */
    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse serCateoryName(HttpSession session,Integer categoryId,String categoryName){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),"用户需要登录");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            return categoryService.updateCategoryName(categoryId,categoryName);
        }else{
            return ServerResponse.createErrorMessageResponse("没有权限");
        }
    }

    /**
     * 根据categoryId 获得平级子节点
     * @param session
     * @param categoryId
     * @return
     */
    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse setCateoryName(HttpSession session, @RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),"用户需要登录");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            return categoryService.getChildrenParallelCategory(categoryId);
        }else{
            return ServerResponse.createErrorMessageResponse("没有权限");
        }
    }

    /**
     * 具有管理员权限,获得当前id及所有递归子节点
     * @param session
     * @param categoryId
     * @return
     */
    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),"用户需要登录");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            return categoryService.selectCategoryAndDeepChildrenCategory(categoryId);
        }else{
            return ServerResponse.createErrorMessageResponse("没有权限");
        }
    }
}
