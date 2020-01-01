package com.lwbldy.mall.service;

import com.lwbldy.mbg.model.PmsProductCategory;
import java.util.List;

/**
 * 产品分类 service
 * @author lwbldy
 * @email lwbldy@163.com
 * @date 2019-12-31 16:39:41
 */
public interface PmsProductCategoryService {

    /**
    * 保存
    * @return
    */
    int save(PmsProductCategory pmsProductCategory);

    /**
     * 更新
     * @return
     */
    int edit(PmsProductCategory pmsProductCategory);

    /**
     * 根据主键查询
     * @return
     */
     PmsProductCategory queryByPrimaryKey(Long id);

    /**
     * 根据分页查询
     * @return
     */
    List<PmsProductCategory> listBrand(int pageNum, int pageSize);

    /**
     * 根据ID删除
     * @param id
     * @return
     */
    int delete(Long id);

}
