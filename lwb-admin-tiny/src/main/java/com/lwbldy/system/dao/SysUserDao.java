package com.lwbldy.system.dao;

import java.util.List;

public interface SysUserDao {

    /**
     * 根据用户id查找权限
     * @param userId
     * @return
     */
    List<String> queryAllPerms(Long userId);

}
