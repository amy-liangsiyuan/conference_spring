package com.example.server.service;

import org.example.common.entity.Result;
import org.example.common.po.User;

/**
 * @author Amy
 * @date 2022-03-22 18:01
 * @description
 * @package com.example.server.service
 * @title
 */
public interface UserService {
    //登录
    User login(User user);

    //用户注册
    boolean register(User user);

    //更新用户信息
    Result updateUser(User user);

    //校验验证码
    boolean verifyCode(String verKey, String captcha, String sessionCode);

    //根据id查找用户
    User findById(String userId);

    //更新用户头像
    boolean updateAvatar(String path,User user);



}
