package com.lwbldy.system.service;

import java.util.List;

public interface SysRoleMenuService {

    /**
     * 保存或更新 角色权限
     * @param roleId
     * @param menuIdList
     */
    void saveOrUpdate(Long roleId, List<Integer> menuIdList);

    /**
     * 根据角色ID，获取菜单ID列表
     * @param roleId
     * @return
     */
    List<Integer> queryMenuIdList(long roleId);

    /**
     * 根据 角色ID 删除权限
     * @param roleId
     * @return
     */
    int deleteByRoleId(Long roleId);

}
