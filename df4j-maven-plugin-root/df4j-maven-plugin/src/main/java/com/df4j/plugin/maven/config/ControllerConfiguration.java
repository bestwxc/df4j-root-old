package com.df4j.plugin.maven.config;

import java.util.Map;

/**
 * Controller生成配置
 */
public class ControllerConfiguration {
    /**
     * 是否启用
     */
    private boolean enabled = true;

    /**
     * Controller名称
     */
    private String controllerClassName;

    /**
     * 类上的
     */
    private String classRequestMapping;

    /**
     * 方法上的公共
     */
    private String methodRequestMapping;


    /**
     * 查询配置
     */
    private Map<String,Object> list;

    /**
     * 新增配置
     */
    private Map<String,Object> add;

    /**
     * 更新配置
     */
    private Map<String,Object> update;

    /**
     * 删除配置
     */
    private Map<String,Object> delete;


    /**
     * 取消该类中方法的映射，用于继承
     */
    private boolean disableMapping = false;


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getControllerClassName() {
        return controllerClassName;
    }

    public void setControllerClassName(String controllerClassName) {
        this.controllerClassName = controllerClassName;
    }

    public String getClassRequestMapping() {
        return classRequestMapping;
    }

    public void setClassRequestMapping(String classRequestMapping) {
        this.classRequestMapping = classRequestMapping;
    }

    public String getMethodRequestMapping() {
        return methodRequestMapping;
    }

    public void setMethodRequestMapping(String methodRequestMapping) {
        this.methodRequestMapping = methodRequestMapping;
    }

    public Map<String, Object> getList() {
        return list;
    }

    public void setList(Map<String, Object> list) {
        this.list = list;
    }

    public Map<String, Object> getAdd() {
        return add;
    }

    public void setAdd(Map<String, Object> add) {
        this.add = add;
    }

    public Map<String, Object> getUpdate() {
        return update;
    }

    public void setUpdate(Map<String, Object> update) {
        this.update = update;
    }

    public Map<String, Object> getDelete() {
        return delete;
    }

    public void setDelete(Map<String, Object> delete) {
        this.delete = delete;
    }

    public boolean isDisableMapping() {
        return disableMapping;
    }

    public void setDisableMapping(boolean disableMapping) {
        this.disableMapping = disableMapping;
    }
}
