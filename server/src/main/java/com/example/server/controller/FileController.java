package com.example.server.controller;

import com.example.server.annotation.LoginRequired;
import com.example.server.service.ConferenceService;
import com.example.server.service.UserService;
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
        userService.updateAvatar(url,user);
        return new Result(true,MessageConstant.OK,"upload success!",url);
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
        conferenceService.updateFirstPicture(url,request.getHeader("conferenceId"));
        return new Result(true,MessageConstant.OK,"upload success!",url);
    }
    @DeleteMapping("delete_firstPicture")
    @LoginRequired
    public Result deleteFirstPicture(HttpServletRequest request) {
        if (conferenceService.updateFirstPicture("", request.getHeader("conferenceId")))
            return new Result(true, "success!");
        else return new Result(false, "error!");
    }

}
