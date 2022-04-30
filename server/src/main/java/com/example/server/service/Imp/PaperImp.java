package com.example.server.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.server.dao.PaperDao;
import com.example.server.dao.ParticipantDao;
import com.example.server.service.PaperService;
import org.example.common.entity.MessageConstant;
import org.example.common.entity.Result;
import org.example.common.po.Paper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
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
    public Result submitPaper(MultipartFile paper, String conferenceId, String participantId) {
        if (paper.isEmpty()) return new Result(false, "The file is empty!");
        QueryWrapper<Paper> wrapper = new QueryWrapper<>();
        wrapper.eq("conference_id", conferenceId)
                .eq("submitter_id", participantId);
        if (paperDao.selectCount(wrapper) >= 10)
            return new Result(false, "Submit a maximum of 10 files!");
        Paper newPaper = new Paper();
        newPaper.setCreateTime(new Date());
        newPaper.setState(0);
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
     * @author Amy
     * @date 2022/4/30 16:36
     * @description paper重传
     * @param paper
     * @param paperId
     * @return org.example.common.entity.Result
     */
    @Override
    public Result reuploadPaper(MultipartFile paper, String paperId) {
        Paper newPaper=paperDao.selectById(paperId);
        if (newPaper==null)return new Result(false,"error");
        File file=new File(newPaper.getPath());
        if (file.delete()){
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
            return new Result(true,"success");
        }else {
            return new Result(false,"error");
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
        Paper paper=paperDao.selectById(paperId);
        File file=new File(paper.getPath());
        if (file.delete()){
            paperDao.deleteById(paperId);
            return new Result(true,"delete success");
        }else {
            return new Result(false,"delete error");
        }

    }

    /**
     * 方法说明
     * @param participantId
     * @param conferenceId
     * @return org.example.common.entity.Result
     * @author Amy
     * @date 2022/4/30 12:37
     * @description 获取参与者所在会议的文件列表
     */
    @Override
    public Result getParticipantPaperList(String conferenceId,String participantId) {
        QueryWrapper<Paper> wrapper=new QueryWrapper<>();
        wrapper.eq("conference_id",conferenceId)
                .eq("submitter_id",participantId)
                .orderByDesc("create_time");
        return new Result(true,"success", MessageConstant.OK,paperDao.selectList(wrapper));
    }

    /**
     * 方法说明
     *
     * @param conferenceId
     * @return org.example.common.entity.Result
     * @author Amy
     * @date 2022/4/30 12:37
     * @description
     */
    @Override
    public Result getConferencePaperList(String conferenceId) {
        return null;
    }

    /**
     * 方法说明
     *
     * @param RefereeId
     * @return org.example.common.entity.Result
     * @author Amy
     * @date 2022/4/30 12:37
     * @description
     */
    @Override
    public Result getRefereePaperList(String RefereeId) {
        return null;
    }
}
