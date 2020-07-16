package com.yuzuhard.dao;


import com.yuzuhard.pojo.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Map;


public interface ContentDAO extends JpaRepository<Content, Integer> {
    @Query(value = "SELECT id ,title,created ,modified ,firstImg ,status ,password ,allowComment ,pvNum   " +
            "FROM  content " ,
            nativeQuery = true)
    Page<Map<String,Object>> findNotAbstractAndText(Pageable pageable);

    @Query(value = "select created from content where id = :id",nativeQuery = true)
    Date findCreatedById(int id);

    //根据id更新status
    @Modifying
    @Query(value = "update content set status=:status where id = :id",nativeQuery = true)
    int updateStatus(int id,String status);
}
/**
 * 两表连接查询
 @Query(value = "SELECT ct.id ,ct.title,ct.created ,ct.modified ,ct.firstImg ,ct.status ,ct.password ,ct.allowComment ,ct.pvNum ,cg.name   " +
 "FROM  content ct  ,category cg " +
 "where ct.cgid=cg.id",
 nativeQuery = true)
 */