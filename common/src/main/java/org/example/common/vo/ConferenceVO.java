package org.example.common.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Amy
 * @date 2022-03-16 20:46
 * @description
 * @package org.example.common.vo
 * @title
 */
@Data
public class ConferenceVO {
    private String id;
    private String name;
    private String city;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Timestamp createTime;
}
