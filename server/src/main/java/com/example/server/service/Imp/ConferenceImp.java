package com.example.server.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.example.server.dao.ConferenceDao;
import com.example.server.service.ConferenceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.example.common.entity.MessageConstant;
import org.example.common.entity.QueryPage;
import org.example.common.entity.Result;
import org.example.common.po.Conference;

import org.example.common.po.User;
import org.example.common.vo.ConferenceVO;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Amy
 * @date 2022-03-16 19:14
 * @description
 * @package org.example.server.service.Imp
 * @title
 */
@Service
public class ConferenceImp implements ConferenceService {
    @Resource
    private ConferenceDao conferenceDao;

    @Override
    public Result getConference(String id) {
        return new Result(true, MessageConstant.OK, "success",conferenceDao.selectById(id));
    }

    @Override
    public List<ConferenceVO> getRecommendList() {
        QueryWrapper<Conference> wrapper = new QueryWrapper<>();
        wrapper.select("id", "name", "create_Time", "city")
                .last("limit 6")
                .orderByDesc("create_Time");
        List<ConferenceVO> list = new LinkedList<>();
        List<Conference> list1 = conferenceDao.selectList(wrapper);

        for (Conference conference : list1) {

            ConferenceVO conferenceVO = new ConferenceVO();
            BeanUtils.copyProperties(conference, conferenceVO);
            list.add(conferenceVO);
        }
        return list;
    }

    @Override
    public boolean add_Conference(Conference add_Conference, User user) {
        QueryWrapper<Conference> wrapper = new QueryWrapper<>();
        wrapper.eq("name", add_Conference.getName());
        if (conferenceDao.selectOne(wrapper) != null)
            return false;
        Conference conference = new Conference();
        BeanUtils.copyProperties(add_Conference, conference);
        conference.setOwnerId(user.getId());
        conference.setOwnerName(user.getName());
        conference.setPhone(user.getPhone());
        conference.setMail(user.getMail());
        conference.setState(1);
        conferenceDao.insert(conference);
        return true;
    }

    @Override
    public PageInfo<Conference> getPage(QueryPage queryPage) {
        QueryWrapper<Conference> wrapper = new QueryWrapper<>();
        wrapper.select("name", "id", "create_Time", "city");
        if (!queryPage.getQueryString().isEmpty())
            wrapper.like("name", queryPage.getQueryString());
        PageHelper.startPage(queryPage.getCurrentPage(), queryPage.getPageSize());
        List<Conference> list = conferenceDao.selectList(wrapper);
        return new PageInfo<>(list);
    }

    @Override
    public List<Conference> getMyConference(User user) {
        QueryWrapper<Conference> wrapper = new QueryWrapper<>();
        return conferenceDao.selectList(wrapper);
    }

    @Override
    public boolean update_Conference(Conference updates_Conference) {
        QueryWrapper<Conference> wrapper = new QueryWrapper<>();
        wrapper.eq("name", updates_Conference.getName());
        Conference conference = conferenceDao.selectOne(wrapper);
        BeanUtils.copyProperties(updates_Conference, conference);
        conference.setCity(updates_Conference.getCity());
        conference.setAddress(updates_Conference.getAddress());
        conference.setDepartment(updates_Conference.getDepartment());
        conference.setCreateTime(updates_Conference.getCreateTime());
        conference.setDropTime(updates_Conference.getDropTime());
        conference.setLanguage(updates_Conference.getLanguage());
        conference.setPeopleNum(updates_Conference.getPeopleNum());
        conference.setDescription(updates_Conference.getDescription());
        conferenceDao.updateById(conference);
        return true;

    }

    @Override
    public boolean updateFirstPicture(String path, String id) {
        //如果id为空则直接返回
        if (id.isEmpty()) return false;
        Conference conference = conferenceDao.selectById(id);
        if (path.isEmpty()) {
            //如果是空路径，说明是删除操作
            String filePath = System.getProperty("user.dir") + "/File/firstPicture/" + conference.getFirstPicture().trim().substring(conference.getFirstPicture().lastIndexOf("/") + 1);
            if (new File(filePath).delete()) {
                Conference newConference = conferenceDao.selectById(id);
                newConference.setFirstPicture(path);
                conferenceDao.updateById(newConference);
                return true;
            } else return false;
        }
        //否则是更新操作
        conference.setFirstPicture(path);
        conferenceDao.updateById(conference);
        return true;
    }

    @Override
    public Result changeState(String id) {
        Conference conference = conferenceDao.selectById(id);
        conference.setState(conference.getState() == 1 ? 0 : 1);
        conferenceDao.updateById(conference);
        return new Result(true,"Change success!");
    }
}
