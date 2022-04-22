package org.example.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Classname Result
 * @Description2
 * @Version 1.0.0
 * @Date 2021/12/2 15:38
 * @Created by Amy
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Result {
    private Boolean flag;
    private String code;
    private String message;
    private Object data;

    public Result(Boolean flag, String message, String code) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Result(Boolean flag, String message) {
        this.flag = flag;
        this.message = message;
    }

}
