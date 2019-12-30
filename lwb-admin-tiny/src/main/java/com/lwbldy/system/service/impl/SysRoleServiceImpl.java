package com.lwbldy.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.lwbldy.mbg.mapper.SysRoleMapper;
import com.lwbldy.mbg.model.SysRole;
import com.lwbldy.mbg.model.SysRoleExample;
import com.lwbldy.system.service.SysRoleMenuService;
import com.lwbldy.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    SysRoleMenuService sysRoleMenuService;

    @Override
    public SysRole queryById(long id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SysRole> listBrand(int pageNum, int pageSize) {
        //开启分页查询
        PageHelper.startPage(pageNum, pageSize);
        SysRoleExample sysRoleExample = new SysRoleExample();
        sysRoleExample.setOrderByClause("role_id desc");
        return sysRoleMapper.selectByExample(sysRoleExample);
    }

    @Override
    public int save(SysRole sysRole, String roleMenuIds) {
        sysRole.setCreateTime(new Date());
        int r = sysRoleMapper.insert(sysRole);
        if(r == 0){
            return r;
        }
        System.out.println("roleMenuIds:"+roleMenuIds);
        if(roleMenuIds == null || roleMenuIds.length() == 0)
            return 1;


        String[] roleMenuIdsArr = roleMenuIds.split(",");
        List<Integer> roleMenuIdList = new ArrayList<Integer>();
        for (String menuId : roleMenuIdsArr){
            roleMenuIdList.add(Integer.parseInt(menuId));
        }
        sysRoleMenuService.saveOrUpdate(sysRole.getRoleId(),roleMenuIdList);
        return r;
    }

    @Override
    public int update(SysRole sysRole, String roleMenuIds) {
        //先更新
        int r = sysRoleMapper.updateByPrimaryKey(sysRole);
        if(r == 0){
            return r;
        }
        if(roleMenuIds == null || roleMenuIds.length() == 0)
            return 1;
        String[] roleMenuIdsArr = roleMenuIds.split(",");
        List<Integer> roleMenuIdList = new ArrayList<Integer>();
        for (String menuId : roleMenuIdsArr){
            roleMenuIdList.add(Integer.parseInt(menuId));
        }
        sysRoleMenuService.saveOrUpdate(sysRole.getRoleId(),roleMenuIdList);
        return r;
    }

    @Override
    public int delete(Long roleId) {
        sysRoleMenuService.deleteByRoleId(roleId);
        return sysRoleMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    public List<SysRole> queryAll() {
        SysRoleExample sysRoleExample = new SysRoleExample();
        sysRoleExample.setOrderByClause("role_id desc");
        return sysRoleMapper.selectByExample(sysRoleExample);
    }
}
