package com.df4j.plugin.maven.config;

/**
 * modelinfo生成配置
 */
public class ModelInfoConfiguration {
    /**
     * 是否启用
     */
    private boolean enabled = true;

    /**
     * ModelInfo名称
     */
    private String modelInfoClassName;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getModelInfoClassName() {
        return modelInfoClassName;
    }

    public void setModelInfoClassName(String modelInfoClassName) {
        this.modelInfoClassName = modelInfoClassName;
    }
}
