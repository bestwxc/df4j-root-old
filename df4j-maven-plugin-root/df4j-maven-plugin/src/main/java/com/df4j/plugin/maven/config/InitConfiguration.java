package com.df4j.plugin.maven.config;
import java.util.List;
import java.util.Map;

public class InitConfiguration {

    private Map<String,InitFileConfiguration> files;

    private List<String> shouldNotOverrideFileNames;

    public Map<String, InitFileConfiguration> getFiles() {
        return files;
    }

    public void setFiles(Map<String, InitFileConfiguration> files) {
        this.files = files;
    }

    public List<String> getShouldNotOverrideFileNames() {
        return shouldNotOverrideFileNames;
    }

    public void setShouldNotOverrideFileNames(List<String> shouldNotOverrideFileNames) {
        this.shouldNotOverrideFileNames = shouldNotOverrideFileNames;
    }
}


