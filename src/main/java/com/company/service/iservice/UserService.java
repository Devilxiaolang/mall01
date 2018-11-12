package com.company.service.iservice;

import com.company.dao.pojo.User;
import com.company.service.vo.PageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {
    public User findById(Integer userId);
    public String save(User user);
    public String update(User user);
    public String delete(User user);
    public List<User> findByPage(PageVO pageVO);
    public List<User> findAll();
    public List<User> findByName(String username);
    public void sessionCache();
}
