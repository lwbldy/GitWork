package com.lwbldy.system.service.impl;

import com.lwbldy.system.dao.SysRoleMenuDao;
import com.lwbldy.system.dao.SysUserRoleDao;
import com.lwbldy.system.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl implements SysRoleMenuService {


    @Autowired
    SysRoleMenuDao sysRoleMenuDao;

    @Override
    public void saveOrUpdate(Long roleId, List<Integer> menuIdList) {
        //先删除角色与菜单关系
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("role_id",roleId);
        sysRoleMenuDao.deleteByRoleId(roleId);
        if(menuIdList.size() == 0){
            return ;
        }



        Map<String, Object> map = new HashMap<String, Object>();
        map.put("roleId", roleId);
        map.put("menuIdList", menuIdList);

        System.out.println("保存多少个："+map.size());

        sysRoleMenuDao.saveByMap(map);
    }

    @Override
    public List<Integer> queryMenuIdList(long roleId) {
        return sysRoleMenuDao.queryMenuIdList(roleId);
    }

    @Override
    public int deleteByRoleId(Long roleId) {
        return sysRoleMenuDao.deleteByRoleId(roleId);
    }
}
