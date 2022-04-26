package org.example.common.po;

import lombok.Data;

import java.util.List;

/**
 * @author Amy
 * @date 2022-04-22 13:11
 * @description menu
 * @package org.example.common.po
 * @title menu
 */

@Data
public class Menu {
    //菜单id
    private String id;
    private int level;
    private int sort;
    private String label;
    private int childrenNum;
    private String content;
    private String conferenceId;
    private String pid;
    private List<Menu> children;
}
