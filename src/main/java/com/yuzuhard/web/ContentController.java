package com.yuzuhard.web;

import com.yuzuhard.pojo.Category;
import com.yuzuhard.pojo.Content;
import com.yuzuhard.service.ContentService;
import com.yuzuhard.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContentController {
    @Autowired
    ContentService contentService;

    @GetMapping("/contents")
    public Page4Navigator<Content> list(@RequestParam(value = "start", defaultValue = "0") int start,
                                        @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = 0 < start ? start : 0;
        Page4Navigator<Content> page = contentService.list(start, size, 5); //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        return page;
    }
}
