package com.example.server.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.server.dao.InfoDao;
import com.example.server.service.InfoService;
import org.example.common.entity.MessageConstant;
import org.example.common.entity.Result;
import org.example.common.po.Info;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Amy
 * @date 2022-04-26 20:28
 * @description
 * @package com.example.server.service.Imp
 *
 */
@Service
public class InfoImp implements InfoService {
    @Resource
    InfoDao infoDao;

    /**
     * 方法说明
     * @author Amy
     * @date 2022/4/26 20:59
     * @description
     * @param infoList
     * @return org.example.common.entity.Result
     */
    @Override
    public Result updateInfo(List<Info> infoList) {
        if (infoList.isEmpty())return new Result(false,"error");
        String conferenceId=infoList.get(0).getConferenceId();
        QueryWrapper<Info> wrapper=new QueryWrapper<>();
        wrapper.eq("conference_id",conferenceId);
        infoDao.delete(wrapper);
        for (Info info : infoList) {
            infoDao.insert(info);
        }
        return new Result(true,"success");
    }
    
    /**
     * 方法说明
     * @author Amy
     * @date 2022/4/26 21:45
     * @description 获取一个会议的通知列表并且以时间顺序排序
     * @param id
     * @return org.example.common.entity.Result
     */
    @Override
    public Result getInfoList(String id) {
        QueryWrapper<Info> wrapper=new QueryWrapper<>();
        wrapper.eq("conference_id",id).orderByAsc("create_time");
        List<Info> infoList=infoDao.selectList(wrapper);
        return new Result(true,"success", MessageConstant.OK,infoList);
    }
}
