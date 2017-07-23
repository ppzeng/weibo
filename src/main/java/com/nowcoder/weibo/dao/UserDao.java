package com.nowcoder.weibo.dao;

import com.nowcoder.weibo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by now on 2017/7/21.
 */
@Mapper
public interface UserDao {
    //@Insert({"insert into user(name,password,salt,head_url) values()"})
    String TABLE_NAME=" user ";
    String INSERT_FIELDS=" name,password,salt,head_url ";
    String SELECT_FIELDS=" id,name,password,salt,head_url ";

    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,") values (#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where name=#{name}"})
    User findByName(String name);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where id=#{id}"})
    User findById(int id);
}
