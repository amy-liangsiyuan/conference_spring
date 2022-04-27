package org.example.common.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Amy
 * @date 2022-04-26 20:26
 * @description Info
 * @package org.example.common.po
 * @title Info
 */
@Data
public class Info {
    private String id;
    private String title;
    private String content;
    private String conferenceId;
    @DateTimeFormat(pattern = "yyyy-MM-dd MM:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date createTime;
}
