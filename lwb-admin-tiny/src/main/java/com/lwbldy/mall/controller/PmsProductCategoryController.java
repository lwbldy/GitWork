package com.lwbldy.mall.controller;

import com.lwbldy.common.util.PageUtils;
import com.lwbldy.common.util.R;
import com.lwbldy.mall.service.PmsProductCategoryService;
import com.lwbldy.mbg.model.PmsProductCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@Api(tags = "产品分类")
@RequestMapping("/mall/pmsProductCategory")
public class PmsProductCategoryController {

    @Autowired
    private PmsProductCategoryService pmsProductCategoryService;

    @ApiOperation(value = "显示列表界面")
    @GetMapping("/list")
    @RequiresPermissions("pmsProductCategory:list")
    public String list(){
        return "/mall/pmsproductcategory/list";
    }

    @ApiOperation(value = "分页查询列表")
    @PostMapping("/list")
    @RequiresPermissions("pmsProductCategory:list")
    @ResponseBody
    public R list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                  @RequestParam(value = "limit", defaultValue = "15") Integer limit){
        List<PmsProductCategory> brandList = pmsProductCategoryService.listBrand(page,limit);
        return PageUtils.restPage(brandList);
    }

    @ApiOperation(value = "查询用户信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("pmsProductCategory:info")
    @ResponseBody
    public R info(@PathVariable("id") Long id){
        PmsProductCategory entity = pmsProductCategoryService.queryByPrimaryKey(id);
        return R.ok(entity);
    }

    @ApiOperation(value = "保存信息界面")
    @GetMapping("/save")
    @RequiresPermissions("pmsProductCategory:save")
    public String save(ModelMap map){
        return "/mall/pmsproductcategory/save";
    }

    @ApiOperation(value = "保存信息")
    @PostMapping("/save")
    @RequiresPermissions("pmsProductCategory:save")
    @ResponseBody
    public R save(PmsProductCategory entity){
        if(pmsProductCategoryService.save(entity) == 1){
            return R.ok();
        }else
            return R.error();
    }

    @ApiOperation(value = "修改信息界面")
    @GetMapping("/edit/{id}")
    @RequiresPermissions("pmsProductCategory:edit")
    public String update(@PathVariable("id")Long id,ModelMap map){
        map.put("id",id);
        return "/mall/pmsproductcategory/edit";
    }

    @ApiOperation(value = "修改信息")
    @PostMapping("/edit")
    @RequiresPermissions("pmsProductCategory:edit")
    @ResponseBody
    public R edit(PmsProductCategory entity){
        if(pmsProductCategoryService.edit(entity) == 1){
            return R.ok();
        }else
            return R.error();
    }

    @ApiOperation(value = "删除")
    @GetMapping("/delete/{id}")
    @RequiresPermissions("pmsProductCategory:delete")
    @ResponseBody
    public R delete(@PathVariable("id")Long id){
        int r = pmsProductCategoryService.delete(id);
        if(r != 0){
            return R.ok();
        }else{
            return R.error();
        }
    }

}
