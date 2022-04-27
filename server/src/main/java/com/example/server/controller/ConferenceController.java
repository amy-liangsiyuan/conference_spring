package com.example.server.controller;


import com.example.server.annotation.LoginRequired;
import com.example.server.service.ConferenceService;
import com.example.server.service.InfoService;
import com.example.server.service.MenuService;
import org.example.common.entity.MessageConstant;
import org.example.common.entity.QueryPage;
import org.example.common.entity.Result;
import org.example.common.po.Conference;
import org.example.common.po.Info;
import org.example.common.po.Menu;
import org.example.common.po.User;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Amy
 * @date 2022-03-16 18:32
 * @description
 * @package org.example.server.controller
 * @title
 */
@RestController
@RequestMapping("/conference")
public class ConferenceController {
    @Resource
    ConferenceService conferenceService;
    @Resource
    MenuService menuService;
    @Resource
    InfoService infoService;

    @GetMapping("/getRecommendList")
    public Result getRecommendList() {
        return new Result(true, "getList success！", MessageConstant.OK, conferenceService.getRecommendList());
    }

    @GetMapping("/getConference{id}")
    public Result getConference(@PathVariable String id){
       return conferenceService.getConference(id);
    }

    @GetMapping("/getMyConference")
    @LoginRequired
    public Result getMyConference(HttpServletRequest request) {
        User user = (User) request.getAttribute("currentUser");
        return new Result(true, "getList success！", MessageConstant.OK, conferenceService.getMyConference(user));
    }

    @PostMapping("/addConference")
    @LoginRequired
    public Result addConference(@RequestBody Conference conference, HttpServletRequest request) {
        User user = (User) request.getAttribute("currentUser");
        if (conferenceService.add_Conference(conference, user))
            return new Result(true, "success！", MessageConstant.OK);
        return new Result(false, "the conference already exist！", MessageConstant.ERROR);
    }

    @PostMapping("/updateConference")
    @LoginRequired
    public Result updateConference(@RequestBody Conference conference) {
        if (conferenceService.update_Conference(conference))
            return new Result(true, "success！", MessageConstant.OK);
        return new Result(false, "error！", MessageConstant.ERROR);
    }

    @PostMapping("/searchPage")
    public Result getPage(@RequestBody QueryPage queryPage) {
        return new Result(true, "success", MessageConstant.OK, conferenceService.getPage(queryPage));
    }

    @PostMapping("/updatePage")
    public  Result updatePage(@RequestBody List<Menu> Tree){
        return menuService.updateMenu(Tree);
    }

    @GetMapping("/findPage{id}")
    public Result getPage(@PathVariable String id){
        return menuService.getMenu(id);
    }

    @GetMapping("/changeState{id}")
    public Result changeState(@PathVariable String id){
        System.out.println(id);
        return conferenceService.changeState(id);
    }

    @PostMapping("/updateInfo")
    public Result updateInfo(@RequestBody List<Info> infoList){
        return infoService.updateInfo(infoList);
    }

    @GetMapping("/getInfoList{id}")
    public Result getInfoList(@PathVariable String id){
        return infoService.getInfoList(id);
    }
}
