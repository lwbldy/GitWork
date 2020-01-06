package com.lwbldy.gen.service.impl;

//import com.alibaba.fastjson.JSONObject;
import com.lwbldy.common.util.R;
import com.lwbldy.gen.dao.SysGeneratorDao;
import com.lwbldy.gen.service.SysGeneratorService;
import com.lwbldy.gen.util.GenUtils;
import com.lwbldy.gen.vo.TableConfVO;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

@Service("sysGeneratorService")
public class SysGeneratorServiceImpl implements SysGeneratorService {

    @Resource
    private SysGeneratorDao sysGeneratorDao;

    @Override
    public List<Map<String, Object>> queryList(Map<String, Object> map) {
        return sysGeneratorDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysGeneratorDao.queryTotal(map);
    }

    @Override
    public Map<String, String> queryTable(String tableName) {
        return sysGeneratorDao.queryTable(tableName);
    }

    @Override
    public List<Map<String, String>> queryColumns(String tableName) {
        return sysGeneratorDao.queryColumns(tableName);
    }

    @Override
    public R generatorCode(TableConfVO table) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        GenUtils.generatorCode(table,zip);
        IOUtils.closeQuietly(zip);

        //写入到本地文件中
        OutputStream out = null;
        try {
            out = new FileOutputStream("D:\\code.zip");
            out.write(outputStream.toByteArray());
            out.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return R.ok("文件以及保存到：D:\\code.zip");
    }

//    @Override
//    public byte[] generatorCode(String[] tableNames) {
//        return generatorCode(tableNames,null,null,null,null,null);
//    }
//
//    @Override
//    public byte[] generatorCode(String[] tableNames,String packageName,String author,String email,String tablePrefix,String modelName) {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        ZipOutputStream zip = new ZipOutputStream(outputStream);
//        for(String tableName : tableNames){
//            //查询表信息
//            Map<String, String> table = queryTable(tableName);
//            //查询列信息
//            List<Map<String, String>> columns = queryColumns(tableName);
//
//            //System.out.println("--->"+JSONObject.toJSONString(table));
//            // {"engine":"InnoDB","createTime":1553589750000,"tableComment":"菜单管理","tableName":"sys_menu"}
////            System.out.println("-2-->"+JSONObject.toJSONString(columns));
//            //[{"dataType":"bigint","extra":"auto_increment","columnComment":"","columnKey":"PRI","columnName":"menu_id"},{"dataType":"bigint","extra":"","columnComment":"父菜单ID，一级菜单为0","columnKey":"","columnName":"parent_id"},{"dataType":"varchar","extra":"","columnComment":"菜单名称","columnKey":"","columnName":"name"},{"dataType":"varchar","extra":"","columnComment":"菜单URL","columnKey":"","columnName":"url"},{"dataType":"varchar","extra":"","columnComment":"授权(多个用逗号分隔，如：user:list,user:create)","columnKey":"","columnName":"perms"},{"dataType":"int","extra":"","columnComment":"类型   0：目录   1：菜单   2：按钮","columnKey":"","columnName":"type"},{"dataType":"varchar","extra":"","columnComment":"菜单图标","columnKey":"","columnName":"icon"},{"dataType":"int","extra":"","columnComment":"排序","columnKey":"","columnName":"order_num"}]
//
//            //生成代码
//            GenUtils.generatorCode(table, columns, zip,packageName,author,email,tablePrefix,modelName);
//        }
//        IOUtils.closeQuietly(zip);
//        return outputStream.toByteArray();
//
//        return null;
//    }

    @Override
    public TableConfVO initTableInfo(String tableName) {
        //查询表信息
        Map<String, String> tableInfo = queryTable(tableName);
        //查询列信息
        List<Map<String, String>> tableColInfo = queryColumns(tableName);

        return GenUtils.initTableConfVO(tableInfo,tableColInfo);
    }


}
