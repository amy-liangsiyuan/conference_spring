package com.example.server.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.common.po.User;
import org.springframework.stereotype.Repository;

/**
 * @author Amy
 * @date 2022-03-16 18:54
 * @description
 * @package org.example.server.dao
 * @title
 */
@Repository
public interface UserDao extends BaseMapper<User> {
}
