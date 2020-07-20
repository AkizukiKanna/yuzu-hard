package com.yuzuhard.dao;

import com.yuzuhard.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserDAO extends JpaRepository<User,Integer>{
    User getByNameAndPassword(String name,String password);
}