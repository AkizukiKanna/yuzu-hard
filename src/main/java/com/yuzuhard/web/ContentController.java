package com.yuzuhard.web;

import com.yuzuhard.pojo.Category;
import com.yuzuhard.pojo.Content;
import com.yuzuhard.pojo.Ct_t_relationship;
import com.yuzuhard.pojo.Tag;
import com.yuzuhard.service.CategoryService;
import com.yuzuhard.service.ContentService;
import com.yuzuhard.service.Ct_t_relationshipService;
import com.yuzuhard.service.TagService;
import com.yuzuhard.util.Page4Navigator;
import com.yuzuhard.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class ContentController {
    @Autowired
    ContentService contentService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    TagService tagService;
    @Autowired
    Ct_t_relationshipService ct_t_relationshipService;

    @GetMapping("/contents")
    public Page4Navigator<Content> list(@RequestParam(value = "start", defaultValue = "0") int start,
                                        @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = 0 < start ? start : 0;
        Page4Navigator<Content> page = contentService.list(start, size, 5); //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        return page;
    }

    //新建
    @PostMapping("/contents")
    public Object add(@RequestBody Map<String, Object> datas) throws Exception {
        int ctid = -1;
        //获取flag
        String flag = datas.get("flag").toString();
        //获取category
        int cgid = Integer.parseInt(datas.get("category").toString());
        Category category = categoryService.get(cgid);
        //获取content
        Content content = new Content();
        Map<String, Object> map = (Map) datas.get("content");
        //content对象赋值
        map.forEach((k, v) -> {
            switch (k) {
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

        System.out.println(flag);
        //flag发布保存判断
        if (flag.equals("published")) {
            ctid = contentService.addPulish(content);
        } else if (flag.equals("draft")) {
            ctid = contentService.addDraft(content);
        } else
            return Result.fail("操作失败");
        //获取tag
        List<Integer> tags = (List<Integer>) datas.get("tag");
//        for (int tagId : tags) {
//            Tag tag = tagService.get(tagId);
//            Ct_t_relationship ct_t_relationship = new Ct_t_relationship();
//            ct_t_relationship.setTag(tag);
//            ct_t_relationship.setContent(content);
//            ct_t_relationshipService.add(ct_t_relationship);
//        }

        for (int tagId : tags) {
            ct_t_relationshipService.add(ctid, tagId);
        }

        return Result.success();
    }

    @GetMapping("/contents/{id}")
    public Object get(@PathVariable("id") int id) throws Exception {

        return contentService.get(id);
    }
}
