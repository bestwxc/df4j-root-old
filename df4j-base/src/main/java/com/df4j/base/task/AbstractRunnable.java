package com.df4j.base.task;

import com.df4j.base.exception.DfException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRunnable implements Runnable{

    private Logger logger = LoggerFactory.getLogger(AbstractRunnable.class);

    private ThreadLocal<Long> beginTime = new ThreadLocal<>();

    protected String getName(){
        return this.getClass().getName();
    }

    @Override
    public void run() {
        try {
            Long time = System.currentTimeMillis();
            logger.info("Task-{} start.", this.getName());
            beginTime.set(time);
            this.run0();
            logger.info("Task-{} end, cost {} ms",
                    this.getName(), System.currentTimeMillis() - beginTime.get());
            beginTime.remove();
        }catch (Throwable e){
            String errorMsg = "Task-" + this.getName() + " error";
            if(e instanceof Exception){
                throw new DfException(errorMsg, e);
            }else{
                logger.error(errorMsg, e);
                throw e;
            }
        }
    }

    public abstract void run0();
}
