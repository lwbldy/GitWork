package com.lwbldy.system.service;

import com.lwbldy.mbg.model.SysMenu;
import com.lwbldy.system.vo.MenuTreeVO;
import com.lwbldy.system.vo.MenuVO;

import java.util.List;

/**
 * 系统菜单service
 */
public interface SysMenuService {

    /**
     * 查询所有菜单
     */
    List<SysMenu> listAllSysMenu();

    /**
     * 根据用户id获取菜单
     * @param userId 用户id
     * @return
     */
    MenuVO queryMenu(Long userId);

    /**
     * 查询所有树状菜单
     * @return
     */
    List<MenuTreeVO> queryAllTreeMenu();

    /**
     * 保存系统菜单
     * @param sysMenu
     * @return
     */
    int saveSysMenu(SysMenu sysMenu);

    /**
     * 根据id获取菜单信息
     * @param id 菜单id
     * @return
     */
    SysMenu queryById(long id);

    /**
     * 修改菜单信息
     * @param sysMenu
     * @return
     */
    int update(SysMenu sysMenu);
}
