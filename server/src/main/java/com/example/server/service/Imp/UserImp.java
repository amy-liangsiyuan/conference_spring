package com.example.server.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.example.server.Utils.RedisUtil;
import com.example.server.annotation.IpRequired;
import com.example.server.dao.UserDao;
import com.example.server.service.UserService;
import org.example.common.entity.MessageConstant;
import org.example.common.entity.QueryPage;
import org.example.common.entity.Result;
import org.example.common.po.User;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Amy
 * @date 2022-03-22 18:02
 * @description
 * @package com.example.server.service.Imp
 * @title
 */
@Service
public class UserImp implements UserService {
    @Resource
    RedisUtil redisUtil;
    @Resource
    private UserDao userDao;
    @Resource
    private BCryptPasswordEncoder encoder;

    @Override
    public User login(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("id", "name", "phone", "state", "avatar", "mail", "password", "create_time");
        wrapper.eq("name", user.getName());
        //登录的用户
        User login_user = userDao.selectOne(wrapper);
        if (!encoder.matches(user.getPassword(), login_user.getPassword())) {
            throw new RuntimeException("用户名或密码不正确，登录失败");
        }
        if (login_user.getState() == 0) {
            throw new RuntimeException("用户已被禁用,登录失败");
        }
        login_user.setPassword("");
        return login_user;
    }

    @Override
    public boolean verifyCode(String verKey, String code, String realCode) throws RuntimeException {
        redisUtil.del(verKey);  // 验证码是否正确都删除，否则验证错误的验证码会存在redis中无法删除
        return realCode != null && !realCode.isEmpty() && code.equalsIgnoreCase(realCode);
    }

    @Override
    public boolean register(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", user.getName());
        if (userDao.selectOne(wrapper) != null) {
            return false;
        }
        wrapper.eq("phone", user.getPhone());
        if (userDao.selectOne(wrapper) != null) {
            return false;
        }
        User new_user = new User();
        Long id = IdWorker.getId(User.class);
        user.setName(user.getName().toLowerCase());
        BeanUtils.copyProperties(user, new_user);
        new_user.setCreateTime(new Date());
        new_user.setState(1);
        new_user.setPassword(encoder.encode(new_user.getPassword()));
        userDao.insert(new_user);
        return true;
    }

    @Override
    public User findById(String userId) {
        return userDao.selectById(userId);
    }

    @Override
    public boolean updateAvatar(String path, User user) {
        //如果用户为空则直接返回
        if (user == null) return false;
        if (path.isEmpty()) {
            //如果是空路径，说明是删除操作
            String filePath = System.getProperty("user.dir") + "/File/avatar/" + user.getAvatar().trim().substring(user.getAvatar().lastIndexOf("/") + 1);
            if (new File(filePath).delete()) {
                User newUser = userDao.selectById(user.getId());
                newUser.setAvatar(path);
                userDao.updateById(newUser);
                return true;
            } else return false;
        }
        //否则是更新操作
        User newUser = userDao.selectById(user.getId());
        newUser.setAvatar(path);
        userDao.updateById(newUser);
        return true;
    }

    @Override
    public Result updateUser(User user) {
        //用户为空直接报错
        if (user == null) return new Result(false, "error");
        //创建查询对象
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", user.getPhone());
        User newUser = this.findById(user.getId());
        //密码如果不为空设置新密码
        if (!user.getPassword().isEmpty()) newUser.setPassword(encoder.encode(user.getPassword()));
        //邮箱如果不为空直接设置新邮箱
        if (!user.getMail().isEmpty()) newUser.setMail(user.getMail());
        //电话号码如果不为空
        if (!user.getPhone().isEmpty()) {
            //如果电话更改
            if (!user.getPhone().equals(newUser.getPhone()))
                //如果此电话已经注册
                if (userDao.selectOne(wrapper) != null)
                    return new Result(false, "Only one user can be registered with a phone number!");
            newUser.setPhone(user.getPhone());
        }
        userDao.updateById(newUser);
        //最后把密码去掉
        newUser.setPassword("");
        return new Result(true, MessageConstant.OK, "Update success!", newUser);
    }
}
