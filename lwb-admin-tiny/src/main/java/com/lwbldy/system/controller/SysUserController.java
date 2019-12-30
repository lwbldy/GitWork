package com.lwbldy.system.controller;

import com.lwbldy.common.shiro.ShiroUtils;
import com.lwbldy.common.util.PageUtils;
import com.lwbldy.common.util.R;
import com.lwbldy.mbg.model.SysUser;
import com.lwbldy.system.service.SysRoleService;
import com.lwbldy.system.service.SysUserRoleService;
import com.lwbldy.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "系统用户管理控制器")
@Controller
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("sysuser/list")
    @RequiresPermissions("sysuser:list")
    public String list(){
        return "/system/sysuser/list";
    }


    @ApiOperation(value = "根据用户名分页查询用户")
    @PostMapping("sysuser/list")
    @ResponseBody
    @RequiresPermissions("sysuser:list")
    public R list(@RequestParam(value = "userName", defaultValue = "") String userName,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
                  @RequestParam(value = "limit", defaultValue = "15") Integer limit){
        List<SysUser> brandList = sysUserService.listBrand(userName,page,limit);
        return PageUtils.restPage(brandList);
    }

    @ApiOperation(value = "查询用户信息")
    @RequestMapping("sysuser/info/{userId}")
    @ResponseBody
    @RequiresPermissions("sysuser:info")
    public R info(@PathVariable("userId") Long userId){
        SysUser sysUser = sysUserService.queryByPrimaryKey(userId);
        List<Long> roleIds = sysUserRoleService.queryRoleIdList(userId);
        return R.ok(sysUser).put("roleIds",roleIds);
    }

    @ApiOperation(value = "保存用户信息页面")
    @GetMapping("sysuser/save")
    @RequiresPermissions("sysuser:save")
    public String save(ModelMap map){
        map.put("roleList",sysRoleService.queryAll());
        return "system/sysuser/save";
    }

    @ApiOperation(value = "保存用户信息")
    @RequestMapping("sysuser/save")
    @ResponseBody
    @RequiresPermissions("sysuser:save")
    public R save(SysUser sysUser,String[] roleIds){

        List<Long> roleIdList = new ArrayList<>();
        if(roleIds != null && roleIds.length!=0){
            for(String s:roleIds){
                roleIdList.add(Long.parseLong(s));
            }
        }
        int r = sysUserService.save(sysUser,roleIdList);
        if(r == 1){
            return R.ok("保存成功！");
        }else{
            return R.error("保存失败！");
        }
    }

    @GetMapping("sysuser/edit/{id}")
    @RequiresPermissions("sysuser:edit")
    public String update(@PathVariable("id")long id,ModelMap map){
        map.put("id",id);
        map.put("roleList",sysRoleService.queryAll());
        return "system/sysuser/edit";
    }


    @ApiOperation(value = "修改用户信息")
    @RequestMapping("sysuser/edit")
    @ResponseBody
    @RequiresPermissions("sysuser:edit")
    public R update(SysUser sysUser,String[] roleIds){
        List<Long> roleIdList = new ArrayList<>();
        if(roleIds != null && roleIds.length!=0){
            for(String s:roleIds){
                roleIdList.add(Long.parseLong(s));
            }
        }
        int r = sysUserService.update(sysUser,roleIdList);
        if(r == 1){
            return R.ok("修改成功！");
        }else{
            return R.error("修改失败！");
        }
    }

    @ApiOperation(value = "修改密码")
    @RequestMapping("sysuser/changePWD")
    @ResponseBody
    @RequiresPermissions("sysuser:changePWD")
    public R changePWD(long userId,String newPWD){
        if (sysUserService.changePWD(userId,newPWD) >= 1){
            return R.ok("修改成功");
        }else
            return R.ok("修改失败");
    }

    @ApiOperation(value = "修改自己的密码")
    @RequestMapping("sysuser/changeMyPWD")
    @ResponseBody
    public R changeMyPWD(String newPWD){
        if (sysUserService.changePWD(ShiroUtils.getUserEntity().getUserId(),newPWD) >= 1){
            return R.ok("修改成功");
        }else
            return R.ok("修改失败");
    }

}
