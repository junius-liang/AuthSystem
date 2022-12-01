package com.junius;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author junius
 * @date 2022/10/27 16:15
 **/
public class MyJwtUtil {
    public static String to = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHBpcmVfdGltZSI6MTY3MDk5NjUwMTY3NSwidXNlck5hbWUiOiJhZG1pbiIsInVzZXJJZCI6IjEwIn0.wn6oGCG-W0ou3P2V7_PdJrjQKOJ_j5vOfN5fiNgU7Rg";

    public static String getJwt(String id,String username){
        Map<String, Object> map = new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;
            {
                put("userName", username);
                put("userId",id);
                put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15);
            }
        };

        String token = JWTUtil.createToken(map, "1234".getBytes());
        return token;
    }
    public static Map<String,String> getPayloadByToken(String token){
        final JWT jwt = JWTUtil.parseToken(token);
        String userName = (String) jwt.getPayload("userName");
        String userId = (String) jwt.getPayload("userId");
        Long time = (Long) jwt.getPayload("expire_time");
        Map<String, String> map = new HashMap<>(2);
        map.put("time", String.valueOf(time));
        map.put("userName",userName);
        map.put("userId",userId);
        return map;
    }

    public static Boolean isExpire(String token){
        Map<String, String> payloadByToken = getPayloadByToken(token);
        Long time = Long.valueOf(payloadByToken.get("time"));
        return time>System.currentTimeMillis();
    }

    public static void main(String[] args) {
        Map<String, String> payloadByToken = getPayloadByToken(to);
        Long time = Long.valueOf(payloadByToken.get("time"));
        System.out.println(time>System.currentTimeMillis());
        System.out.println(payloadByToken);
    }
}
