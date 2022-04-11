package org.example.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname QueryPageBean
 * @Description 封装查询条件
 * @Version 1.0.0
 * @Date 2021/12/2 15:37
 * @Created by Amy
 */

@Data
public class QueryPage{
    private Integer currentPage;    //页码
    private Integer pageSize;   //每页记录数
    private String queryString; //查询条件

}
