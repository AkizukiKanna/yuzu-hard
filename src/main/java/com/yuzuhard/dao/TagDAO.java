package com.yuzuhard.dao;

import com.yuzuhard.pojo.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagDAO extends JpaRepository<Tag,Integer> {

}