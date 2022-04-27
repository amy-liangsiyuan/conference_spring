package com.example.server.service;

import org.example.common.entity.Result;
import org.example.common.po.Participant;

/**
 * @author Amy
 * @date 2022-04-27 21:47
 * @description
 * @package com.example.server.service
 * @title
 */
public interface ParticipantService {
    //参与者登录接口
    Result Login(Participant participant,String conferenceId);

    //参与者注册接口
    Result Register(Participant participant,String conferenceId);
}
