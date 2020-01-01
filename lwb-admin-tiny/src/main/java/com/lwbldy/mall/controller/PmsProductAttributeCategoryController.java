package com.lwbldy.mall.controller;

import com.lwbldy.common.util.PageUtils;
import com.lwbldy.common.util.R;
import com.lwbldy.mall.service.PmsProductAttributeCategoryService;
import com.lwbldy.mbg.model.PmsProductAttributeCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 产品属性分类表
 * @author lwbldy
 * @email lwbldy@163.com
 * @date 2019-12-31 10:37:32
 */
@Controller
@Api(tags = "产品属性分类")
@RequestMapping("/mall/pmsproductattributecategory")
public class PmsProductAttributeCategoryController {

	@Autowired
	private PmsProductAttributeCategoryService pmsProductAttributeCategoryService;

	@ApiOperation(value = "显示列表界面")
	@GetMapping("/list")
	@RequiresPermissions("pmsproductattributecategory:list")
	public String list(){
		return "/mall/pmsproductattributecategory/list";
	}

	@ApiOperation(value = "分页查询列表")
	@PostMapping("/list")
	@RequiresPermissions("pmsproductattributecategory:list")
	@ResponseBody
	public R list(@RequestParam(value = "page", defaultValue = "1") Integer page,
				  @RequestParam(value = "limit", defaultValue = "15") Integer limit){
		List<PmsProductAttributeCategory> brandList = pmsProductAttributeCategoryService.listBrand(page,limit);
		return PageUtils.restPage(brandList);
	}

	@ApiOperation(value = "查询用户信息")
	@GetMapping("/info/{id}")
	@RequiresPermissions("pmsproductattributecategory:info")
	@ResponseBody
	public R info(@PathVariable("id") Long id){
		PmsProductAttributeCategory entity = pmsProductAttributeCategoryService.queryByPrimaryKey(id);
		return R.ok(entity);
	}

	@ApiOperation(value = "保存信息界面")
	@GetMapping("/save")
	@RequiresPermissions("pmsproductattributecategory:save")
	public String save(ModelMap map){
		return "mall/pmsproductattributecategory/save";
	}

	@ApiOperation(value = "保存信息")
	@PostMapping("/save")
	@RequiresPermissions("pmsproductattributecategory:save")
	@ResponseBody
	public R save(PmsProductAttributeCategory entity){
		if(pmsProductAttributeCategoryService.save(entity) == 1){
			return R.ok();
		}else
			return R.error();
	}

	@ApiOperation(value = "修改信息界面")
	@GetMapping("/edit/{id}")
	@RequiresPermissions("pmsproductattributecategory:edit")
	public String update(@PathVariable("id")long id,ModelMap map){
		map.put("id",id);
		return "mall/pmsproductattributecategory/edit";
	}

	@ApiOperation(value = "修改信息")
	@PostMapping("/edit")
	@RequiresPermissions("pmsproductattributecategory:edit")
	@ResponseBody
	public R edit(PmsProductAttributeCategory entity){
		if(pmsProductAttributeCategoryService.edit(entity) == 1){
			return R.ok();
		}else
			return R.error();
	}

	@ApiOperation(value = "删除")
	@GetMapping("/delete/{id}")
	@RequiresPermissions("pmsproductattributecategory:delete")
	@ResponseBody
	public R delete(@PathVariable("id") Long id){
		int r = pmsProductAttributeCategoryService.delete(id);
		if(r != 0){
			return R.ok();
		}else{
			return R.error();
		}
	}

}
