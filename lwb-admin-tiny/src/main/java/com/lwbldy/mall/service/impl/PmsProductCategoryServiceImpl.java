package com.lwbldy.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.lwbldy.mbg.mapper.PmsProductCategoryMapper;
import com.lwbldy.mbg.model.PmsProductCategory;
import com.lwbldy.mbg.model.PmsProductCategoryExample;
import com.lwbldy.mall.service.PmsProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service("pmsProductCategoryService")
public class PmsProductCategoryServiceImpl implements PmsProductCategoryService {

    @Autowired
	PmsProductCategoryMapper pmsProductCategoryMapper;

    @Override
    public int save(PmsProductCategory pmsProductCategory) {
        return pmsProductCategoryMapper.insert(pmsProductCategory);
    }

    @Override
    public int edit(PmsProductCategory pmsProductCategory) {
        return pmsProductCategoryMapper.updateByPrimaryKey(pmsProductCategory);
    }

    @Override
    public PmsProductCategory queryByPrimaryKey(Long id) {
        return pmsProductCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<PmsProductCategory> listBrand(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
		PmsProductCategoryExample example = new PmsProductCategoryExample();
        example.setOrderByClause("id desc");
        return pmsProductCategoryMapper.selectByExample(example);
    }

    @Override
    public int delete(Long id) {
        return pmsProductCategoryMapper.deleteByPrimaryKey(id);
    }
}
