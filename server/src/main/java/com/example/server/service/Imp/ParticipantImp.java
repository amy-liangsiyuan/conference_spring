package com.example.server.service.Imp;

import ch.qos.logback.core.net.QueueFactory;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.server.dao.PaperDao;
import com.example.server.dao.ParticipantConferenceDao;
import com.example.server.dao.ParticipantDao;
import com.example.server.service.ParticipantService;
import org.example.common.entity.MessageConstant;
import org.example.common.entity.Result;
import org.example.common.po.Paper;
import org.example.common.po.Participant;
import org.example.common.po.ParticipantConference;
import org.example.common.vo.ParticipantVo;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    @Resource
    PaperDao paperDao;

    /**
     * 方法说明
     *
     * @param participant
     * @return org.example.common.entity.Result
     * @author Amy
     * @date 2022/4/27 21:48
     * @description
     */
    @Override
    public Result Login(Participant participant, String conferenceId) {
        //根据邮箱查询
        QueryWrapper<Participant> participantQueryWrapper = new QueryWrapper<>();
        participantQueryWrapper.eq("mail", participant.getMail());
        Participant login_participant = participantDao.selectOne(participantQueryWrapper);
        //无此参与者
        if (login_participant == null) {
            return new Result(false, "The participant is not exist!");
        }
        QueryWrapper<ParticipantConference> wrapper = new QueryWrapper<>();
        wrapper.eq("participant_id", login_participant.getId())
                .eq("conference_id", conferenceId);
        ParticipantConference participantConference = participantConferenceDao.selectOne(wrapper);
        //参与者不在此会议
        if (participantConference == null) {
            return new Result(false, "You have not participated this conference!");
        }
        //用户被禁用
        if (participantConference.getState() == 0) {
            return new Result(false, "Your account is disabled! Please contact the administrator.");
        }
        //密码不匹配
        if (!encoder.matches(participant.getPassword(), login_participant.getPassword())) {
            return new Result(false, "The password is not matched!");
        }
        //干掉密码
        login_participant.setPassword("");
        return new Result(true, MessageConstant.OK, "success", login_participant);
    }

    /**
     * 方法说明
     *
     * @param participant
     * @return org.example.common.entity.Result
     * @author Amy
     * @date 2022/4/27 21:49
     * @description
     */
    @Override
    public Result Register(Participant participant, String conferenceId) {
        //根据邮箱查询用户注册是否合法
        QueryWrapper<Participant> participantQueryWrapper = new QueryWrapper<>();
        participantQueryWrapper.eq("mail", participant.getMail());
        //查询此邮箱用户
        Participant new_participant = participantDao.selectOne(participantQueryWrapper);
        if (new_participant != null) {
            return new Result(false, "The Email has been registered!");
        }
        participant.setPassword(encoder.encode(participant.getPassword()));
        participantDao.insert(participant);
        ParticipantConference participantConference = new ParticipantConference();
        participantConference.setParticipantId(participant.getId());
        participantConference.setConferenceId(conferenceId);
        //注册成功后登入新秀，暂时禁用
        participantConference.setState(0);
        participantConference.setReviewing(0);
        participantConferenceDao.insert(participantConference);
        return new Result(true, "You have registered successfully, please wait for the administrator's review.");
    }

    /**
     * 方法说明
     *
     * @param id
     * @return org.example.common.entity.Result
     * @author Amy
     * @date 2022/4/29 17:10
     * @description
     */
    @Override
    public Result getParticipantsList(String id) {
        List<ParticipantVo> participantList = new ArrayList<>();
        QueryWrapper<ParticipantConference> wrapper = new QueryWrapper<>();
        wrapper.eq("conference_id", id);
        for (ParticipantConference participantConference : participantConferenceDao.selectList(wrapper)) {
            ParticipantVo participantVo = new ParticipantVo();
            BeanUtils.copyProperties(participantDao.selectById(participantConference.getParticipantId()), participantVo);
            participantVo.setState(participantConference.getState());
            participantVo.setReviewing(participantConference.getReviewing());
            participantList.add(participantVo);
        }
        return new Result(true, "success", MessageConstant.OK, participantList);
    }

    /**
     * 方法说明
     *
     * @param conference_id
     * @param participant_id
     * @return org.example.common.entity.Result
     * @author Amy
     * @date 2022/4/29 18:22
     * @description
     */
    @Override
    public Result changeState(String conference_id, String participant_id) {
        QueryWrapper<ParticipantConference> participantConferenceQueryWrapper = new QueryWrapper<>();
        participantConferenceQueryWrapper.eq("conference_id", conference_id)
                .eq("participant_id", participant_id);
        ParticipantConference participantConference = participantConferenceDao.selectOne(participantConferenceQueryWrapper);
        participantConference.setState(participantConference.getState() == 1 ? 0 : 1);
        participantConferenceDao.updateById(participantConference);
        return new Result(true, "Change success!");
    }

    /**
     * 方法说明
     *
     * @param conference_id
     * @param participant_id
     * @return org.example.common.entity.Result
     * @author Amy
     * @date 2022/4/29 18:22
     * @description
     */
    @Override
    public Result changeReferee(String conference_id, String participant_id) {
        QueryWrapper<ParticipantConference> participantConferenceQueryWrapper = new QueryWrapper<>();
        participantConferenceQueryWrapper.eq("conference_id", conference_id)
                .eq("participant_id", participant_id);
        ParticipantConference participantConference = participantConferenceDao.selectOne(participantConferenceQueryWrapper);
        participantConference.setReviewing(participantConference.getReviewing() == 1 ? 0 : 1);
        participantConferenceDao.updateById(participantConference);
        return new Result(true, "Change success!");
    }

    @Override
    public Result checkReferee(String conferenceId, String participantId) {
        QueryWrapper<ParticipantConference> wrapper = new QueryWrapper<>();
        wrapper.eq("conference_id", conferenceId)
                .eq("participant_id", participantId);
        if (participantConferenceDao.selectOne(wrapper).getReviewing() == 1) return new Result(true, "success");
        else return new Result(false,"Not Referee");
    }
}
