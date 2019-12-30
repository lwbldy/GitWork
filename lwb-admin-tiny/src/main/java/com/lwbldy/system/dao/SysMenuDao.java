package com.lwbldy.system.dao;

import com.lwbldy.mbg.model.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单管理
 */
public interface SysMenuDao{

    List<SysMenu> selectMenuByUserId(@Param(value = "userId") Long userId);

}
