package com.yuzuhard.service;

import com.yuzuhard.dao.Ct_t_relationshipDAO;
import com.yuzuhard.pojo.Content;
import com.yuzuhard.pojo.Ct_t_relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Ct_t_relationshipService {
    public static final String deleted = "deleted";
    public static final String saved = "saved";

    @Autowired
    Ct_t_relationshipDAO ct_t_relationshipDAO;

//    public void add(Ct_t_relationship bean){
//        bean.setStatus(saved);
//        ct_t_relationshipDAO.save(bean);
//    }

    @Transactional
    public void add(int ctid,int tid){
        ct_t_relationshipDAO.insert(ctid,tid);
    }

    public int[] getByContentId(int ctid){
        return ct_t_relationshipDAO.findByContentId(ctid,"saved");
    }
}
