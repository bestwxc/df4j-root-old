package com.df4j.base.server;

import java.util.List;

public class PageResult<T> extends MultiResult<T>{

    /**
     * 分页当前页码
     */
    private Integer pageNo = 1;

    /**
     * 分页大小
     */
    private Integer pageSize = 100;

    /**
     * 总记录数
     */
    private Integer total = 100;

    public PageResult(Integer pageNo, Integer pageSize, Integer total, List<T> result){
        super(true, result);
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.total = total;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageNo() {
        return pageNo;
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
