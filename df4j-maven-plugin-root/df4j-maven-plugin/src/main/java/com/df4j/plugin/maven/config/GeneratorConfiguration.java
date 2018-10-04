package com.df4j.plugin.maven.config;

import java.util.Map;

public class GeneratorConfiguration {

    private Map<String,ModelConfiguration> models;

    public Map<String, ModelConfiguration> getModels() {
        return models;
    }

    public void setModels(Map<String, ModelConfiguration> models) {
        this.models = models;
    }
}
