package com.df4j.base.task;

import com.df4j.base.exception.DfException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRunnable implements Runnable{

    private Logger logger = LoggerFactory.getLogger(AbstractRunnable.class);

    private ThreadLocal<Long> beginTime = new ThreadLocal<>();

    // 是否向外抛出异常，默认不抛，适用于大部分业务。部分调用该类处理逻辑希望抛出异常的，可以指定该参数为true
    private boolean throwException = false;

    protected String getName(){
        return this.getClass().getName();
    }

    public AbstractRunnable() {
        this(false);
    }

    public AbstractRunnable(boolean throwException){
        this.throwException = throwException;
    }

    public boolean isThrowException() {
        return throwException;
    }

    public void setThrowException(boolean throwException) {
        this.throwException = throwException;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        try {
            Long time = System.currentTimeMillis();
            logger.debug("Task-{}[{}] start.", this.getName(), threadName);
            beginTime.set(time);
            this.run0();
            logger.debug("Task-{}[{}] end, cost {} ms",
                    this.getName(), threadName, System.currentTimeMillis() - beginTime.get());
            beginTime.remove();
        }catch (Throwable e){
            String errorMsg = "Task-" + this.getName() + "[" + threadName +"] error";
            if(!throwException){
                logger.error(errorMsg, e);
            }
            if(throwException){
                DfException exception = e instanceof DfException ? (DfException)e : new DfException(errorMsg, e);
                throw exception;
            }
        }
    }

    public abstract void run0();
}
