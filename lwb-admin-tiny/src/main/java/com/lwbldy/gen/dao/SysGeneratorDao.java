package com.lwbldy.gen.dao;

import java.util.List;
import java.util.Map;

/**
 * 代码自动生成
 */
public interface SysGeneratorDao {

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

}
