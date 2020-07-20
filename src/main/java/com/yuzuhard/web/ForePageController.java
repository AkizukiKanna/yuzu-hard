package com.yuzuhard.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForePageController {

    @GetMapping(value="/")
    public String index(){
        return "fore/home";
    }
    @GetMapping("/home")
    public String home(){
        return "fore/home";
    }
    @GetMapping("/detail")
    public String detail(){
        return "fore/detail";
    }
}
