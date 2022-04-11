package com.example.server.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * @Classname JWTUtils
 * @Description JWT
 * @Version 1.0.0
 * @Date 2021/12/2 16:29
 * @Created by Amy
 */
public class JwtUtil {
    //签名设为一个成员变量用于使用和验证
    private static final String SING = "!@*(^*#sfdf&*$asdh$F&^";

    //生成token
    public static String getToken(Map<String, String> map) {

        //设置过期时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7);//默认七天过期

        //创建jwt builder
        JWTCreator.Builder builder = JWT.create();

        //payload键值
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });

        String token =builder.withExpiresAt(instance.getTime())//令牌过期时间
                .sign(Algorithm.HMAC256(SING));//签名

        return token;
    }

    //token的验证
    public static void verify(String token) {
        try{
            JWT.require(Algorithm.HMAC256(SING)).build().verify(token);//如果抛出异常说明验证没有通过
        }catch (Exception e){
            throw new RuntimeException("token生成失败，您的账号密码输入错误");
        }
    }

    //获取token信息
    public static DecodedJWT getTokenInfo(String token) {
        //verify是token解码后的集合，返回后用于日后的使用
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
        return verify;
    }

}
