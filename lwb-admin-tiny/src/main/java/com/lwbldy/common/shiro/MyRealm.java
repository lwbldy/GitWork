package com.lwbldy.common.shiro;

import com.lwbldy.mbg.model.SysMenu;
import com.lwbldy.mbg.model.SysUser;
import com.lwbldy.system.service.SysMenuService;
import com.lwbldy.system.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 自定义 拦截器
 */
public class MyRealm extends AuthorizingRealm {

    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysMenuService sysMenuService;

    /**
     * 授权(验证权限时调用)
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUser user = (SysUser) principals.getPrimaryPrincipal();
        Long userId = user.getUserId();
        List<String> permsList = null;

        //系统管理员，拥有最高权限
        if(userId == 1){
            List<SysMenu> menuList = sysMenuService.listAllSysMenu();
            permsList = new ArrayList<String>(menuList.size());
            //获得所有权限
            for(SysMenu menu : menuList){
                permsList.add(menu.getPerms());
            }
        }else {
            permsList = sysUserService.queryAllPerms(userId);
        }

        //用户权限列表
        Set<String> permsSet = new HashSet<String>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }


    /**
     * 认证(登录时调用)
     * @param authcToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authcToken;
        String userNam = token.getUsername();
        SysUser user = sysUserService.queryByName(userNam);

        //账号不存在
        if(user == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }

        if(user.getStatus() == null || user.getStatus() == 0){
            throw new LockedAccountException("用户被锁定");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
        return info;
    }

    //init-method 配置.
    public void setCredentialMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(ShiroUtils.hashAlgorithmName);//MD5算法加密
        credentialsMatcher.setHashIterations(ShiroUtils.hashIterations);//1024次循环加密
        setCredentialsMatcher(credentialsMatcher);
    }


    //用来测试的算出密码password盐值加密后的结果，下面方法用于新增用户添加到数据库操作的，我这里就直接用main获得，直接数据库添加了，省时间
    public static void main(String[] args) {

    }

    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        shaCredentialsMatcher.setHashAlgorithmName(ShiroUtils.hashAlgorithmName);
        shaCredentialsMatcher.setHashIterations(ShiroUtils.hashIterations);
        super.setCredentialsMatcher(shaCredentialsMatcher);
    }

}
