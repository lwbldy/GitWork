package com.lwbldy.gen.util;

import com.lwbldy.common.exception.RRException;
import com.lwbldy.common.util.DateUtils;
import com.lwbldy.gen.entity.ColumnEntity;
import com.lwbldy.gen.entity.TableEntity;
import com.lwbldy.gen.vo.ColumnConfVO;
import com.lwbldy.gen.vo.TableConfVO;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器   工具类
 */
public class GenUtils {

    /**
     * 需要生成的模板
     * @return
     */
    public static List<String> getTemplates(){
        List<String> templates = new ArrayList<String>();
//        templates.add("template/Entity.java.vm");
//        templates.add("template/Dao.java.vm");
//        templates.add("template/Dao.xml.vm");
        templates.add("template/Service.java.vm");
        templates.add("template/ServiceImpl.java.vm");
        templates.add("template/Controller.java.vm");
        templates.add("template/list.jsp.vm");
        templates.add("template/edit.jsp.vm");
        templates.add("template/save.jsp.vm");
////        templates.add("template/list.js.vm");
        templates.add("template/menu.sql.vm");
        return templates;
    }

    /**
     * 初始化配置信息
     * @param table 表数据
     * @param columns 表列数据
     * @return
     */
    public static TableConfVO initTableConfVO(Map<String, String> table,
                                       List<Map<String, String>> columns){
        //配置信息
        Configuration config = getConfig();
        TableConfVO tableEntity = new TableConfVO();
        tableEntity.setTableName(table.get("tableName"));
        tableEntity.setComments(table.get("tableComment"));

        tableEntity.setAuthor(config.getString("author"));
        tableEntity.setPackageName(config.getString("package"));
        tableEntity.setEmail(config.getString("email"));
        tableEntity.setModelName(config.getString("modelName"));

        //表名转换成Java类名
        String className = tableToJava(tableEntity.getTableName(), config.getString("tablePrefix"));
        tableEntity.setClassName(className);

        tableEntity.setClassname(StringUtils.uncapitalize(className));
        //获取列信息
        List<ColumnConfVO> columsList = new ArrayList<ColumnConfVO>();

        for(Map<String, String> column : columns){
            ColumnConfVO columnEntity = new ColumnConfVO();
            columnEntity.setColumnName(column.get("columnName"));
            columnEntity.setDataType(column.get("dataType"));
            columnEntity.setComments(column.get("columnComment"));
            columnEntity.setIsNullable(column.get("is_nullable"));

            //列名转换成Java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setAttrName(attrName);
            columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = config.getString(columnEntity.getDataType(), "unknowType");
            columnEntity.setAttrType(attrType);

            //是否主键
            if("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null){

                ColumnConfVO pri = new ColumnConfVO();
                pri.setColumnName(columnEntity.getColumnName());
                pri.setDataType(columnEntity.getDataType());
                pri.setComments(columnEntity.getComments());
                pri.setIsNullable(columnEntity.getIsNullable());
                pri.setAttrName(columnEntity.getAttrName());
                pri.setAttrname(columnEntity.getAttrname());
                pri.setAttrType(columnEntity.getAttrType());
                tableEntity.setPk(pri);
            }
            columsList.add(columnEntity);
        }

        //设置
        tableEntity.setColumns(columsList);

        //没主键，则第一个字段为主键
        if(tableEntity.getPk() == null){
            ColumnConfVO columnEntity = tableEntity.getColumns().get(0);
            ColumnConfVO pri = new ColumnConfVO();
            pri.setColumnName(columnEntity.getColumnName());
            pri.setDataType(columnEntity.getDataType());
            pri.setComments(columnEntity.getComments());
            pri.setIsNullable(columnEntity.getIsNullable());
            pri.setAttrName(columnEntity.getAttrName());
            pri.setAttrname(columnEntity.getAttrname());
            pri.setAttrType(columnEntity.getAttrType());
            tableEntity.setPk(pri);
        }
        return tableEntity;
    }

