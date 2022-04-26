package org.example.common.vo;

import lombok.Data;

/**
 * @author Amy
 * @date 2022-04-22 13:48
 * @description
 * @package org.example.common.vo
 * @title
 */
@Data
public class MenuRecord {
    private String id;
    private int level;
    private int sort;
    private String pid;
    private String label;
    private int childrenNum;
    private String content;
    private String conferenceId;
}
