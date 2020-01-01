package com.lwbldy.mall.service;

import com.lwbldy.mbg.model.PmsProductAttributeCategory;
import java.util.List;

/**
 * 产品属性分类表
 * @author lwbldy
 * @email lwbldy@163.com
 * @date 2019-12-31 10:37:32
 */
public interface PmsProductAttributeCategoryService {

    /**
     * 保存
     * @param pmsProductAttributeCategory
     * @return
     */
    int save(PmsProductAttributeCategory pmsProductAttributeCategory);

    /**
     * 更新
     * @param pmsProductAttributeCategory
     * @return
     */
    int edit(PmsProductAttributeCategory pmsProductAttributeCategory);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    PmsProductAttributeCategory queryByPrimaryKey(Long id);

    /**
     * 根据分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<PmsProductAttributeCategory> listBrand(int pageNum, int pageSize);

    /**
     * 根据ID删除
     * @param id
     * @return
     */
    int delete(Long id);
}
