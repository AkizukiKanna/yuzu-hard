package com.yuzuhard.dao;

import com.yuzuhard.pojo.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentDAO extends JpaRepository<Comment, Integer> {
    @Query(value = "SELECT t1.id ,content.id ctid,content.title ,t1.text ,t1.created ,t1.url ,t1.ownerName ,t1.mail ,t1.ip ,t1.status,t2.ownerName t2o   " +
            "FROM  comment t1,comment t2 , content" +
            " WHERE t1.parent = t2.id and t1.ctid = content.id",
            countQuery = "select count(*) from  comment t1,comment t2 , content WHERE t1.parent = t2.id and t1.ctid = content.id",
            nativeQuery = true)
    Page<Object[]> findAllComments(Pageable pageable);

    //根据id更新status
    @Modifying
    @Query(value = "update comment set status=:status where id = :id",nativeQuery = true)
    int updateStatus(int id,String status);

    //前台显示
    @Query(value = "select cm.id ,cm.text,cm.created, cm.ownerName, cm.parent " +
            " from comment cm " +
            " where ctid = :ctid and cm.status='published' and parent=1 " ,
            countQuery = "select count(*) from comment where ctid = :ctid and status='published' and parent=1 "
            ,nativeQuery = true)
    Page<Object[]> findByCTid(int ctid,Pageable pageable);

    @Query(value = "select cm.id ,cm.text,cm.created, cm.ownerName, cm.parent " +
            " from comment cm " +
            " where cm.status='published' and parent=:parent " ,
            countQuery = "select count(*) from comment where status='published' and parent=:parent "
            ,nativeQuery = true)
    List<Object[]> findChildByParent(int parent);

    @Query(value = "select cm.id  cmid,ct.id ctid,  ct.title,cm.text,cm.created, cm.url , cm.ownerName " +
            " from comment cm , content ct " +
            " where cm.status='published' and cm.ctid = ct.id " +
            " order by cm.id desc " +
            " limit 0, :num "
            ,nativeQuery = true)
    List<Object[]> findRecentlyComments(int num);
}
