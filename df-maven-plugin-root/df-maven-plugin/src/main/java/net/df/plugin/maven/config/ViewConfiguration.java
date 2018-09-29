package net.df.plugin.maven.config;

/**
 * 页面生成配置
 */
public class ViewConfiguration {
    /**
     * 是否启用
     */
    private boolean enabled = true;

    /**
     * 页面工程路径
     */
    private String viewProjectPath;

    /**
     * 页面的公共文件夹
     */
    private String viewPackage = "src/views";

    /**
     * 页面的相对路径及文件名
     */
    private String viewPathAndName;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getViewProjectPath() {
        return viewProjectPath;
    }

    public void setViewProjectPath(String viewProjectPath) {
        this.viewProjectPath = viewProjectPath;
    }

    public String getViewPackage() {
        return viewPackage;
    }

    public void setViewPackage(String viewPackage) {
        this.viewPackage = viewPackage;
    }

    public String getViewPathAndName() {
        return viewPathAndName;
    }

    public void setViewPathAndName(String viewPathAndName) {
        this.viewPathAndName = viewPathAndName;
    }
}
