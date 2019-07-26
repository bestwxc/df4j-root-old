package com.df4j.base.model;

/**
 * model信息接口定义
 */
public interface AbstractModelInfo {

    /**
     * 获取model类
     */
    public Class getModelClass();

    /**
     * 获取service类
     */
    public Class getServiceClass();

    /**
     * 获取controller类
     */
    public Class getControllerClass();

    /**
     * 获取mapper类
     */
    public Class getMapperClass();

    /**
     * 是否逻辑删除
     */
    public boolean logicDelete();

    /**
     * 逻辑删除键
     */
    public String deleteFiledKey();

    /**
     * 唯一键
     */
    public String[] ukColumns();

}
