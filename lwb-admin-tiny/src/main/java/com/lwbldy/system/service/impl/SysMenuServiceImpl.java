package com.lwbldy.system.service.impl;

import com.lwbldy.mbg.mapper.SysMenuMapper;
import com.lwbldy.mbg.model.SysMenu;
import com.lwbldy.mbg.model.SysMenuExample;
import com.lwbldy.system.dao.SysMenuDao;
import com.lwbldy.system.service.SysMenuService;
import com.lwbldy.system.vo.MenuTreeVO;
import com.lwbldy.system.vo.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    SysMenuMapper sysMenuMapper;

    @Autowired
    SysMenuDao sysMenuDao;

    @Override
    public List<SysMenu> listAllSysMenu() {
        return sysMenuMapper.selectByExample(new SysMenuExample());
    }

    @Override
    public MenuVO queryMenu(Long userId) {
        //查询出所有的菜单，转换成
        List<SysMenu> rootMenu = sysMenuDao.selectMenuByUserId(userId);

        //转换成MenuVO
        List<MenuVO> rootMenuVOList = new ArrayList<MenuVO>();
        for(SysMenu sysMenu : rootMenu){
            MenuVO menuVO = new MenuVO();
            menuVO.setHref(sysMenu.getUrl());
            menuVO.setParentId(sysMenu.getParentId());
            menuVO.setIcon(sysMenu.getIcon());
            menuVO.setTitle(sysMenu.getName());
            menuVO.setMenuId(sysMenu.getMenuId());
            menuVO.setTarget(sysMenu.getTager());
            rootMenuVOList.add(menuVO);
        }

        // 先找到所有的一级菜单
        List<MenuVO> menuList = new ArrayList<>();
        for(MenuVO sysMenu : rootMenuVOList){
            if(sysMenu.getParentId() == 0){
                menuList.add(sysMenu);
            }
        }

        //为所有一菜单设置子菜单
        for (MenuVO menu : menuList) {
            menu.setChild(getChild(menu.getMenuId(),rootMenuVOList));
        }
        //返回json字符串
        MenuVO rootMenuVo = new MenuVO();
        rootMenuVo.setTitle("root");
        rootMenuVo.setChild(menuList);
        return rootMenuVo;
    }

    public List<MenuVO> getChild(long id,List<MenuVO> rootMenu){
        List<MenuVO> childList = new ArrayList<MenuVO>();
        //循环所有菜单
        for(MenuVO menu:rootMenu){
            //如果是和父id相同
            if(menu.getParentId() == id){
                childList.add(menu);
            }
        }
        //再循环一遍 子菜单，如果有值，则继续循环下去。如果没有值返回空
        for(MenuVO menu : childList){
            getChild(menu.getMenuId(),rootMenu);
        }
        if(childList.size() == 0){
            return null;
        }
        return childList;
    }

    @Override
    public List<MenuTreeVO> queryAllTreeMenu() {
        SysMenuExample sysMenuExample = new SysMenuExample();
        SysMenuExample.Criteria criteria = sysMenuExample.createCriteria();
        //菜单不等于2
        criteria.andTypeNotEqualTo(2);

        //查询所有
        List<SysMenu> rootMenu = sysMenuMapper.selectByExample(sysMenuExample);

        //先找到所有的一级菜单
        List<MenuTreeVO> menuList = new ArrayList<MenuTreeVO>();
        for(SysMenu sysMenu : rootMenu){
            if(sysMenu.getParentId() == 0){
                MenuTreeVO treeVO = new MenuTreeVO();
                treeVO.setId(sysMenu.getMenuId());
                treeVO.setTitle(sysMenu.getName());
                treeVO.setParentId(sysMenu.getParentId());
                menuList.add(treeVO);
            }
        }

        for (MenuTreeVO menu : menuList) {
            menu.setChildren(getTreeChildren(menu.getId(),rootMenu));
        }
        return menuList;
    }

    @Override
    public int saveSysMenu(SysMenu sysMenu) {
        return sysMenuMapper.insert(sysMenu);
    }

    @Override
    public SysMenu queryById(long id) {
        return sysMenuMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(SysMenu sysMenu) {
        return sysMenuMapper.updateByPrimaryKey(sysMenu);
    }

    private List<MenuTreeVO> getTreeChildren(long id,List<SysMenu> rootMenu){
        List<MenuTreeVO> childList = new ArrayList<MenuTreeVO>();

        //循环所有菜单
        for(SysMenu menu:rootMenu){
            //如果是和父id相同
            if(menu.getParentId() == id){
                MenuTreeVO treeVO = new MenuTreeVO();
                treeVO.setId(menu.getMenuId());
                treeVO.setTitle(menu.getName());
                treeVO.setParentId(menu.getParentId());
                childList.add(treeVO);
            }
        }
        if(childList.size() == 0){
            return null;
        }
        return childList;
    }
}
