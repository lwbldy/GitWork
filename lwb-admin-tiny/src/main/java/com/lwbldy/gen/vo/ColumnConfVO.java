package com.lwbldy.gen.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

public class ColumnConfVO {

    @ApiModelProperty(value = "列名")
    private String columnName;

    @ApiModelProperty(value = "对应数据库的数据类型")
    private String dataType;

    @ApiModelProperty(value = "对应 java 属性类型")
    private String attrType;

    @ApiModelProperty(value = "列名备注")
    private String comments;

    @ApiModelProperty(value = "属性名称(第一个字母大写)，如：user_name => UserName")
    private String attrName;

    @ApiModelProperty(value = "属性名称(第一个字母小写)，如：user_name => userName")
    private String attrname;

    @ApiModelProperty(value = "允许为空")
    private String isNullable;

    @ApiModelProperty(value = "是否允许查询")
    private String isQuery;

    @ApiModelProperty(value = "条件查询方式，如：=，!= ,>=,<=,like")
    private String columnQuery;

    @ApiModelProperty(value = "在菜单里是否显示")
    private String isMenuShow;

    @ApiModelProperty(value = "在表单中是否显示")
    private String isFormShow;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrname() {
        return attrname;
    }

    public void setAttrname(String attrname) {
        this.attrname = attrname;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    public String getIsQuery() {
        return isQuery;
    }

    public void setIsQuery(String isQuery) {
        this.isQuery = isQuery;
    }

    public String getColumnQuery() {
        return columnQuery;
    }

    public void setColumnQuery(String columnQuery) {
        this.columnQuery = columnQuery;
    }

    public String getIsMenuShow() {
        return isMenuShow;
    }

    public void setIsMenuShow(String isMenuShow) {
        this.isMenuShow = isMenuShow;
    }

    public String getIsFormShow() {
        return isFormShow;
    }

    public void setIsFormShow(String isFormShow) {
        this.isFormShow = isFormShow;
    }
}
