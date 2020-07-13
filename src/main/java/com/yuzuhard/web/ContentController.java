package com.yuzuhard.web;

import com.yuzuhard.pojo.Category;
import com.yuzuhard.pojo.Content;
import com.yuzuhard.pojo.Tag;
import com.yuzuhard.service.CategoryService;
import com.yuzuhard.service.ContentService;
import com.yuzuhard.util.Page4Navigator;
import com.yuzuhard.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
public class ContentController {
    @Autowired
    ContentService contentService;
    @Autowired
    CategoryService categoryService;

    @GetMapping("/contents")
    public Page4Navigator<Content> list(@RequestParam(value = "start", defaultValue = "0") int start,
                                        @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = 0 < start ? start : 0;
        Page4Navigator<Content> page = contentService.list(start, size, 5); //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        return page;
    }

    //新建并直接发布
    @PostMapping("/contents")
    public Object add(@RequestBody Map<String,Object> datas)throws Exception{
        System.out.println(datas);


        Category category=categoryService.get(Integer.parseInt(datas.get("category").toString()));
        Content content =new Content();
        Map<String,Object> map = (Map)datas.get("content");
        //content对象赋值
        map.forEach((k,v)->{
            switch(k){
                case "id":
                    content.setId(Integer.parseInt(v.toString()));
                    break;
                case "title":
                    content.setTitle(v.toString());
                    break;
                case "articleAbstract":
                    content.setArticleAbstract(v.toString());
                    break;
                case "text":
                    content.setText(v.toString());
                    break;
                case "firstImg":
                    content.setFirstImg(v.toString());
                    break;
                case "password":
                    content.setPassword(v.toString());
                    break;
                case "allowComment":
                    content.setAllowComment(v.toString());
                    break;
            }
        });
        content.setCategory(category);
        Date date = new Date();
        content.setCreated(date);
        content.setModified(date);
        contentService.addPulish(content);
        return Result.success();
    }

    @GetMapping("/contents/{id}")
    public Object get(@PathVariable("id") int id)throws Exception{

        return contentService.get(id);
    }
}
