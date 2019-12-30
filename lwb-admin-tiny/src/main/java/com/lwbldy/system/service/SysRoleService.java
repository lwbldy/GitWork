package com.lwbldy.system.service;

import com.lwbldy.mbg.model.SysRole;

import java.util.List;

public interface SysRoleService {

    /**
     * 根据Id查询角色
     * @param id
     * @return
     */
    SysRole queryById(long id);

    /**
     * 分页查询角色
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<SysRole> listBrand(int pageNum, int pageSize);

    /**
     * 保存角色
     * @param sysRole
     * @param roleMenuIds
     * @return
     */
    int save(SysRole sysRole, String roleMenuIds);

    /**
     * 更新角色
     * @param sysRole
     * @param roleMenuIds
     * @return
     */
    int update(SysRole sysRole, String roleMenuIds);


    /**
     * 根据id删除目录
     * @param roleId
     * @return
     */
    int delete(Long roleId);

    /**
     * 查询所有角色
     * @return
     */
    List<SysRole> queryAll();

}
