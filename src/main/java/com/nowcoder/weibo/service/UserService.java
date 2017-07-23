package com.nowcoder.weibo.service;

import com.nowcoder.weibo.dao.LoginTicketDao;
import com.nowcoder.weibo.dao.UserDao;
import com.nowcoder.weibo.model.LoginTicket;
import com.nowcoder.weibo.model.User;
import com.nowcoder.weibo.util.MD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by now on 2017/7/21.
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private LoginTicketDao loginTicketDao;

    //注册
    public Map<String,Object> addUser(String name,String password) {
        //前台页面信息
        Map<String,Object> map=new HashMap<>();

        if (StringUtils.isBlank(name)) {
            map.put("msg", "用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }
        if(userDao.findByName(name)!= null){
            map.put("msg","用户已存在");
            return map;
        }

        User user=new User();
        user.setName(name);
        user.setHeadUrl("String.format(\"http://images.nowcoder.com/head/%dt.png\", new Random().nextInt(1000))");
        user.setSalt(UUID.randomUUID().toString());
        user.setPassword(MD5.getMd5(password+user.getSalt()));
        userDao.addUser(user);
        //注册成功后登陆
        String ticket=addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }
    //登陆
    public Map<String,Object> login(String name,String password) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(name)) {
            map.put("msg", "用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }
        User user = userDao.findByName(name);
        if (user == null) {
            map.put("msg", "用户名不存在");
            return map;
        }
        if (!MD5.getMd5(password + user.getSalt()).equals(user.getPassword())) {
            map.put("msg", "密码错误");
            return map;
        }
        map.put("uId",user.getId());
        String ticket=addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }
        private String addLoginTicket(int uId){
            LoginTicket ticket=new LoginTicket();
            ticket.setUserId(uId);
            Date date=new Date();
            date.setTime(date.getTime()+1000*3600*24);
            ticket.setExpired(date);
            ticket.setStatus(0);
            ticket.setTicket(UUID.randomUUID().toString().replaceAll("-","."));
            loginTicketDao.addTicket(ticket);
            return ticket.getTicket();

    }

    //登出
    public void logout(String ticket){
           loginTicketDao.updateStatus(ticket,1);
    }
}
