package com.example.server.service;

import org.example.common.entity.Result;
import org.example.common.po.Menu;

import java.util.List;

/**
 * @author Amy
 * @date 2022-04-22 13:25
 * @description
 * @package com.example.server.service
 * @title
 */
public interface MenuService {
     Result updateMenu(List<Menu> menuList);
     Result getMenu(String id);
}
