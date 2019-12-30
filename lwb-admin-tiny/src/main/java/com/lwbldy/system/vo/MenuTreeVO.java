package com.lwbldy.system.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 树状菜单
 */
@Getter
@Setter
public class MenuTreeVO {

    private String title;
    private Long id;
    private Long parentId;
    private List<MenuTreeVO> children;

}
