package com.yuzuhard.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @GetMapping("get")
    public Object get(){
        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("key","background-image: url('http://live2d.yuzu-hard.xyz/82109697_p0.jpg')");
        map.put("key","http://live2d.yuzu-hard.xyz/82109697_p0.jpg");
        return map;
    }
}
