package ${basePackage}.${moduleName}.model.info;

import com.df4j.base.model.AbstractModelInfo;
import com.df4j.base.form.Field;
import ${basePackage}.${moduleName}.model.${modelClassName};
import ${basePackage}.${moduleName}.mapper.${modelClassName}Mapper;
import ${basePackage}.${moduleName}.controller.${modelClassName}Controller;
import ${basePackage}.${moduleName}.service.${modelClassName}Service;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;

@Component
public class ${modelClassName}ModelInfo implements AbstractModelInfo {

    public Class getModelClass() {
        return ${modelClassName}.class;
    }

    public Class getServiceClass() {
        return ${modelClassName}Service.class;
    }

    public Class getControllerClass() {
        return ${modelClassName}Controller.class;
    }

    public Class getMapperClass() {
        return ${modelClassName}Mapper.class;
    }

    public boolean logicDelete() {
        return ${isLogicDelete};
    }

    public String deleteFiledKey() {
        return "${deleteFiledKey}";
    }

    public String[] ukColumns() {
        return "${ukColumns}".split(",");
    }

}
