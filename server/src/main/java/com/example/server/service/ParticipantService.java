package com.example.server.service;

import org.example.common.entity.Result;
import org.example.common.po.Participant;
import org.springframework.web.multipart.MultipartFile;

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

    //获取会议参与者列表
    Result getParticipantsList(String id);

    //改变状态
    Result changeState(String conference_id,String participant_id);

    //改变身份
    Result changeReferee(String conference_id,String participant_id);

}
