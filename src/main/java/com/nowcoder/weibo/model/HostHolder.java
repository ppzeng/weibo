package com.nowcoder.weibo.model;

import org.springframework.stereotype.Component;

/**
 * Created by now on 2017/7/23.
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users=new ThreadLocal<>();

    public User getUser(){
        return users.get();
    }
    public void setUsers(User user){
        users.set(user);
    }

    public void clear(){
        users.remove();
    }

}
