package com.yuzuhard.web;

import com.yuzuhard.pojo.User;
import com.yuzuhard.service.UserService;
import com.yuzuhard.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;


@RestController
public class UserController {
    @Autowired
    UserService userService;

    //返回用户名和密码为null的user对象
    @GetMapping("/user")
    public Object get(@RequestParam(value = "id",defaultValue = "1") int id) throws Exception{
        User user = userService.get(id);
        userService.removeNameAndPwd(user);
        String signature = user.getSignature();
        user.setSignature(HtmlUtils.htmlUnescape(signature));
        return user;
    }

    //更新user的profilePicture和signature属性
    //转义signature属性
    @PutMapping("/user")
    public Object update(User user) throws Exception{
        User bean = userService.get(user.getId());
        user.setName(bean.getName());
        user.setPassword(bean.getPassword());
        String signature = user.getSignature();
        user.setSignature(HtmlUtils.htmlEscape(signature));
        userService.update(user);
        return Result.success();
    }

    @PostMapping("/user")
    public Object login(@RequestBody User userParam, HttpSession session) {
        String name =  userParam.getName();
        String password = userParam.getPassword();
        User user =userService.get(name,password);
        if(null==user){
            String message ="有这时间不如去看看我老婆";
            return Result.fail(message);
        }
        else{
            session.setAttribute("user", user);
            return Result.success();
        }
    }
}
