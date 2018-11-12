package com.company.service.impl;

import com.company.dao.UserDao;
import com.company.dao.pojo.User;
import com.company.service.iservice.UserService;
import com.company.service.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
            @Qualifier("userDao")
    UserDao userDao;

    @Override
    public User findById(Integer userId) {

        return userDao.findById(userId,(int)(Math.random()*1000));
    }

    @Override
    public String save(User user) {
        userDao.save(user);
        return "success";
    }

    @Override
    public String update(User user) {
        userDao.update(user);
        return "success";
    }

    @Override
    public String delete(User user) {
        userDao.delete(user);
        return "success";
    }

    @Override
    public List<User> findByPage(PageVO pageVO) {
        return userDao.findByPage(pageVO);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public List<User> findByName(String username) {
        return userDao.findByName(username);
    }

    @Override
    public void sessionCache() {

    }
}
