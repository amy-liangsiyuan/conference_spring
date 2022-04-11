package org.example.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @Classname RequestLog
 * @Description 请求日志记录的参数
 * @Version 1.0.0
 * @Date 2021/12/2 15:38
 * @Created by Amy
 */

@Data
@AllArgsConstructor
@ToString
public class RequestLog {
    private String url;
    private String ip;
    private String classMethod;
    private Object[] args;
}
