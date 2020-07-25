package com.yuzuhard.service;

import com.yuzuhard.dao.UserDAO;
import com.yuzuhard.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "users")
public class UserService {
    @Autowired
    UserDAO userDAO;


    @Cacheable(key = "'users-one-'+ #p0")
    public User get(int id) {
        return userDAO.findById(id).get();
    }

    //移除name和pwd属性
    public void removeNameAndPwd(User user) {
        user.setName(null);
        user.setPassword(null);
    }

    @CacheEvict(allEntries=true)
    public void update(User user) {
        userDAO.save(user);
    }

    @Cacheable(key = "'users-one-'+ #p0+ '-' + #p1")
    public User get(String name, String password) {
        return userDAO.getByNameAndPassword(name, password);
    }
}
