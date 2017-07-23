package com.nowcoder.weibo.interceptor;

import com.nowcoder.weibo.dao.LoginTicketDao;
import com.nowcoder.weibo.dao.UserDao;
import com.nowcoder.weibo.model.HostHolder;
import com.nowcoder.weibo.model.LoginTicket;
import com.nowcoder.weibo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by now on 2017/7/23.
 */
@Component
public class PassportInterceptor implements HandlerInterceptor{
    @Autowired
    HostHolder hostHolder;
   @Autowired
    LoginTicketDao loginTicketDao;
   @Autowired
   UserDao userDao;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String ticket=null;
        if (httpServletRequest.getCookies()!=null){
            for (Cookie cookie:httpServletRequest.getCookies()){
                if (cookie.getName().equals("ticket")){
                    ticket=cookie.getValue();
                    break;
                }
            }
        }

        if (ticket!=null){
            //验证是否是伪造的ticket
            LoginTicket loginTicket=loginTicketDao.findByTicket(ticket);
            if (loginTicket==null||loginTicket.getExpired().before(new Date())||loginTicket.getStatus()!=0){
                return true;
            }
            User user=userDao.findById(loginTicket.getUserId());
            hostHolder.setUsers(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if (modelAndView!=null&&hostHolder.getUser()!=null){
        modelAndView.addObject("user",hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
