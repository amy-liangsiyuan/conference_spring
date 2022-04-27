package org.example.common.po;

import lombok.Data;

/**
 * @author Amy
 * @date 2022-04-27 21:41
 * @description participant
 * @package org.example.common.po
 * @title participant
 */
@Data
public class Participant {
    private String id;
    private String name;
    private String mail;
    private String phone;
    private String password;
}
