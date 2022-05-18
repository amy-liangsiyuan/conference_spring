package com.example.server.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.server.dao.PaperDao;
import com.example.server.dao.ParticipantDao;
import com.example.server.service.PaperService;
import org.example.common.entity.MessageConstant;
import org.example.common.entity.Result;
import org.example.common.po.Paper;
import org.example.common.po.Participant;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.sound.midi.MetaMessage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Amy
 * @date 2022-04-30 12:27
 * @description
 * @package com.example.server.service.Imp
 * @title
 */
@Service
public class PaperImp implements PaperService {
    @Resource
    ParticipantDao participantDao;
    @Resource
    PaperDao paperDao;

    /**
     * 方法说明
     *
     * @param paper
     * @param conferenceId
     * @param participantId
     * @return org.example.common.entity.Result
     * @author Amy
     * @date 2022/4/30 12:34
     * @description 单个paper上传接口
     */
    @Override
    public Result submitPaper(MultipartFile paper, String conferenceId, String participantId, String url) {
        if (paper.isEmpty()) return new Result(false, "The file is empty!");
        QueryWrapper<Paper> wrapper = new QueryWrapper<>();
        wrapper.eq("conference_id", conferenceId)
                .eq("submitter_id", participantId);
        if (paperDao.selectCount(wrapper) >= 10)
            return new Result(false, "Submit a maximum of 10 files!");
        Paper newPaper = new Paper();
        newPaper.setCreateTime(new Date());
        newPaper.setState1(0);
        newPaper.setState2(0);
        newPaper.setState(0);
        newPaper.setRefereeNum(0);
        newPaper.setConferenceId(conferenceId);
        newPaper.setSubmitterId(participantId);
        newPaper.setSubmitterName(participantDao.selectById(participantId).getName());

        //获取文件名
        String fileName = paper.getOriginalFilename();
        newPaper.setName(fileName);

        //获取文件后缀名
        assert fileName != null;
        String suffixName = fileName.substring(fileName.lastIndexOf("."));

        //设置新文件名
        String pathName = UUID.randomUUID() + suffixName;
        newPaper.setPathName(pathName);

        //设置文件路径
        String path = System.getProperty("user.dir") + "/File/paper/" + pathName;
        newPaper.setPath(path);
        newPaper.setUrl(url + pathName);

        //创建文件
        File dest = new File(path);
        //写入文件
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            paper.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, "error!");
        }
        paperDao.insert(newPaper);
        return new Result(true, "success");
    }

    /**
     * 方法说明
     *
     * @param paper
     * @param paperId
     * @return org.example.common.entity.Result
     * @author Amy
     * @date 2022/4/30 16:36
     * @description paper重传
     */
    @Override
    public Result reuploadPaper(MultipartFile paper, String paperId, String url) {
        Paper newPaper = paperDao.selectById(paperId);
        if (newPaper == null) return new Result(false, "error");
        File file = new File(newPaper.getPath());
        if (file.delete()) {
            //获取文件名
            String fileName = paper.getOriginalFilename();
            newPaper.setName(fileName);

            //获取文件后缀名
            assert fileName != null;
            String suffixName = fileName.substring(fileName.lastIndexOf("."));

            //设置新文件名
            String pathName = UUID.randomUUID() + suffixName;
            newPaper.setPathName(pathName);

            //设置文件路径
            String path = System.getProperty("user.dir") + "/File/paper/" + pathName;
            newPaper.setPath(path);
            newPaper.setUrl(url + pathName);

            //创建文件
            File dest = new File(path);
            //写入文件
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                paper.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
                return new Result(false, "error");
            }
            paperDao.updateById(newPaper);
            return new Result(true, "success");
        } else {
            return new Result(false, "error");
        }

    }

    /**
     * 方法说明
     *
     * @param paperId
     * @return org.example.common.entity.Result
     * @author Amy
     * @date 2022/4/30 12:34
     * @description 删除制定paper
     */
    @Override
    public Result deletePaper(String paperId) {
        Paper paper = paperDao.selectById(paperId);
        File file = new File(paper.getPath());
        if (file.delete()) {
            paperDao.deleteById(paperId);
            return new Result(true, "delete success");
        } else {
            return new Result(false, "delete error");
        }

    }

    /**
     * 方法说明
     *
     * @param participantId
     * @param conferenceId
     * @return org.example.common.entity.Result
     * @author Amy
     * @date 2022/4/30 12:37
     * @description 获取参与者所在会议的文件列表
     */
    @Override
    public Result getParticipantPaperList(String conferenceId, String participantId) {
        QueryWrapper<Paper> wrapper = new QueryWrapper<>();
        wrapper.eq("conference_id", conferenceId)
                .eq("submitter_id", participantId)
                .orderByDesc("create_time");
        return new Result(true, "success", MessageConstant.OK, paperDao.selectList(wrapper));
    }

    /**
     * 方法说明
     *
     * @param conferenceId
     * @return org.example.common.entity.Result
     * @author Amy
     * @date 2022/4/30 12:37
     * @description 获取对应会议的文件列表
     */
    @Override
    public Result getConferencePaperList(String conferenceId) {
        QueryWrapper<Paper> wrapper = new QueryWrapper<>();
        wrapper.eq("conference_id", conferenceId)
                .orderByDesc("create_time");
        return new Result(true, "success", MessageConstant.OK, paperDao.selectList(wrapper));
    }

    /**
     * 方法说明
     *
     * @param conferenceId
     * @param RefereeId
     * @return org.example.common.entity.Result
     * @author Amy
     * @date 2022/5/2 11:04
     * @description
     */
    @Override
    public Result getRefereePaperList(String conferenceId, String RefereeId) {
        QueryWrapper<Paper> wrapper = new QueryWrapper<>();
        wrapper.eq("conference_id", conferenceId)
                .between("referee_num", 0, 1)
                .or().eq("referee_id1", RefereeId)
                .or().eq("referee_id2", RefereeId);
        List<Paper> paperList = paperDao.selectList(wrapper);
        return new Result(true, "success", MessageConstant.OK, paperList);
    }

    /**
     * 方法说明
     *
     * @param paperId
     * @param refereeId
     * @return org.example.common.entity.Result
     * @author Amy
     * @date 2022/5/2 13:57
     * @description
     */
    @Override
    public Result setReviewing(String paperId, String refereeId) {
        Participant referee = participantDao.selectById(refereeId);
        Paper paper = paperDao.selectById(paperId);
        if (paper.getRefereeNum() == 0) {
            paper.setState1(1);
            paper.setRefereeId1(referee.getId());
            paper.setRefereeName1(referee.getName());
            paper.setRefereeNum(1);
        } else if (paper.getRefereeNum() == 1) {
            if (paper.getRefereeId1() == null) {
                paper.setState1(1);
                paper.setRefereeId1(referee.getId());
                paper.setRefereeName1(referee.getName());
                paper.setRefereeNum(2);
            } else {
                paper.setState2(1);
                paper.setRefereeId2(referee.getId());
                paper.setRefereeName2(referee.getName());
                paper.setRefereeNum(2);
            }
        }
        paperDao.updateById(paper);
        return new Result(true, "success");
    }

    /**
     * 方法说明
     *
     * @param paperId
     * @return org.example.common.entity.Result
     * @author Amy
     * @date 2022/5/2 14:24
     * @description
     */
    @Override
    public Result setUnReviewing(String paperId, String refereeId) {
        Paper paper = paperDao.selectById(paperId);
        if (paper.getRefereeId1() != null && paper.getRefereeId1().equals(refereeId)) {
            paper.setRefereeId1(null);
            paper.setRefereeName1(null);
            paper.setState1(0);
            paper.setRefereeNum(paper.getRefereeNum() - 1);
        } else if (paper.getRefereeId2().equals(refereeId)) {
            paper.setRefereeId2(null);
            paper.setRefereeName2(null);
            paper.setState2(0);
            paper.setRefereeNum(paper.getRefereeNum() - 1);
        }
        paperDao.updateById(paper);
        return new Result(true, "success");
    }

    /**
     * 方法说明
     *
     * @param id
     * @param state
     * @return org.example.common.entity.Result
     * @author Amy
     * @date 2022/5/2 16:06
     * @description
     */
    @Override
    public Result setPaperState(String id, Integer state) {
        Paper paper = paperDao.selectById(id);
        paper.setState(state);
        paperDao.updateById(paper);
        return new Result(true, "success");
    }

    /**
     * 方法说明
     *
     * @param id
     * @param refereeId
     * @param state
     * @return org.example.common.entity.Result
     * @author Amy
     * @date 2022/5/18 18:05
     * @description
     */
    @Override
    public Result refereeChangeState(String id, String refereeId, Integer state) {
        Paper paper = paperDao.selectById(id);
        if (paper.getRefereeId1() != null && paper.getRefereeId1().equals(refereeId)) {
            paper.setState1(state);
        } else if (paper.getRefereeId2().equals(refereeId)) {
            paper.setState2(state);
        }
        paperDao.updateById(paper);
        return new Result(true, "success");
    }
}
