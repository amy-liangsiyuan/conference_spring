package org.example.common.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Classname PageResult
 * @Description2
 * @Version 1.0.0
 * @Date 2021/12/2 15:36
 * @Created by Amy
 */

@Data
public class PageResult {

    private List<Map<String, Object>> records;

    private Integer total;

}
