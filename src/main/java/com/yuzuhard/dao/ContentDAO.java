package com.yuzuhard.dao;


import com.yuzuhard.pojo.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ContentDAO extends JpaRepository<Content, Integer> {
    @Query(value = "SELECT ct.id ,ct.title,ct.created ,ct.modified ,ct.firstImg ,ct.status ,ct.password ,ct.allowComment ,ct.pvNum   " +
            "FROM  content ct " ,
            nativeQuery = true)
    Page<Object[]> findNotAbstractAndText(Pageable pageable);
}
/**
 * 两表连接查询
 @Query(value = "SELECT ct.id ,ct.title,ct.created ,ct.modified ,ct.firstImg ,ct.status ,ct.password ,ct.allowComment ,ct.pvNum ,cg.name   " +
 "FROM  content ct  ,category cg " +
 "where ct.cgid=cg.id",
 nativeQuery = true)
 */