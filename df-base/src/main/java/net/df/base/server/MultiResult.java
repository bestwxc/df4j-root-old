package net.df.base.server;

import net.df.base.constants.ResultType;

import java.util.List;

/**
 * 多结果集
 * @param <T>
 */
public class MultiResult<T> extends Result<List<T>>{

    private boolean page = false;

    public MultiResult(){
        this(false, null);
    }

    public MultiResult(List<T> list){
        this(false, list);
    }

    public MultiResult(boolean page, List<T> list){
        super(ResultType.LIST);
        this.setResult(list);
        this.page = page;
    }

    public boolean isPage() {
        return page;
    }

    public void setPage(boolean page) {
        page = page;
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
