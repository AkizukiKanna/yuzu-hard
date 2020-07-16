package com.yuzuhard.web;

import com.yuzuhard.service.Ct_t_relationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CtTRelationshipController {
    @Autowired
    Ct_t_relationshipService ct_t_relationshipService;

    //根据contentId选取选中的tagsID
    @GetMapping("/contents/{id}/tags")
    public int[] getByContent(@PathVariable("id") int id){
        return ct_t_relationshipService.getByContentId(id);
    }
}
