package com.yuzuhard.dao;

import com.yuzuhard.pojo.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface TagDAO extends JpaRepository<Tag,Integer> {
    //查询所有分类表中状态为"status"的id和name
    @Query(value = "SELECT id , name FROM  tag  WHERE status = :status" ,
            nativeQuery = true)
    List<Map<String,Object>> findIdNameUseStatus(@Param("status")String status);
}