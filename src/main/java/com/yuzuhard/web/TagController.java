package com.yuzuhard.web;

import com.yuzuhard.pojo.Category;
import com.yuzuhard.pojo.Tag;
import com.yuzuhard.service.TagService;
import com.yuzuhard.util.Page4Navigator;
import com.yuzuhard.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TagController {
    @Autowired
    TagService tagService;

    @GetMapping("/tags")
    public Page4Navigator<Tag> list(@RequestParam(value = "start", defaultValue = "0") int start,
                                         @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = 0 < start ? start : 0;
        Page4Navigator<Tag> page = tagService.list(start, size, 5); //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        return page;
    }

    @DeleteMapping("/tags/{id}")
    public String delete(@PathVariable("id") int id) throws Exception {
        tagService.delete(id);
        return null;
    }

    @PostMapping("/tags")
    public Object add(@RequestBody Tag bean) throws Exception {
        tagService.add(bean);
        return bean;
    }

    @GetMapping("/tags/{id}")
    public Object get(@PathVariable("id") int id) throws Exception {
        return tagService.get(id);
    }

    @PutMapping("/tags")
    public Object update(@RequestBody Tag bean)throws Exception{
        tagService.update(bean);
        return Result.success(bean);
    }

    //根据状态返回id和标签名
    @GetMapping("/tags/findIdName/{status}")
    public Object findIdNameUseStatus(@PathVariable("status") String status)throws Exception{
        return tagService.findIdNameUseStatus(status);
    }
}
