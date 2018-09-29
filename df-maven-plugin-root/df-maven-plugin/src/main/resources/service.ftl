package net.df.module.${moduleName}.service;

import net.df.base.utils.ValidateUtils;
import net.df.module.${moduleName}.mapper.${modelClassName}Mapper;
import net.df.module.${moduleName}.model.${modelClassName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;
import java.util.Date;
import java.util.List;

@Service
public class ${modelClassName}Service {

    @Autowired
    private ${modelClassName}Mapper ${modelObjectName}Mapper;


    <#assign  keys=allFieldMap?keys/>
    <#assign  keys2=fieldMap?keys/>

    /**
     * 新增
    <#list keys2 as key>
     * @param ${key}
    </#list>
     * @return
     */
    public ${modelClassName} add(<#list keys2 as key>${fieldMap["${key}"]} ${key}<#if keys2?size != (key_index + 1)>,</#if></#list>){
        ${modelClassName} ${modelObjectName} = new ${modelClassName}();
        setObject(${modelObjectName},<#list keys2 as key>${key}<#if keys2?size != (key_index + 1)>,</#if></#list>);
        Date now = new Date();
        ${modelObjectName}.setCreateTime(now);
        ${modelObjectName}.setUpdateTime(now);
        ${modelObjectName}Mapper.insert(${modelObjectName});
        return ${modelObjectName};
    }


    /**
     * 更新
     * @param id
    <#list keys2 as key>
     * @param ${key}
    </#list>
     * @return
     */
    public ${modelClassName} update(Long id, <#list keys2 as key>${fieldMap["${key}"]} ${key}<#if keys2?size != (key_index + 1)>,</#if></#list>){
        ${modelClassName} ${modelObjectName} = ${modelObjectName}Mapper.selectByPrimaryKey(id);
        setObject(${modelObjectName},<#list keys2 as key>${key}<#if keys2?size != (key_index + 1)>,</#if></#list>);
        Date now = new Date();
        ${modelObjectName}.setUpdateTime(now);
        ${modelObjectName}Mapper.updateByPrimaryKey(${modelObjectName});
        return ${modelObjectName};
    }

    /**
     * 查询
    <#list keys as key>
     * @param ${key}
    </#list>
     * @return
     */
    public List<${modelClassName}> list(<#list keys as key>${allFieldMap["${key}"]} ${key}<#if keys?size != (key_index + 1)>,</#if></#list>){
        Example example = this.getExample(<#list keys as key>${key}<#if keys?size != (key_index + 1)>,</#if></#list>);
        return ${modelObjectName}Mapper.selectByExample(example);
    }

    /**
     * 查询一个
    <#list keys as key>
     * @param ${key}
    </#list>
     * @return
     */
    public ${modelClassName} listOne(<#list keys as key>${allFieldMap["${key}"]} ${key}<#if keys?size != (key_index + 1)>,</#if></#list>){
        Example example = this.getExample(<#list keys as key>${key}<#if keys?size != (key_index + 1)>,</#if></#list>);
        return ${modelObjectName}Mapper.selectOneByExample(example);
    }


    /**
     * 删除
    <#list keys as key>
     * @param ${key}
    </#list>
     * @return
     */
    public int delete(<#list keys as key>${allFieldMap["${key}"]} ${key}<#if keys?size != (key_index + 1)>,</#if></#list>){
        Example example = this.getExample(<#list keys as key>${key}<#if keys?size != (key_index + 1)>,</#if></#list>);
        return ${modelObjectName}Mapper.deleteByExample(example);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public int delete(Long id){
        return this.delete(<#list keys as key> <#if key_index == 0>id<#else>null</#if><#if keys?size != (key_index + 1)>,</#if></#list>);
    }


    /**
     * 组装更新数据
    <#list keys2 as key>
     * @param ${key}
    </#list>
     * @return
     */
    private void setObject(${modelClassName} ${modelObjectName}, <#list keys2 as key>${fieldMap["${key}"]} ${key}<#if keys2?size != (key_index + 1)>,</#if></#list>){
        <#list keys2 as key>
        if(ValidateUtils.<#if fieldMap["${key}"] == "String">isNotEmptyString<#else>notNull</#if>(${key})){
            ${modelObjectName}.set${key?cap_first}(${key});
        }
        </#list>
    }

    /**
     * 组装Example
    <#list keys as key>
     * @param ${key}
    </#list>
     * @return
     */
    private Example getExample(<#list keys as key>${allFieldMap["${key}"]} ${key}<#if keys?size != (key_index + 1)>,</#if></#list>){
        WeekendSqls<${modelClassName}> sqls = WeekendSqls.<${modelClassName}>custom();
        <#list keys as key>
        if(ValidateUtils.<#if allFieldMap["${key}"] == "String">isNotEmptyString<#else>notNull</#if>(${key})) {
            sqls.andEqualTo(${modelClassName}::get${key?cap_first}, ${key});
        }
        </#list>
        Example example = new Example.Builder(${modelClassName}.class).where(sqls).build();
        return example;
    }
}
