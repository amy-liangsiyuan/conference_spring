package org.example.common.po;


import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
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
    private Integer state1;
    private Integer state2;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String refereeId1;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String refereeId2;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String refereeName1;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String refereeName2;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String submitterId;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String submitterName;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String conferenceId;
    private Integer refereeNum;
}
