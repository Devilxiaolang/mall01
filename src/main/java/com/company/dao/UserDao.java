package com.company.dao;

import com.company.dao.pojo.User;
import com.company.service.vo.PageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 对User表进行CRUD操作
 */
public interface UserDao {
    /**
     *
     * @param userId
     * @return User
     */
    public User findById(@Param("userId") Integer userId,@Param("randNum") Integer randNum);
    public void save(User user);
    public void update(User user);
    public void delete(User user);
    public List<User> findByPage(PageVO pageVO);
    public List<User> findAll();
    public List<User> findByName(String username);
}
