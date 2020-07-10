package com.yuzuhard.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {
    @GetMapping("/yuzu_admin")
    public String admin(){
        return "admin/userInfo";
    }
    @GetMapping("/userInfo")
    public String userInfo(){
        return "admin/userInfo";
    }
    @GetMapping("/listCategory")
    public String listCategory(){
        return "admin/listCategory";
    }
}
