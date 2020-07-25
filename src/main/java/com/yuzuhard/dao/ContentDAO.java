package com.yuzuhard.dao;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yuzuhard.dto.ContentDto;
import com.yuzuhard.pojo.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ContentDAO extends JpaRepository<Content, Integer> {
    @Query(value = "SELECT id ,title,created ,modified ,firstImg ,status ,password ,allowComment ,pvNum   " +
            "FROM  content " ,
            nativeQuery = true)
    Page<Map<String,Object>> findNotAbstractAndText(Pageable pageable);
//    Page<List<ContentDto>> findNotAbstractAndText(Pageable pageable);

    @Query(value = "select created from content where id = :id",nativeQuery = true)
    Date findCreatedById(int id);

    //根据id更新status
    @Modifying
    @Query(value = "update content set status=:status where id = :id",nativeQuery = true)
    int updateStatus(int id,String status);

    //根据status查询
    @Query(value = "select c.id ,c.title,c.created ,c.modified ,c.firstImg ,c.articleAbstract  ,c.pvNum ,cg.name " +
            "from  content c, category cg " +
            " where c.status=:status and c.cgid=cg.id" +
            " order by id desc " +
            "limit 0,10",nativeQuery = true)
    List<Map<String,Object>> findByStatus(String status);




}
/**
 * 两表连接查询
 @Query(value = "SELECT ct.id ,ct.title,ct.created ,ct.modified ,ct.firstImg ,ct.status ,ct.password ,ct.allowComment ,ct.pvNum ,cg.name   " +
 "FROM  content ct  ,category cg " +
 "where ct.cgid=cg.id",
 nativeQuery = true)
 */