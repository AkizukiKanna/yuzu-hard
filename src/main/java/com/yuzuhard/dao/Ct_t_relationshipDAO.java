package com.yuzuhard.dao;

import com.yuzuhard.pojo.Ct_t_relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface Ct_t_relationshipDAO extends JpaRepository<Ct_t_relationship,Integer> {
    //根据文章id查询对应选中的tagid
    @Query(value = "select tid from ct_t_relationship where ctid=:ctid and status=:status",nativeQuery = true)
    int[] findByContentId(@Param("ctid")int ctid,@Param("status") String status);

    //根据文章id，tagid 插入
    @Modifying
    @Query(value = "insert into ct_t_relationship (ctid,tid,status) values (?1,?2,'saved')",nativeQuery = true)
    int insert(int ctid,int tid);
}
