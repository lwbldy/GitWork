package com.lwbldy.system.service;

import java.util.List;

public interface SysUserRoleService {
    /**
     * 保存或更新用户角色
     * @param userId
     * @param roleIdList
     */
    void saveOrUpdate(Long userId, List<Long> roleIdList);

    /**
     * 根据用户ID，获取角色ID列表
     */
    List<Long> queryRoleIdList(Long userId);
}
