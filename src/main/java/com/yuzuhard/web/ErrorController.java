package com.yuzuhard.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {
    @GetMapping("/404")
    public String error_404(){
        return "error/404";
    }
    @GetMapping("/500")
    public String error_500(){
        return "error/500";
    }
}
