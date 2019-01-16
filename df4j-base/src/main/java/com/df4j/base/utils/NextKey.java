package com.df4j.base.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NextKey<T> {

    private List<T> keyList = new ArrayList<T>();

    private int size = 0;

    private int cur = 0;

    public NextKey(Collection<T> keys){
        this.keyList.addAll(keys);
        this.size = keyList.size();
    }

    public synchronized T nextKey(){
        this.cur = (this.cur + 1) % size;
        return keyList.get(cur);
    }

}
