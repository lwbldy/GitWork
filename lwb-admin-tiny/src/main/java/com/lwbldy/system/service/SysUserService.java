package com.lwbldy.system.service;

import com.github.pagehelper.PageHelper;
import com.lwbldy.mbg.model.SysUser;
import com.lwbldy.mbg.model.SysUserExample;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 系统用户service
 */
public interface SysUserService {
    /**
     * 查询用户的所有权限
     * @param userId  用户ID
     */
    List<String> queryAllPerms(Long userId);

    /**
     * 根据用户名查询用户信息
     * @param name
     * @return
     */
    SysUser queryByName(String name);

    /**
     * 按条件分页查询系统用户
     * @param userName
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<SysUser> listBrand(String userName,int pageNum, int pageSize);

    /**
     * 根据用户id查询系统用户
     * @param userId
     * @return
     */
    SysUser queryByPrimaryKey(Long userId);

    int save(SysUser sysUser,List<Long> roleIds);

    int update(SysUser sysUser,List<Long> roleIds);

    /**
     * 修改密码
     * @param userId
     * @param newPWD
     * @return
     */
    int changePWD(long userId,String newPWD);

}
