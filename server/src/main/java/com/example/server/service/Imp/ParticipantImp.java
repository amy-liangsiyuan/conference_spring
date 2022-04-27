package com.example.server.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.server.dao.ParticipantConferenceDao;
import com.example.server.dao.ParticipantDao;
import com.example.server.service.ParticipantService;
import org.example.common.entity.MessageConstant;
import org.example.common.entity.Result;
import org.example.common.po.Participant;
import org.example.common.po.ParticipantConference;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Amy
 * @date 2022-04-27 21:47
 * @description
 * @package com.example.server.service.Imp
 * @title
 */
@Service
public class ParticipantImp implements ParticipantService {
    @Resource
    private BCryptPasswordEncoder encoder;
    @Resource
    ParticipantDao participantDao;
    @Resource
    ParticipantConferenceDao participantConferenceDao;

    /**
     * 方法说明
     * @author Amy
     * @date 2022/4/27 21:48
     * @description
     * @param participant
     * @return org.example.common.entity.Result
     */
    @Override
    public Result Login(Participant participant,String conferenceId) {
        //根据邮箱查询
        QueryWrapper<Participant> participantQueryWrapper=new QueryWrapper<>();
        participantQueryWrapper.eq("mail",participant.getMail());
        Participant login_participant=participantDao.selectOne(participantQueryWrapper);
        //无此参与者
        if (login_participant==null){
            return new Result(false,"The participant is not exist!");
        }
        QueryWrapper<ParticipantConference> wrapper=new QueryWrapper<>();
        wrapper.eq("participant_id",login_participant.getId())
                .eq("conference_id",conferenceId);
        //参与者不在此会议
        if (participantConferenceDao.selectOne(wrapper)==null){
            return new Result(false,"You have not participated this conference!");
        }
        //密码不匹配
        if (!encoder.matches(participant.getPassword(),login_participant.getPassword())){
            return new Result(false,"The password is not matched!");
        }
        //干掉密码
        login_participant.setPassword("");
        return new Result(true, MessageConstant.OK,"success",login_participant);
    }

    /**
     * 方法说明
     * @author Amy
     * @date 2022/4/27 21:49
     * @description
     * @param participant
     * @return org.example.common.entity.Result
     */
    @Override
    public Result Register(Participant participant,String conferenceId) {
        //根据邮箱查询
        QueryWrapper<Participant> participantQueryWrapper=new QueryWrapper<>();
        participantQueryWrapper.eq("mail",participant.getMail());
        Participant new_participant=participantDao.selectOne(participantQueryWrapper);
        if (new_participant!=null){
            return new Result(false,"The Email has been registered!");
        }
        participant.setPassword(encoder.encode(participant.getPassword()));
        participantDao.insert(participant);
        ParticipantConference participantConference=new ParticipantConference();
        participantConference.setParticipantId(participant.getId());
        participantConference.setConferenceId(conferenceId);
        participantConferenceDao.insert(participantConference);
        return new Result(true,"You have participate the conference");
    }
}
