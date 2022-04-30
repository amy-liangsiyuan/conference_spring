package com.example.server.controller;

import com.example.server.Utils.JwtUtil;
import com.example.server.Utils.RedisUtil;
import com.example.server.annotation.LoginRequired;
import com.example.server.service.UserService;
import com.wf.captcha.SpecCaptcha;
import org.example.common.entity.MessageConstant;
import org.example.common.entity.Result;
import org.example.common.po.User;
import org.example.common.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author Amy
 * @date 2022-03-22 18:00
 * @description
 * @package com.example.server.controller
 * @title
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private UserService userService;

    //用户登录
    @PostMapping("/login")
    public Result login(@RequestBody UserVo userVo, HttpServletRequest request) {
        //登陆控制器
        User user = new User();
        //用户数据vo封装
        BeanUtils.copyProperties(userVo, user);
        //验证码检验
        if (!userService.verifyCode(userVo.getVerKey(), userVo.getCode(), (String) redisUtil.get(userVo.getVerKey()))
        ) return new Result(false, "Verification Code is error！", MessageConstant.ERROR);
        request.getSession().setAttribute("username", user.getName());   // 给websocket取出
        try {
            //认证成功，生成jwt令牌
            User userDB = userService.login(user);
            HashMap<String, String> payload = new HashMap<>();
            payload.put("id", String.valueOf(userDB.getId()));
            payload.put("username", userDB.getName());
            String token = JwtUtil.getToken(payload);
            return new Result(true, token, "success！", userDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, "Token generation failed. Please check the account and password！");
    }

    //获取验证码
    @GetMapping("/code")
    public Result code(HttpServletRequest request) {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 45, 5);
        String verCode = specCaptcha.text().toLowerCase();
        String key = UUID.randomUUID().toString();
        // 存入redis并设置过期时间为10分钟
        redisUtil.set(key, verCode, 600);
        request.getSession().setAttribute("CAPTCHA", verCode);  //存入session
        // 将key和base64返回给前端
        return new Result(true, key, MessageConstant.VERIFICATION_CODE_SUCCESS, specCaptcha.toBase64());
    }

    //用户注册
    @PostMapping("/register")
    public Result login(@RequestBody User user) {
        if (userService.register(user)) return new Result(true, "success！", MessageConstant.OK);
        else return new Result(false, "This user already exists!");
    }

    //用户信息更新
    @PostMapping("update_user")
    @LoginRequired
    public Result updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }




}
