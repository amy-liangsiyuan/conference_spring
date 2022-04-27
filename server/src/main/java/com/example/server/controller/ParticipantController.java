package com.example.server.controller;

import com.example.server.service.ParticipantService;
import org.example.common.entity.Result;
import org.example.common.po.Participant;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Amy
 * @date 2022-04-27 21:46
 * @description
 * @package com.example.server.controller
 * @title
 */
@RestController
@RequestMapping("/participant")
public class ParticipantController {
    @Resource
    ParticipantService participantService;

    @PostMapping("/login{id}")
    public Result Login(@RequestBody Participant participant, @PathVariable String id) {
        return participantService.Login(participant, id);
    }

    @PostMapping("/register{id}")
    public Result Register(@RequestBody Participant participant, @PathVariable String id) {
        return participantService.Register(participant, id);
    }
}
