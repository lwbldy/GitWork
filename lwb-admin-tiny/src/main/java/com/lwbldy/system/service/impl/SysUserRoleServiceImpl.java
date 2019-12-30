package com.lwbldy.system.service.impl;

import com.lwbldy.mbg.mapper.SysUserRoleMapper;
import com.lwbldy.mbg.model.SysUserRole;
import com.lwbldy.system.dao.SysUserRoleDao;
import com.lwbldy.system.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("sysUserRoleService")
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public void saveOrUpdate(Long userId, List<Long> roleIdList) {
        //先删除原来的用户角色
        sysUserRoleDao.deleteByUserId(userId);

        if(roleIdList == null || roleIdList.size() == 0){
            return;
        }
        //再保存用户角色
        List<SysUserRole> sysUserRoles = new ArrayList<SysUserRole>();
        for(long roleid : roleIdList){
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(roleid);
            sysUserRoles.add(sysUserRole);
        }
        sysUserRoleDao.saveBatch(sysUserRoles);
    }

    @Override
    public List<Long> queryRoleIdList(Long userId) {
        return sysUserRoleDao.queryRoleIdList(userId);
    }
}
