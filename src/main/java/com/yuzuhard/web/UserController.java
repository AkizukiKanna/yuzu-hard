package com.yuzuhard.web;

import com.yuzuhard.pojo.User;
import com.yuzuhard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class UserController {
    @Autowired
    UserService userService;

    //返回用户名和密码为null的user对象
    @GetMapping("/user")
    public Object get(@RequestParam(value = "id",defaultValue = "1") int id) throws Exception{
        User user = userService.get(id);
        userService.removeNameAndPwd(user);
        return user;
    }

    //更新user的profilePicture和signature属性
    //不能直接返回user对象，内含有账号密码
    @PutMapping("/user")
    public Object update(User user) throws Exception{
        User bean = userService.get(user.getId());
        user.setName(bean.getName());
        user.setPassword(bean.getPassword());
        userService.update(user);
        return new Object();
    }
}
