package com.example.server.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.common.po.Conference;
import org.springframework.stereotype.Repository;

/**
 * @author Amy
 * @date 2022-03-16 18:54
 * @description
 * @package org.example.server.dao
 * @title
 */
@Repository
@Mapper
public interface ConferenceDao extends BaseMapper<Conference> {
}
