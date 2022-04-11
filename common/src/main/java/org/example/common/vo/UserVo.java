package org.example.common.vo;

import lombok.Data;
import org.example.common.po.User;


/**
 * @author Amy
 * @date 2022-03-16 18:21
 * @description user
 * @package org.example.common.po
 * @title user
 */
@Data
public class UserVo extends User {
    private String code; // 登录时的验证码
    private String verKey;  // 缓存在redis中的验证码的key
}
