package org.example.common.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Amy
 * @date 2022-04-28 14:54
 * @description PaperPo
 * @package org.example.common.po
 * @title PaperPo
 */
@Data
public class Paper {
    private String id;
    private String name;//original name
    private String pathName;
    private String path;
    private String url;
    @DateTimeFormat(pattern = "yyyy-MM-dd MM:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date createTime;
    private Integer state;
    private String refereeId;
    private String refereeName;
    private String submitterId;
    private String submitterName;
    private String conferenceId;
}
