package com.nowcoder.weibo.controller;

import com.nowcoder.weibo.service.UserService;
import com.nowcoder.weibo.util.WeiboUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by now on 2017/7/22.
 */
@Controller
public class RegisterController {
    private static final Logger logger= LoggerFactory.getLogger(RegisterController.class);
    @Autowired
    UserService userService;
    @RequestMapping(value="/register",method=RequestMethod.POST)
    @ResponseBody
    public String register(@RequestParam("name") String name,
                           @RequestParam("password") String password,
                           HttpServletResponse response
                           ){
        try {
            Map<String, Object> map = userService.addUser(name, password);
            if (map.containsKey("ticket")) {
                Cookie cookie=new Cookie("ticket",map.get("ticket").toString());
                cookie.setPath("/");
                response.addCookie(cookie);
                return WeiboUtil.getJSONString(0, "注册成功");
            }
            else
                return WeiboUtil.getJSONString(1, map);
        }catch (Exception e){

            return WeiboUtil.getJSONString(1,"注册异常");
        }
    }

}
