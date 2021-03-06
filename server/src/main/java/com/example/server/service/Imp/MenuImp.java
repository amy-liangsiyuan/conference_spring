package com.example.server.service.Imp;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.server.dao.ConferenceDao;
import com.example.server.dao.MenuDao;
import com.example.server.service.MenuService;
import org.example.common.entity.MessageConstant;
import org.example.common.entity.Result;
import org.example.common.po.Menu;
import org.example.common.vo.MenuRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Amy
 * @date 2022-04-22 13:25
 * @description
 * @package com.example.server.service.Imp
 * @title
 */

@Service
public class MenuImp implements MenuService {
    @Resource
    MenuDao menuDao;
    /**
     * 方法说明
     * @author Amy
     * @date 2022/4/28 14:02
     * @description 用于更新用户菜单列表
     * @param menuList
     * @return org.example.common.entity.Result
     */
    @Override
    public Result updateMenu(List<Menu> menuList) {
        if (menuList.size() == 0) return new Result(false, "The Menu could not be empty!");
        String conferenceId = menuList.get(0).getConferenceId();
        QueryWrapper<MenuRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("conference_id", conferenceId);
        menuDao.delete(wrapper);
        for (Menu menu : menuList) {
            this.getChildren(menu);
        }
        return new Result(true, "success");
    }
    
    /**
     * 方法说明
     * @author Amy
     * @date 2022/4/28 14:02
     * @description 内部掉用函数，用于重构数据库查询记录为Tree结构并返回
     * @param menu
     * @return void
     */
    private void getChildren(Menu menu) {
        MenuRecord menuRecord = new MenuRecord();
        BeanUtils.copyProperties(menu, menuRecord);
        menuDao.insert(menuRecord);
        if (menu.getChildrenNum() == 0) return;
        for (Menu child : menu.getChildren()) {
            this.getChildren(child);
        }
    }
    /**
     * 方法说明
     * @author Amy
     * @date 2022/4/28 14:03
     * @description 获取对应会议的菜单列表
     * @param id
     * @return org.example.common.entity.Result
     */
    @Override
    public Result getMenu(String id) {
        QueryWrapper<MenuRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("conference_id", id)
                .orderByAsc("level")
                .orderByAsc("sort");
        List<MenuRecord> menuRecords = menuDao.selectList(wrapper);
        List<Menu> rootList=new ArrayList<>();
        List<Menu> menuList=new ArrayList<>();

        for (MenuRecord menuRecord : menuRecords) {
            Menu menu=new Menu();
            BeanUtils.copyProperties(menuRecord,menu);
            if (menu.getPid()==null) rootList.add(menu);
            else menuList.add(menu);
        }
        for (Menu menu : rootList) {
            this.setChildren(menu, menuList);
        }
        return new Result(true, MessageConstant.OK, "success",rootList);
    }
    /**
     * 方法说明
     * @author Amy
     * @date 2022/4/28 14:03
     * @description 内部掉用算法，用于重构树形结构
     * @param menu
 * @param menuList
     * @return org.example.common.po.Menu
     */
    private Menu setChildren(Menu menu,List<Menu> menuList) {
        List<Menu> childrenMenus=new ArrayList<>();
        for (Menu menuNode : menuList) {
            if (menuNode.getPid().equals(menu.getId()))
                childrenMenus.add(setChildren(menuNode,menuList));
        }
        menu.setChildren(childrenMenus);
        return menu;
    }
}
