package com.nowcoder.weibo.controller;

import com.nowcoder.weibo.model.HostHolder;
import com.nowcoder.weibo.service.UserService;
import com.nowcoder.weibo.util.WeiboUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by now on 2017/7/21.
 */
@Controller
public class LoginController {
    private static final Logger logger= LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(value="/login",method= RequestMethod.POST)
    @ResponseBody
    public String login(@RequestParam("name") String name,
                        @RequestParam("password") String password,
                        HttpServletResponse response,
                        Model model){
        try {
            Map<String, Object> map = new HashMap();
            if(hostHolder.getUser() != null ){
                map.put("msg","您已登陆");
                return WeiboUtil.getJSONString(1,map);
            }
            map = userService.login(name, password);
            if (map.containsKey("ticket")) {
                Cookie cookie=new Cookie("ticket",map.get("ticket").toString());
                cookie.setPath("/");
                response.addCookie(cookie);
                return WeiboUtil.getJSONString(0, "登陆成功");
            }
            else
                return WeiboUtil.getJSONString(1, map);
        }catch(Exception e){
            logger.error("登陆异常"+e.getMessage());
            return WeiboUtil.getJSONString(1,"登陆异常");
        }
    }

    @RequestMapping(path={"/logout"},method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String logout(@CookieValue("ticket") String ticket){
        userService.logout(ticket);
        return "登出成功";
    }
}