package com.yuzuhard.service;

import com.yuzuhard.dao.Ct_t_relationshipDAO;
import com.yuzuhard.dto.ContentDto;
import com.yuzuhard.dto.Ct_t_relationshipDto;
import com.yuzuhard.pojo.Content;
import com.yuzuhard.pojo.Ct_t_relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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
    public Ct_t_relationshipDto getByContentId(int ctid){
        int[] tagIds = ct_t_relationshipDAO.findByContentId(ctid,"saved");

        Ct_t_relationshipDto dto = new Ct_t_relationshipDto(tagIds);
        return dto;
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
    public List<Ct_t_relationshipDto> findTidTnameByCTid(int ctid){
        List<Object[]> objectList = ct_t_relationshipDAO.findTidTnameByCTid(ctid);

        List list = new ArrayList();
        for (Object[] objects : objectList){
            Ct_t_relationshipDto ct_t_relationshipDto = new Ct_t_relationshipDto(
                    (int)objects[0],
                    (String)objects[1]);
            list.add(ct_t_relationshipDto);
        }
        return list;
    }


}
