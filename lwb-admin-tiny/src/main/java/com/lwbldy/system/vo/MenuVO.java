package com.lwbldy.system.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuVO {

    private Long menuId;
    private String title;//显示的标题
    private String icon;//显示的图标
    private String href;//链接
    private Long parentId;
    private String target = "_self";// 在哪里打开
    private List<MenuVO> child;

}
