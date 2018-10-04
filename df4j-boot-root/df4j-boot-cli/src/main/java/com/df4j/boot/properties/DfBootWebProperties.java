package com.df4j.boot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * SpringBoot Web配置
 */
@ConfigurationProperties(prefix = "df.boot.web")
public class DfBootWebProperties {
    private boolean openCors;

    private boolean openHttpDebugLog;

    private String securityType;

    public boolean isOpenCors() {
        return openCors;
    }

    public void setOpenCors(boolean openCors) {
        this.openCors = openCors;
    }

    public boolean isOpenHttpDebugLog() {
        return openHttpDebugLog;
    }

    public void setOpenHttpDebugLog(boolean openHttpDebugLog) {
        this.openHttpDebugLog = openHttpDebugLog;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public String getSecurityType() {
        return securityType;
    }
}
