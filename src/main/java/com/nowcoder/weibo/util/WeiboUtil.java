package com.nowcoder.weibo.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by now on 2017/7/22.
 */
public class WeiboUtil {
    private static final Logger logger= LoggerFactory.getLogger(WeiboUtil.class);

    //code为0时正确，为1时错误
    public  static String getJSONString(int code){
        JSONObject json=new JSONObject();
        json.put("code",code);
        return json.toJSONString();
    }

    public  static String getJSONString(int code, Map<String,Object> map){
        JSONObject json=new JSONObject();
        json.put("code",code);
        for(Map.Entry<String,Object> entry:map.entrySet()){
            json.put(entry.getKey(),entry.getValue());
        }
        return json.toJSONString();
    }

    public  static String getJSONString(int code, String s){
        JSONObject json=new JSONObject();
        json.put("code",s);
        return json.toJSONString();
    }
}
