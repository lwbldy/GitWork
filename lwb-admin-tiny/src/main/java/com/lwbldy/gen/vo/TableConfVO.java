package com.lwbldy.gen.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class TableConfVO {

    @ApiModelProperty(value = "表名")
    private String tableName;
    @ApiModelProperty(value = "作者名称")
    private String author;

    @ApiModelProperty(value = "模块名称")
    private String modelName;

    @ApiModelProperty(value = "包名")
    private String packageName;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "去除的前缀")
    private String tablePrefix;

    @ApiModelProperty(value = "表说明")
    private String comments;

    @ApiModelProperty(value = "类名(第一个字母大写)，如：sys_user => SysUser")
    private String className;

    @ApiModelProperty(value = "路径名称(全部小写)，如：sys_user => sysuser")
    private String pathName;

    @ApiModelProperty(value = "类名(第一个字母小写)，如：sys_user => sysUser")
    private String classname;

    @ApiModelProperty(value = "表的主键")
    private ColumnConfVO pk;

    @ApiModelProperty(value = "表的列名(不包含主键)")
    private List<ColumnConfVO> columns;


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public ColumnConfVO getPk() {
        return pk;
    }

    public void setPk(ColumnConfVO pk) {
        this.pk = pk;
    }

    public List<ColumnConfVO> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnConfVO> columns) {
        this.columns = columns;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }
}
