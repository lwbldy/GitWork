package com.lwbldy.system.controller;

import com.lwbldy.common.util.PageUtils;
import com.lwbldy.common.util.R;
import com.lwbldy.mbg.model.SysRole;
import com.lwbldy.mbg.model.SysUser;
import com.lwbldy.system.service.SysRoleMenuService;
import com.lwbldy.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "角色管理控制器")
@Controller
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @ApiOperation(value = "跳转到列表")
    @GetMapping("sysrole/list")
    @RequiresPermissions("sysrole:list")
    public String list(){
        return "system/sysrole/list";
    }


    @ApiOperation(value = "分页查询角色")
    @PostMapping("sysrole/list")
    @ResponseBody
    @RequiresPermissions("sysrole:list")
    public R list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                  @RequestParam(value = "limit", defaultValue = "15") Integer limit){
        List<SysRole> brandList = sysRoleService.listBrand(page,limit);
        return PageUtils.restPage(brandList);
    }

    @ApiOperation(value = "跳转到添加角色页面")
    @GetMapping("sysrole/save")
    @RequiresPermissions("sysrole:save")
    public String save(){
        return "system/sysrole/save";
    }

    @ApiOperation(value = "添加角色")
    @PostMapping("sysrole/save")
    @ResponseBody
    @RequiresPermissions("sysrole:save")
    public R save(SysRole sysRole,String roleMenuIds){
        int r = sysRoleService.save(sysRole,roleMenuIds);
        if(r != 0){
            return R.ok("保存成功");
        }else{
            return R.error("保存失败");
        }
    }

    @RequestMapping("sysrole/fundAll")
    @ResponseBody
    @ApiOperation(value = "查询所有角色，供用户选择角色")
    public R fundAll(){
        List list = sysRoleService.queryAll();
        return R.ok("成功").put("list",list);
    }

    @ApiOperation(value="修改角色")
    @GetMapping("sysrole/edit/{id}")
    @RequiresPermissions("sysrole:edit")
    public String edit(@PathVariable("id")long id,ModelMap map){
        map.put("id",id);
        return "system/sysrole/edit";
    }

    @ApiOperation(value="修改角色")
    @PostMapping("sysrole/edit")
    @ResponseBody
    @RequiresPermissions("sysrole:edit")
    public R edit(SysRole sysRole,String roleMenuIds){
        int r = sysRoleService.update(sysRole,roleMenuIds);
        if(r != 0){
            return R.ok("修改成功");
        }else{
            return R.error("修改失败");
        }
    }

    @GetMapping("sysrole/delete/{roleId}")
    @ResponseBody
    @RequiresPermissions("sysrole:delete")
    public R delete(@PathVariable("roleId") Long roleId){
        int r = sysRoleService.delete(roleId);
        if(r != 0){
            return R.ok("删除成功");
        }else{
            return R.error("删除失败");
        }
    }

    /**
     * 信息
     */
    @RequestMapping("sysrole/info/{roleId}")
    @ResponseBody
    @RequiresPermissions("sysrole:info")
    public R info(@PathVariable("roleId") Long roleId){
        SysRole sysRole = sysRoleService.queryById(roleId);
        //获取权限菜单
        List<Integer> rolMenuList = sysRoleMenuService.queryMenuIdList(roleId);
        return R.ok("成功").put("sysRole", sysRole).put("roleList",rolMenuList);
    }

}
