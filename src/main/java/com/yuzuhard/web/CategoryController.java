package com.yuzuhard.web;

import com.yuzuhard.pojo.Category;
import com.yuzuhard.service.CategoryService;
import com.yuzuhard.util.Page4Navigator;
import com.yuzuhard.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public Page4Navigator<Category> list(@RequestParam(value = "start", defaultValue = "0") int start,
                                         @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = 0 < start ? start : 0;
        Page4Navigator<Category> page = categoryService.list(start, size, 5); //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        return page;
    }

    @DeleteMapping("/categories/{id}")
    public String delete(@PathVariable("id") int id) throws Exception {
        categoryService.delete(id);
        return null;
    }

    @PostMapping("/categories")
    public Object add(@RequestBody Category bean) throws Exception {
        categoryService.add(bean);
        return bean;
    }

    @GetMapping("/categories/{id}")
    public Object get(@PathVariable("id") int id) throws Exception {
        return categoryService.get(id);
    }

    @PutMapping("/categories")
    public Object update(@RequestBody Category bean)throws Exception{
        categoryService.update(bean);
        return Result.success(bean);
    }
}
