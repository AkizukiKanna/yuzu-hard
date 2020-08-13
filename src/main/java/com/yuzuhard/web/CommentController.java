package com.yuzuhard.web;

import com.yuzuhard.service.CommentService;
import com.yuzuhard.util.Page4Navigator;
import com.yuzuhard.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping("/comments")
    public Page4Navigator<Object[]> list(@RequestParam(value = "start", defaultValue = "0") int start,
                                         @RequestParam(value = "size", defaultValue = "10") int size) throws Exception {
        start = 0 < start ? start : 0;
        Page4Navigator<Object[]> page = commentService.list(start, size, 5); //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        return page;
    }

    @DeleteMapping("/comments/{id}")
    public String delete(@PathVariable("id") int id) throws Exception{
        commentService.delete(id);
        return "";
    }

    @PutMapping("/comments/{id}/published")
    public Object undo(@PathVariable("id") int id) throws Exception{
        commentService.undo(id);
        return Result.success();
    }

}
