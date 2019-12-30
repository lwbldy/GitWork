package com.lwbldy.system.dao;

import java.util.List;
import java.util.Map;

public interface SysRoleMenuDao {

    /**
     * 根据map保存角色菜单对象
     * @param map
     */
    void saveByMap(Map<String, Object> map);

    /**
     * 根据角色id删除菜单
     * @param roleId
     * @return
     */
    int deleteByRoleId(long roleId);

    /**
     * 根据角色 id 查询菜单id
     * @param roleId
     * @return
     */
    List<Integer> queryMenuIdList(long roleId);

}
