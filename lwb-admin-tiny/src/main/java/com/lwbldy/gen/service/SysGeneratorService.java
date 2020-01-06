package com.lwbldy.gen.service;

import com.lwbldy.common.util.R;
import com.lwbldy.gen.vo.TableConfVO;

import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月19日 下午3:33:38
 */
public interface SysGeneratorService {

    /**
     * 获取所有表
     * @param map
     * @return
     */
    List<Map<String, Object>> queryList(Map<String, Object> map);

    /**
     * 统计表张数
     * @param map
     * @return
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 获取表的描述信息
     * @param tableName
     * @return
     */
    Map<String, String> queryTable(String tableName);

    /**
     * 获取表的列信息
     * @param tableName
     * @return
     */
    List<Map<String, String>> queryColumns(String tableName);

    /**
     * 生成代码
     */
//    byte[] generatorCode(String[] tableNames);

    /**
     *  自定义生成代码
     * @param tableNames 表名
     * @param packageName 包名
     * @param author 作者
     * @param email 邮箱
     * @param tablePrefix 表前缀
     * @param modelName 模块名称
     * @return
     */
//    byte[] generatorCode(String[] tableNames,String packageName,String author,String email,String tablePrefix,String modelName);

    /**
     * 自定义生成代码
     * @param table 表格配置信息
     * @return
     */
    R generatorCode(TableConfVO table);


    /**
     * 根据 加载默认的配置类
     * @param tableName
     * @return
     */
    TableConfVO initTableInfo(String tableName);

}
