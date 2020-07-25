package com.yuzuhard.service;

import com.yuzuhard.dao.Ct_t_relationshipDAO;
import com.yuzuhard.pojo.Content;
import com.yuzuhard.pojo.Ct_t_relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@CacheConfig(cacheNames = "ct_t_relation")
public class Ct_t_relationshipService {
    public static final String deleted = "deleted";
    public static final String saved = "saved";

    @Autowired
    Ct_t_relationshipDAO ct_t_relationshipDAO;

    @Transactional
    @CacheEvict(allEntries=true)
    public void add(int ctid,int tid){
        ct_t_relationshipDAO.insert(ctid,tid);
    }

    @Cacheable(key = "'ct_t_relation-'+ #p0")
    public int[] getByContentId(int ctid){
        return ct_t_relationshipDAO.findByContentId(ctid,"saved");
    }

    @Transactional
    @CacheEvict(allEntries=true)
    public void deleteByCtid(int id){
        ct_t_relationshipDAO.deleteByCtid(id);
    }

    @Transactional
    @CacheEvict(allEntries=true)
    public void updateStatus(int ctid,String status){
        ct_t_relationshipDAO.updateStatus(ctid,status);
    }

    //根据ctid查对应tid和tname
    @Cacheable(key = "'findTidTnameByCTid-'+ #p0")
    public List<Map<String, Object>> findTidTnameByCTid(int ctid){
        return ct_t_relationshipDAO.findTidTnameByCTid(ctid);
    }
}
