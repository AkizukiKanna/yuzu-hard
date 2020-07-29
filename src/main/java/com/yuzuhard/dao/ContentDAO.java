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
//    Page<Map<String,Object>> findNotAbstractAndText(Pageable pageable);
    Page<Object[]> findNotAbstractAndText(Pageable pageable);

    @Query(value = "select created from content where id = :id",nativeQuery = true)
    Date findCreatedById(int id);

    //根据id更新status
    @Modifying
    @Query(value = "update content set status=:status where id = :id",nativeQuery = true)
    int updateStatus(int id,String status);

    //根据status查询
    @Query(value = "select c.id ,c.title ,c.articleAbstract ,c.created ,c.modified ,c.firstImg   ,c.pvNum ,cg.name " +
            "from  content c, category cg " +
            " where c.status=:status and c.cgid=cg.id" +
            " order by id desc " +
            "limit 0,10",nativeQuery = true)
    List<Object[]> findByStatus(String status);


    //根据cgid查询文章
    @Query(value = "select c.id ,c.title ,c.articleAbstract ,c.created ,c.modified ,c.firstImg   ,c.pvNum ,cg.name " +
            "from  content c, category cg " +
            " where c.cgid=:cgid and c.cgid=cg.id and c.status='published' ",
            countQuery = "select count(*) from content , category where content.cgid=:cgid and content.cgid=category.id and content.status='published' ",
            nativeQuery = true)
    Page<Object[]> findByCategoryOrderById(int cgid,Pageable pageable);

    //根据tid查询文章
    @Query(value = "select c.id ,c.title ,c.articleAbstract ,c.created ,c.modified ,c.firstImg   ,c.pvNum  " +
            "from  content c, ct_t_relationship r " +
            " where r.tid=:tid and r.ctid=c.id and c.status='published' ",
            countQuery = "select count(*) from content ,ct_t_relationship where ct_t_relationship.tid=:tid and ct_t_relationship.ctid=content.id and content.status='published' ",
            nativeQuery = true)
    Page<Object[]> findByTagOrderById(int tid,Pageable pageable);
}
