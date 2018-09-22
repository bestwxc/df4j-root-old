package net.df.base.server;

import net.df.base.constants.ResultType;
import java.util.List;

/**
 * 多结果集
 * @param <T>
 */
public class MultiResult<T> extends Result<List<T>>{

    private Integer pageNo = 1;
    private Integer pageSize = 100;
    private Integer total = 100;

    public MultiResult(){
        super(ResultType.LIST);
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

    @Override
    public List<T> getResult() {
        return super.getResult();
    }

    @Override
    public void setResult(List<T> result) {
        super.setResult(result);
    }
}
