package com.df4j.plugin.maven.config;

public class Configuration {
    private InitConfiguration init;
    private GeneratorConfiguration generate;
    public InitConfiguration getInit() {
        return init;
    }
    public void setInit(InitConfiguration init) {
        this.init = init;
    }

    public GeneratorConfiguration getGenerate() {
        return generate;
    }

    public void setGenerate(GeneratorConfiguration generate) {
        this.generate = generate;
    }
}
