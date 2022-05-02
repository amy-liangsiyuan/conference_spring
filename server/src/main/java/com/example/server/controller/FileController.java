package com.example.server.controller;

import com.example.server.annotation.LoginRequired;
import com.example.server.service.ConferenceService;
import com.example.server.service.PaperService;
import com.example.server.service.UserService;
import com.netflix.ribbon.proxy.annotation.Http;
import org.example.common.entity.MessageConstant;
import org.example.common.entity.Result;
import org.example.common.po.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Amy
 * @date 2022-03-26 23:07
 * @description
 * @package com.example.server.controller
 * @title
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    UserService userService;

    @Resource
    ConferenceService conferenceService;

    @Resource
    PaperService paperService;

    @RequestMapping(value = "/add_avatar")
    @LoginRequired
    public Result uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        if (file.isEmpty())
            return new Result(false, "the file is empty！", MessageConstant.ERROR);
        //获取文件名
        User user = (User) request.getAttribute("currentUser");
        String fileName = file.getOriginalFilename();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //设置文件路径
        String filePath = System.getProperty("user.dir") + "/File/avatar/";
        //设置新的文件名
        fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/avatar/" + fileName;
        userService.updateAvatar(url, user);
        return new Result(true, MessageConstant.OK, "upload success!", url);
    }

    @DeleteMapping("delete_avatar")
    @LoginRequired
    public Result deleteAvatar(HttpServletRequest request) {
        User user = (User) request.getAttribute("currentUser");
        if (userService.updateAvatar("", user))
            return new Result(true, "success!");
        else return new Result(false, "error!");
    }

    @RequestMapping(value = "/add_firstPicture")
    @LoginRequired
    public Result uploadFirst(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        if (file.isEmpty())
            return new Result(false, "the file is empty！", MessageConstant.ERROR);
        //获取文件名
        String fileName = file.getOriginalFilename();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //设置文件路径
        String filePath = System.getProperty("user.dir") + "/File/firstPicture/";
        //设置新的文件名
        fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/firstPicture/" + fileName;
        conferenceService.updateFirstPicture(url, request.getHeader("conferenceId"));
        return new Result(true, MessageConstant.OK, "upload success!", url);
    }

    @DeleteMapping("delete_firstPicture")
    @LoginRequired
    public Result deleteFirstPicture(HttpServletRequest request) {
        if (conferenceService.updateFirstPicture("", request.getHeader("conferenceId")))
            return new Result(true, "success!");
        else return new Result(false, "error!");
    }

    @PostMapping("/submitPaper{conferenceId}/{participantId}")
    public Result submitPaper(@RequestParam("file") MultipartFile paper, @PathVariable String conferenceId, @PathVariable String participantId, HttpServletRequest request) {
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/paper/";
        return paperService.submitPaper(paper, conferenceId, participantId, url);
    }

    @PostMapping("/reuploadPaper{participantId}")
    public Result reuploadPaper(@RequestParam("file") MultipartFile paper, @PathVariable String participantId, HttpServletRequest request) {
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/paper/";
        return paperService.reuploadPaper(paper, participantId, url);
    }

    @GetMapping("/getParticipantPaperList{conferenceId}/{participantId}")
    public Result getParticipantPaperList(@PathVariable String conferenceId, @PathVariable String participantId) {
        return paperService.getParticipantPaperList(conferenceId, participantId);
    }

    @DeleteMapping("/deletePaper{paperId}")
    public Result deletePaper(@PathVariable String paperId) {
        return paperService.deletePaper(paperId);
    }

    @GetMapping("/getConferencePapers{conferenceId}")
    public Result getConferencePapers(@PathVariable String conferenceId) {
        return paperService.getConferencePaperList(conferenceId);
    }

    @GetMapping("/getRefereePapers{conferenceId}/{refereeId}")
    public Result getRefereePapers(@PathVariable String conferenceId, @PathVariable String refereeId) {
        return paperService.getRefereePaperList(conferenceId, refereeId);
    }

    @GetMapping("/setReviewing{paperId}/{refereeId}")
    public Result setReviewing(@PathVariable String paperId, @PathVariable String refereeId){
        return paperService.setReviewing(paperId,refereeId);
    }

    @GetMapping("/setUnReviewing{paperId}")
    public Result setUnReviewing(@PathVariable String paperId){
        return paperService.setUnReviewing(paperId);
    }

    @GetMapping("/setPaperState{id}/{state}")
    public Result setPaperState(@PathVariable String id, @PathVariable Integer state){
        return paperService.setPaperState(id,state);
    }
}
