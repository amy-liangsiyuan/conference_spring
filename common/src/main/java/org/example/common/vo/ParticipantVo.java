package org.example.common.vo;

import lombok.Data;

/**
 * @author Amy
 * @date 2022-04-29 17:38
 * @description
 * @package org.example.common.vo
 * @title
 */
@Data
public class ParticipantVo {
    private String id;
    private String name;
    private String mail;
    private String phone;
    private String password;
    private Integer state;
    private Integer reviewing;
}
