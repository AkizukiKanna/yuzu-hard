package com.yuzuhard.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {
    @GetMapping("/admin_yuzu")
    public String admin(){
        return "admin/userInfo";
    }
    @GetMapping("/admin_userInfo")
    public String userInfo(){
        return "admin/userInfo";
    }
    @GetMapping("/admin_category_list")
    public String listCategory(){
        return "admin/listCategory";
    }
    @GetMapping("/admin_category_edit")
    public String editCategory(){
        return "admin/editCategory";
    }
    @GetMapping("/admin_tag_list")
    public String listTag(){
        return "admin/listTag";
    }
    @GetMapping("/admin_tag_edit")
    public String editTag(){
        return "admin/editTag";
    }
}
