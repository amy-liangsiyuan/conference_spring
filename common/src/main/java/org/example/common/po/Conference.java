package org.example.common.po;

import com.baomidou.mybatisplus.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class Conference {
    //id
    @TableId(value = "id")
    private String id;

    //会议名称
    private String name;

    //创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Timestamp createTime;

    //结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Timestamp dropTime;

    //举办城市
    private String city;

    //详细地址
    private String address;

    //手机号码
    private String phone;

    //电子邮箱
    private String mail;

    //会议规模
    private int peopleNum;

    //单位
    private String department;

    //首图
    private String firstPicture;

    //描述
    private String description;

    //状态
    private int state;

    //语言
    private String language;

    //会议拥有者id
    private String ownerId;

    //会议拥有者姓名
    private String ownerName;

}
