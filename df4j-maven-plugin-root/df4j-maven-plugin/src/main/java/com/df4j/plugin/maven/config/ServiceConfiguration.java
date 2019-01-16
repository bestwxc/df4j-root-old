package com.df4j.plugin.maven.config;

/**
 * service生成配置
 */
public class ServiceConfiguration {
    /**
     * 是否启用
     */
    private boolean enabled = true;

    /**
     * Service名称
     */
    private String serviceClassName;

    /**
     * id生成Key
     */
    private String idGeneratorKey;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getServiceClassName() {
        return serviceClassName;
    }

    public void setServiceClassName(String serviceClassName) {
        this.serviceClassName = serviceClassName;
    }

    public String getIdGeneratorKey() {
        return idGeneratorKey;
    }

    public void setIdGeneratorKey(String idGeneratorKey) {
        this.idGeneratorKey = idGeneratorKey;
    }
}
