package ${basePackage}.${moduleName}.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.df4j.base.server.Result;
import com.df4j.base.utils.MapUtils;
import com.df4j.base.utils.FieldUtils;
import com.df4j.base.utils.DateUtils;
import com.df4j.base.form.Field;
import com.df4j.base.form.BoundType;
import com.df4j.base.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.List;
import java.util.Map;
import ${modelClass};
import ${serviceClass};

<#assign  controllerConfig=config.controller/>
<#assign  keys=allFieldMap?keys/>
<#assign  keys2=fieldMap?keys/>

<#if controllerConfig.disableMapping == true>//</#if>@RestController
@RequestMapping("${controllerConfig.classRequestMapping}")
public class ${controllerClassName} {

    private Logger logger = LoggerFactory.getLogger(${controllerClassName}.class);

    @Autowired
    private ${serviceClassName} ${serviceObjectName};

    <#assign  listConfig=controllerConfig.list/>
    <#if listConfig.enabled == true>
    /**
     * 查询
     * @param map
     * @return
     */
    @RequestMapping("/${controllerConfig.methodRequestMapping}/list")
    public Result<List<${modelClassName}>> list(@RequestBody Map<String,?> map){
        <#list keys as key>
            <#if listConfig["${key}"]??>
            <#else>
        //${allFieldMap["${key}"]} ${key} = MapUtils.get${allFieldMap["${key}"]}FromMap(map, "${key}", null);
                <#if allFieldMap["${key}"] == "Date">
        Field<${allFieldMap["${key}"]}> ${key} = FieldUtils.get${allFieldMap["${key}"]}Field(map, "${key}", DateUtils.DATE_TIME_PATTERN, false, BoundType.INCLUDE, BoundType.INCLUDE);
                <#else>
        Field<${allFieldMap["${key}"]}> ${key} = FieldUtils.get${allFieldMap["${key}"]}Field(map, "${key}", false, BoundType.INCLUDE, BoundType.INCLUDE);
                </#if>
            </#if>
        </#list>
        List<${modelClassName}> list = ${serviceObjectName}.list(<#list keys as key>${key}<#if keys?size != (key_index + 1)>, </#if></#list>);
        return ResultUtils.success(list);
    }
    </#if>

    <#assign  addConfig=controllerConfig.add/>
    <#if addConfig.enabled == true>
    /**
     * 新增
     * @param map
     * @return
     */
    @RequestMapping("/${controllerConfig.methodRequestMapping}/add")
    public Result<${modelClassName}> add(@RequestBody Map<String,?> map){
        <#list keys2 as key>
            <#if addConfig["${key}"]??>
            <#else>
        ${fieldMap["${key}"]} ${key} = MapUtils.get${fieldMap["${key}"]}FromMap(map, "${key}", null);
            </#if>
        </#list>
        ${modelClassName} ${modelObjectName} = ${serviceObjectName}.add(<#list keys2 as key>${key}<#if keys2?size != (key_index + 1)>, </#if></#list>);
        return ResultUtils.success(${modelObjectName});
    }
    </#if>

    <#assign  updateConfig=controllerConfig.update/>
    <#if updateConfig.enabled == true>
    /**
     * 更新
     * @param map
     * @return
     */
    @RequestMapping("/${controllerConfig.methodRequestMapping}/update")
    public Result<${modelClassName}> update(@RequestBody Map<String,?> map) {
        Long id = MapUtils.getLongFromMapNotNull(map, "id");
        <#list keys2 as key>
            <#if updateConfig["${key}"]??>
            <#else>
        ${fieldMap["${key}"]} ${key} = MapUtils.get${fieldMap["${key}"]}FromMap(map, "${key}", null);
            </#if>
        </#list>
        ${modelClassName} ${modelObjectName} = ${serviceObjectName}.update(id, <#list keys2 as key>${key}<#if keys2?size != (key_index + 1)>, </#if></#list>);
        return ResultUtils.success(${modelObjectName});
    }
    </#if>


    <#assign  deleteConfig=controllerConfig.delete/>
    <#if deleteConfig.enabled == true>
    /**
     * 删除
     * @param map
     * @return
     */
    @RequestMapping("/${controllerConfig.methodRequestMapping}/delete")
    public Result delete(@RequestBody Map<String,?> map) {
        Long id = MapUtils.getLongFromMapNotNull(map, "id");
        int num = ${serviceObjectName}.<#if isLoginDelete>logicDelete<#else>delete</#if>(id);
        return ResultUtils.success(null);
    }
    </#if>
}
