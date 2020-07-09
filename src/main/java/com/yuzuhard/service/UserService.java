package com.yuzuhard.service;

import com.yuzuhard.dao.UserDAO;
import com.yuzuhard.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;


    public User get(int id){
        return userDAO.findById(id).get();
    }

    //移除name和pwd属性
    public void removeNameAndPwd(User user){
        user.setName(null);
        user.setPassword(null);
    }

    public void update(User user){
        userDAO.save(user);
    }
}
