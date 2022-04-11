package com.example.server.interveptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.server.Utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //预先处理以完成拦截的功能
        HashMap<String, Object> map = new HashMap<>();
        //获取请求头中的token
        String token = request.getHeader("token");
        try {
            //进行令牌验证
            JwtUtil.verify(token);
            //没有异常，验证通过
            return true;
        //之后进行异常捕捉的分类，根据不同的异常向前端返回不同的错误
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            map.put("msg", "无效签名！");
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            map.put("msg", "token过期！");
        } catch (AlgorithmMismatchException e) {
            e.printStackTrace();
            map.put("msg", "token算法不一致！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "token无效！");
        }
        map.put("state", false);//设置状态
        //map转化为json
        String json = new ObjectMapper().writeValueAsString(map);
        //指定json格式
        response.setContentType("application/json;charset=UTF-8");
        //打印对应的json
        response.getWriter().println(json);
        return false;
    }
}
