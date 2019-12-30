package com.lwbldy.common.util;

import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 分页工具类
 */
public class PageUtils<T> {

    //总记录数
    private long totalCount;
    //每页记录数
    private int pageSize;
    //总页数
    private int totalPage;
    //当前页数
    private int currPage;
    //列表数据
    private List<T> list;

    public PageUtils(){}

    /**
     * 将PageHelper分页后的list转为分页信息
     */
    public static <T> R restPage(List<T> list) {
//        PageUtils<T> result = new PageUtils<T>();
        PageInfo<T> pageInfo = new PageInfo<T>(list);
//        result.setTotalPage(pageInfo.getPages());
//        result.setCurrPage(pageInfo.getPageNum());
//        result.setPageSize(pageInfo.getPageSize());
//        result.setTotalCount(pageInfo.getTotal());
//        result.setList(pageInfo.getList());
        R r = new R();
        r.put("count",pageInfo.getTotal());
        r.put("data",pageInfo.getList());
        return r;
    }

    /**
     * 将SpringData分页后的list转为分页信息
     */
    public static <T> PageUtils<T> restPage(Page<T> pageInfo) {
        PageUtils<T> result = new PageUtils<T>();
        result.setTotalPage(pageInfo.getTotalPages());
        result.setCurrPage(pageInfo.getNumber());
        result.setPageSize(pageInfo.getSize());
        result.setTotalCount(pageInfo.getTotalElements());
        result.setList(pageInfo.getContent());
        return result;
    }


    /**
     * 分页
     * @param list        列表数据
     * @param totalCount  总记录数
     * @param pageSize    每页记录数
     * @param currPage    当前页数
     */
    public PageUtils(List<T> list, int totalCount, int pageSize, int currPage) {
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.totalPage = (int)Math.ceil((double)totalCount/pageSize);
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
