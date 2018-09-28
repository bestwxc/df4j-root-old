package net.df.base.server;

import net.df.base.constants.ResultType;

import java.util.List;

/**
 * 多结果集
 * @param <T>
 */
public class MultiResult<T> extends Result<List<T>>{

    private boolean isPage = false;

    public MultiResult(){
        this(false, null);
    }

    protected MultiResult(List<T> list){
        this(false, list);
    }

    public MultiResult(boolean isPage, List<T> list){
        super(ResultType.LIST);
        this.setResult(list);
        this.isPage = isPage;
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
