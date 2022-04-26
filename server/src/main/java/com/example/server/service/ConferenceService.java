package com.example.server.service;
import com.github.pagehelper.PageInfo;
import org.example.common.entity.QueryPage;
import org.example.common.entity.Result;
import org.example.common.po.Conference;
import org.example.common.po.User;
import org.example.common.vo.ConferenceVO;

import java.util.List;

/**
 * @author Amy
 * @date 2022-03-16 19:15
 * @description
 * @package org.example.server.service
 * @title
 */
public interface ConferenceService {
    //获取推荐列表
    List<ConferenceVO> getRecommendList();

    //获取我用户的会议列表
    List<Conference> getMyConference(User user);
    //新建会议
    boolean add_Conference(Conference add_Conference, User user);

    //新建会议
    boolean update_Conference(Conference updates_Conference);

    //分分页获取会议列表
    PageInfo<Conference> getPage(QueryPage queryPage);

    //更新用户头像
    boolean updateFirstPicture(String path,String id);

    ///改变会议状态
    Result changeState(String id);

    //获取特定会议
    Result getConference(String id);
}
