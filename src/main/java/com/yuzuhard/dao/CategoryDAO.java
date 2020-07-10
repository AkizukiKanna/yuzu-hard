package com.yuzuhard.dao;

import com.yuzuhard.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryDAO extends JpaRepository<Category,Integer>{

}