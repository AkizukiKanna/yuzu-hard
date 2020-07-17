package com.yuzuhard.web;

import com.yuzuhard.service.ContentService;
import com.yuzuhard.service.Ct_t_relationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ForeController {
    @Autowired
    ContentService contentService;
    @Autowired
    Ct_t_relationshipService ct_t_relationshipService;

//    @GetMapping("/fore/contents")
//    public Object getContents(){
//        return contentService.getByStatus(contentService.published);
//    }

    @GetMapping("/fore/contents")
    public Object getContents(){
        List list =new ArrayList();

        List<Map<String, Object>> list_content = contentService.getByStatus(contentService.published);

        for (Map<String,Object> map_content: list_content){
            int id = Integer.parseInt(map_content.get("id").toString());
            List<Map<String, Object>> map_tag  = ct_t_relationshipService.findTidTnameByCTid(id);
            Map<String,Object> map = new HashMap<>(map_content);
            map.put("tag",map_tag);
            list.add(map);
        }

        return list;
    }


    //根据ctid查对应tid和tname
    @GetMapping("/fore/contents/{id}/tag")
    public List<Map<String, Object>> getTagByCTid(@PathVariable("id") int ctid){
        return ct_t_relationshipService.findTidTnameByCTid(ctid);
    }
}
