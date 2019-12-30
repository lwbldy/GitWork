package com.lwbldy.system.dao;

import com.lwbldy.mbg.model.SysUserRole;

import java.util.List;

/**
 * 用户与角色对应关系
 */
public interface SysUserRoleDao {

    /**
     * 根据用户ID，获取角色ID列表
     */
    List<Long> queryRoleIdList(Long userId);

    /**
     * 根据用户ID 删除角色
     * @param userId 用户ID
     * @return
     */
    int deleteByUserId(Long userId);

    //保存多行数据
    int saveBatch(List<SysUserRole> list);

}
