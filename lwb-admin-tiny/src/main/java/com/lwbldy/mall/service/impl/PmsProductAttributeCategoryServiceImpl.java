package com.lwbldy.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.lwbldy.mbg.mapper.PmsProductAttributeCategoryMapper;
import com.lwbldy.mbg.model.PmsProductAttributeCategory;
import com.lwbldy.mbg.model.PmsProductAttributeCategoryExample;
import com.lwbldy.mall.service.PmsProductAttributeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service("pmsProductAttributeCategoryService")
public class PmsProductAttributeCategoryServiceImpl implements PmsProductAttributeCategoryService {

    @Autowired
    PmsProductAttributeCategoryMapper pmsProductAttributeCategoryMapper;

    @Override
    public int save(PmsProductAttributeCategory pmsProductAttributeCategory) {
        return pmsProductAttributeCategoryMapper.insert(pmsProductAttributeCategory);
    }

    @Override
    public int edit(PmsProductAttributeCategory pmsProductAttributeCategory) {
        return pmsProductAttributeCategoryMapper.updateByPrimaryKey(pmsProductAttributeCategory);
    }

    @Override
    public PmsProductAttributeCategory queryByPrimaryKey(Long id) {
        return pmsProductAttributeCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<PmsProductAttributeCategory> listBrand(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductAttributeCategoryExample example = new PmsProductAttributeCategoryExample();
        example.setOrderByClause("id desc");
        return pmsProductAttributeCategoryMapper.selectByExample(example);
    }

    @Override
    public int delete(Long id) {
        return pmsProductAttributeCategoryMapper.deleteByPrimaryKey(id);
    }
}
