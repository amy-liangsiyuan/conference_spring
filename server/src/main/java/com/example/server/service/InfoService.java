package com.example.server.service;

import org.example.common.entity.Result;
import org.example.common.po.Info;

import java.util.List;

/**
 * @author Amy
 * @date 2022-04-26 20:28
 * @description service
 * @package com.example.server.service
 * @title service
 */

public interface InfoService {
    /**
     * 方法说明
     * @author Amy
     * @date 2022/4/26 21:13
     * @description 
     * @param infoList
     * @return org.example.common.entity.Result
     */
    Result updateInfo(List<Info> infoList);
    
    /**
     * 方法说明
     * @author Amy
     * @date 2022/4/26 21:16
     * @description 获取对应会议的列表
     * @param id
     * @return org.example.common.entity.Result
     */
    Result getInfoList(String id);
}