    /**
     * 获取配置信息
     */
    public static Configuration getConfig(){
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (ConfigurationException e) {
            throw new RRException("获取配置文件失败，", e);
        }
    }

    /**
     * 将表名称转换成表名称
     * @param tableName
     * @param tablePrefix
     * @return
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if(StringUtils.isNotBlank(tablePrefix)){
            tableName = tableName.replace(tablePrefix, "");
        }
        return columnToJava(tableName);
    }

    /**
     * 列名转换成Java属性名
     * @param columnName
     * @return
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'})
                .replace("_", "");
    }

    /**
     * 代码生成配置生成二进制文件
     * @param tableConfVO
     * @param zip
     */
    public static void generatorCode(TableConfVO tableConfVO,ZipOutputStream zip){
        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        //封装模板数据
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tableName", tableConfVO.getTableName());
        map.put("comments", tableConfVO.getComments());
        map.put("pk", tableConfVO.getPk());
        map.put("className", tableConfVO.getClassName());
        map.put("classname", tableConfVO.getClassname());
        map.put("pathName", tableConfVO.getClassname().toLowerCase());
        map.put("columns", tableConfVO.getColumns());
        map.put("package", tableConfVO.getPackageName());
        map.put("email", tableConfVO.getEmail());
        map.put("author", tableConfVO.getAuthor());
        map.put("modelName", tableConfVO.getModelName());
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));

        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getTemplates();

        for(String template : templates){
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);
            try {
                //添加到zip
                zip.putNextEntry(new ZipEntry(getFileName(template, tableConfVO.getClassName(), tableConfVO.getPackageName(),tableConfVO.getModelName())));

                System.out.println("模板内容："+sw.toString());

                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new RRException("渲染模板失败，表名：" + tableConfVO.getTableName(), e);
            }
        }


    }

    /**
     * 获取文件名
     * @param template
     * @param className
     * @param packageName
     * @param modelName
     * @return
     */
    public static String getFileName(String template, String className, String packageName,String modelName){
        String packagePath = "main" + File.separator + "java" + File.separator;
        if(StringUtils.isNotBlank(packageName)){
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }

        if(template.contains("Entity.java.vm")){
            return packagePath + "entity" + File.separator + className + "Entity.java";
        }

        if(template.contains("Dao.java.vm")){
            return packagePath + "dao" + File.separator + className + "Dao.java";
        }

        if(template.contains("Dao.xml.vm")){
            return packagePath + "dao" + File.separator + className + "Dao.xml";
        }

        if(template.contains("Service.java.vm")){
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if(template.contains("ServiceImpl.java.vm")){
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if(template.contains("Controller.java.vm")){
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if(template.contains("list.jsp.vm")){
            return "main" + File.separator + "webapp" + File.separator + "WEB-INF" + File.separator + "jsp"
                    + File.separator + modelName + File.separator + className.toLowerCase() + File.separator + "list.jsp";
        }

        if(template.contains("edit.jsp.vm")){
            return "main" + File.separator + "webapp" + File.separator + "WEB-INF" + File.separator + "jsp"
                    + File.separator + modelName + File.separator + className.toLowerCase() + File.separator + "edit.jsp";
        }

        if(template.contains("save.jsp.vm")){
            return "main" + File.separator + "webapp" + File.separator + "WEB-INF" + File.separator + "jsp"
                    + File.separator + modelName + File.separator + className.toLowerCase() + File.separator + "save.jsp";
        }

//        if(template.contains("list.js.vm")){
//            return "main" + File.separator + "webapp" + File.separator + "statics" + File.separator + "js" + File.separator + "modules" + File.separator + "generator" + File.separator + className.toLowerCase() + ".js";
//        }

        if(template.contains("menu.sql.vm")){
            return className.toLowerCase() + ".sql";
        }

        return null;
    }


}
