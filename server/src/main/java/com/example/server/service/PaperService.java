package com.example.server.service;

import org.example.common.entity.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Amy
 * @date 2022-04-30 12:26
 * @description
 * @package com.example.server.service.Imp
 * @title
 */
public interface PaperService {
    //参与者论文列表获取接口
    Result getParticipantPaperList(String conferenceId,String participantId);

    //会议论文获取接口
    Result getConferencePaperList(String conferenceId);

    //审核人员论文获取接口
    Result getRefereePaperList(String conferenceId,String RefereeId);

    //论文提交接口
    Result submitPaper(MultipartFile paper,String conferenceId,String participantId,String url);

    //论文重传接口
    Result reuploadPaper(MultipartFile paper,String paperId,String url);

    //论文删除接口
    Result deletePaper(String paperId);

    //设置为reviewing
    Result setReviewing(String paperId,String RefereeId);

    //设为UnReview
    Result setUnReviewing(String paperId,String refereeId);

    //修改状态
    Result setPaperState(String id,Integer state);

    Result refereeChangeState(String id,String refereeId,Integer state);


}
