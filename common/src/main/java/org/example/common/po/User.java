package org.example.common.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Amy
 * @date 2022-03-16 18:21
 * @description user
 * @package org.example.common.po
 * @title user
 */
@Data
public class User {
    //id
    private String id;

    //姓名
    private String name;

    //手机号码
    private String phone;

    //密码
    private String password;

    //角色
    private int role;

    //状态
    private int state;

    //邮箱
    private String mail;

    //头像
    private String avatar;

    //创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;
}
