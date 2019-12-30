package com.lwbldy.system.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.lwbldy.common.util.R;
import com.lwbldy.mbg.model.SysMenu;
import com.lwbldy.system.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Api(tags = "菜单管理控制器")
@Controller
public class SysMenuController {

    @Autowired
    SysMenuService sysMenuService;

    @GetMapping("/sysmenu/list")
    @RequiresPermissions("sysmenu:list")
    public String list(){
        return "system/menu/list";
    }

    @PostMapping("/sysmenu/list")
    @ResponseBody
    @RequiresPermissions("sysmenu:list")
    public R menuList(){
        List<SysMenu> list = sysMenuService.listAllSysMenu();
        R r = R.ok(list);
        r.put("count",list.size());
        return r;
    }

    @ApiOperation(value="跳转到添加保存菜单")
    @GetMapping("/sysmenu/save")
    @RequiresPermissions("sysmenu:save")
    public String save(){
        return "system/menu/save";
    }

    @ApiOperation(value="获取树菜单")
    @GetMapping("/sysmenu/findTreeMenu")
    @ResponseBody
    public R findTreeMenu(){
        return R.ok(sysMenuService.queryAllTreeMenu());
    }

    @ApiOperation(value="保存菜单")
    @PostMapping("/sysmenu/save")
    @ResponseBody
    @RequiresPermissions("sysmenu:save")
    public R saveMenu(SysMenu sysMenu){
        if(sysMenuService.saveSysMenu(sysMenu) == 1){
            return R.ok();
        }else
            return R.error();
    }

    @ApiOperation(value="获取菜单实体")
    @GetMapping("/sysmenu/info/{id}")
    @ResponseBody
    @RequiresPermissions("sysmenu:info")
    public R info(@PathVariable("id") long id){
        SysMenu sysMenu = sysMenuService.queryById(id);
        return R.ok().put("sysMenu",sysMenu);
    }

    @ApiOperation(value="修改菜单")
    @GetMapping("/sysmenu/edit/{id}")
    @RequiresPermissions("sysmenu:edit")
    public String edit(@PathVariable("id")long id,ModelMap map){
        map.put("id",id);
        return "system/menu/edit";
    }

    @ApiOperation(value="修改菜单")
    @PostMapping("/sysmenu/edit")
    @ResponseBody
    @RequiresPermissions("sysmenu:edit")
    public R edit(SysMenu sysMenu){
        int r = sysMenuService.update(sysMenu);
        if(r == 1){
            return R.ok("修改成功");
        }else{
            return R.error("修改失败");
        }
    }

    @ApiOperation(value="查询所有菜单提供给角色中的权限树使用")
    @GetMapping("/sysmenu/fundAll")
    @ResponseBody
    public List<SysMenu> fundAll(){
        return sysMenuService.listAllSysMenu();
    }

}
