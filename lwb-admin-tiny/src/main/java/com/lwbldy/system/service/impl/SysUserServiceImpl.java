package com.lwbldy.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.lwbldy.common.shiro.ShiroUtils;
import com.lwbldy.mbg.mapper.SysUserMapper;
import com.lwbldy.mbg.model.SysUser;
import com.lwbldy.mbg.model.SysUserExample;
import com.lwbldy.system.dao.SysUserDao;
import com.lwbldy.system.service.SysUserRoleService;
import com.lwbldy.system.service.SysUserService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysUserDao sysUserDao;

    @Autowired
    SysUserRoleService sysUserRoleService;

    @Override
    public List<String> queryAllPerms(Long userId) {
        return sysUserDao.queryAllPerms(userId);
    }

    @Override
    public SysUser queryByName(String name) {
        SysUserExample sysUserExample = new SysUserExample();
        SysUserExample.Criteria criteria = sysUserExample.createCriteria();
        criteria.andUsernameEqualTo(name);
        List<SysUser> list = sysUserMapper.selectByExample(sysUserExample);
        if(list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<SysUser> listBrand(String userName,int pageNum, int pageSize) {
        //开启分页查询
        PageHelper.startPage(pageNum, pageSize);
        SysUserExample sysUserExample = new SysUserExample();
        sysUserExample.setOrderByClause("user_id desc");
        // 条件查询
        SysUserExample.Criteria criteria = sysUserExample.createCriteria();
        if (!StringUtils.isEmpty(userName)){
            criteria.andUsernameLike("%" + userName + "%");
        }
        return sysUserMapper.selectByExample(sysUserExample);
    }

    @Override
    public SysUser queryByPrimaryKey(Long userId) {
        return sysUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public int save(SysUser sysUser, List<Long> roleIds) {
        sysUser.setCreateTime(new Date());

        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        sysUser.setSalt(salt);
        sysUser.setPassword(ShiroUtils.sha256(sysUser.getPassword(), sysUser.getSalt()));
        int r = sysUserMapper.insert(sysUser);
        if(r != 1){
            return 0;
        }
        //保存用户角色
        sysUserRoleService.saveOrUpdate(sysUser.getUserId(),roleIds);
        return r;
    }

    @Override
    public int update(SysUser sysUser, List<Long> roleIds) {
        sysUser.setSalt(null);
        sysUser.setPassword(null);
        int r = sysUserMapper.updateByPrimaryKeySelective(sysUser);

        //保存用户角色
        sysUserRoleService.saveOrUpdate(sysUser.getUserId(),roleIds);
        return 1;
    }

    @Override
    public int changePWD(long userId, String newPWD) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        String salt = RandomStringUtils.randomAlphanumeric(20);
        sysUser.setSalt(salt);
        sysUser.setPassword(ShiroUtils.sha256(newPWD, sysUser.getSalt()));
        return sysUserMapper.updateByPrimaryKeySelective(sysUser);
    }

}
