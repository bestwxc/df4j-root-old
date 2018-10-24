package com.df4j.base.server;

import java.util.List;

public class PageResult<T> extends MultiResult<T>{

    /**
     * 分页当前页码
     */
    private Integer pageNum = 1;

    /**
     * 分页大小
     */
    private Integer pageSize = 100;

    /**
     * 总记录数
     */
    private Integer total = 100;

    public PageResult(Integer pageNum, Integer pageSize, Integer total, List<T> result){
        super(true, result);
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
