package com.company.Commons;

public class Const {
    public static final String CURRENT_USER="currentUser";
    public interface Role{
        int ROLE_USER=0;//用户权限
        int Role_ADMIN=1;//管理员权限
    }
    //注册时用来校验用户名或者密码是否有效的类型
    public interface VaildType{
        String EMAIL="email";
        String USERNAME="username";
    }
}
