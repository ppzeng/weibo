package com.nowcoder.weibo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * Created by now on 2017/7/18.
 */
@Controller
public class IndexController {
    @Autowired
    private ToutiaoService toutiaoService;

    @RequestMapping("/home")
    public String index(HttpSession session){
        return "home";
    }
    @RequestMapping(value="/profile/{groupId}/{userId}")
    @ResponseBody
    public String profile(@PathVariable("groupId") String groupId,
                          @PathVariable("userId") int userId,
                          @RequestParam(value="type",defaultValue="1") int type,
                          @RequestParam(value="key",defaultValue="nowcoder") String key){
        return String.format("GID{%s},UID{%d},TYPE{%d},KEY{%s}",groupId,userId,type,key);
    }
    @RequestMapping(value={"/vm"})
    public String news(){

        return "news";
    }
    @RequestMapping(value={"/request"})
    @ResponseBody
    public String request(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession session
                          ){
        StringBuilder sb=new StringBuilder();
        Enumeration<String> headerNames=request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String name=headerNames.nextElement();
            sb.append(name+":"+request.getHeader(name)+"<br>");
        }
        sb.append("<br>");
        for(Cookie cookie:request.getCookies()){
            sb.append("Cookie:");
            sb.append(cookie.getName());
            sb.append(":");
            sb.append("C00kie:");
            sb.append(cookie.getValue());
            sb.append("<br>");
        }
        sb.append("getMethod:"+request.getMethod()+"<br>");
        sb.append("getPathInfo:"+request.getPathInfo()+"<br>");
        sb.append("getQueryString:"+request.getQueryString()+"<br>");
        sb.append("getResquestURI:"+request.getRequestURI()+"<br>");
        return sb.toString();
    }
    @RequestMapping(value={"/response"})
    @ResponseBody
    public String response(@CookieValue(value="nowcoderid",defaultValue="a") String nowcoderId,
                           @RequestParam(value="key",defaultValue="key") String key,
                           @RequestParam(value="value",defaultValue="value")String value,
                            HttpServletResponse response){
        response.addCookie(new Cookie(key,value));
        response.addHeader(key,value);
        return "Nowcoder From Cookie:"+nowcoderId;
    }

    @RequestMapping("/redirect/{code}")
    public RedirectView redirect(@PathVariable("code") int code,
                           HttpSession session){
       RedirectView red=new RedirectView("/",true);
        session.setAttribute("msg","jump from redict");
        if(code==301){
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return red;
      /* return "redirect:/";*/
    }
}
