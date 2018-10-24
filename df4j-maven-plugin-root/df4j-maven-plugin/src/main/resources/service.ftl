package ${basePackage}.${moduleName}.service;

import com.df4j.base.utils.ValidateUtils;
import com.df4j.boot.mybatis.utils.WeekendSqlsUtils;
import com.df4j.base.form.Field;
import ${basePackage}.${moduleName}.mapper.${modelClassName}Mapper;
import ${basePackage}.${moduleName}.model.${modelClassName};
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

    private WeekendSqlsUtils<${modelClassName}> sqlsUtils = new WeekendSqlsUtils<>();


    <#assign  keys=allFieldMap?keys/>
    <#assign  keys2=fieldMap?keys/>

    /**
     * 新增
    <#list keys2 as key>
     * @param ${key}
    </#list>
     * @return
     */
    public ${modelClassName} add(<#list keys2 as key>${fieldMap["${key}"]} ${key}<#if keys2?size != (key_index + 1)>, </#if></#list>){
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
    public ${modelClassName} update(Long id, <#list keys2 as key>${fieldMap["${key}"]} ${key}<#if keys2?size != (key_index + 1)>, </#if></#list>){
        ${modelClassName} ${modelObjectName} = ${modelObjectName}Mapper.selectByPrimaryKey(id);
        setObject(${modelObjectName},<#list keys2 as key>${key}<#if keys2?size != (key_index + 1)>, </#if></#list>);
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
    public List<${modelClassName}> list(<#list keys as key>${allFieldMap["${key}"]} ${key}<#if keys?size != (key_index + 1)>, </#if></#list>){
        Example example = this.getExample(<#list keys as key>${key}<#if keys?size != (key_index + 1)>, </#if></#list>);
        return ${modelObjectName}Mapper.selectByExample(example);
    }

    /**
     * 查询
    <#list keys as key>
     * @param ${key}
    </#list>
     * @return
     */
    public List<${modelClassName}> list(<#list keys as key>Field<${allFieldMap["${key}"]}> ${key}<#if keys?size != (key_index + 1)>, </#if></#list>){
        Example example = this.getExample(<#list keys as key>${key}<#if keys?size != (key_index + 1)>, </#if></#list>);
        return ${modelObjectName}Mapper.selectByExample(example);
    }

    /**
     * 查询
    <#list keys2 as key>
     * @param ${key}
    </#list>
     * @return
     */
    public List<${modelClassName}> list(<#list keys2 as key>${allFieldMap["${key}"]} ${key}<#if keys2?size != (key_index + 1)>, </#if></#list>){
        return this.list(null, <#list keys2 as key>${key} <#if keys2?size != (key_index + 1)>, </#if></#list> ,null, null);
    }

    /**
     * 查询一个
     * @param id
     * @return
     */
    public ${modelClassName} listOne(Long id){
        return ${modelObjectName}Mapper.selectByPrimaryKey(id);
    }
<#if isLoginDelete>
    <#assign ukKeys=ukColumns?split(",")/>
    /**
     * 查询一个
    <#list ukKeys as key>
     * @param ${key}
    </#list>
     * @return
     */
    public ${modelClassName} listOne(<#list ukKeys as key>${allFieldMap["${key}"]} ${key}<#if ukKeys?size != (key_index + 1)>, </#if></#list>){
        return listOne(null, <#list keys2 as key><#if ukKeys?seq_contains(key)>${key}, <#elseif key == "flag">0, <#else>null, </#if></#list>null, null);
    }
</#if>

    /**
     * 查询一个
    <#list keys as key>
     * @param ${key}
    </#list>
     * @return
     */
    public ${modelClassName} listOne(<#list keys as key>${allFieldMap["${key}"]} ${key}<#if keys?size != (key_index + 1)>, </#if></#list>){
        Example example = this.getExample(<#list keys as key>${key}<#if keys?size != (key_index + 1)>, </#if></#list>);
        return ${modelObjectName}Mapper.selectOneByExample(example);
    }


    /**
     * 删除
    <#list keys as key>
     * @param ${key}
    </#list>
     * @return
     */
    public int delete(<#list keys as key>${allFieldMap["${key}"]} ${key}<#if keys?size != (key_index + 1)>, </#if></#list>){
        Example example = this.getExample(<#list keys as key>${key}<#if keys?size != (key_index + 1)>, </#if></#list>);
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

<#if isLoginDelete>
    /**
     * 逻辑删除
     * @param id
     * @return
     */
    public int logicDelete(Long id){
        ${modelClassName} ${modelObjectName} = ${modelObjectName}Mapper.selectByPrimaryKey(id);
        List<${modelClassName}> list = this.list(null, <#list keys2 as key><#if ukColumns?contains(key)>${modelObjectName}.get${key?cap_first}(), <#else>null, </#if></#list>null, null);
        ${modelClassName} max${modelClassName} = list.stream().max((${modelObjectName}1, ${modelObjectName}2) -> ${modelObjectName}1.getFlag() - ${modelObjectName}2.getFlag()).get();
        this.update(id<#list keys2 as key><#if key != 'flag'>, null<#else>, max${modelClassName}.getFlag() + 1</#if></#list>);
        return 1;
    }
</#if>


    /**
     * 组装更新数据
    <#list keys2 as key>
     * @param ${key}
    </#list>
     * @return
     */
    private void setObject(${modelClassName} ${modelObjectName}, <#list keys2 as key>${fieldMap["${key}"]} ${key}<#if keys2?size != (key_index + 1)>, </#if></#list>){
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
    private Example getExample(<#list keys as key>Object ${key}<#if keys?size != (key_index + 1)>,</#if></#list>){
        WeekendSqls<${modelClassName}> sqls = WeekendSqls.<${modelClassName}>custom();
        <#list keys as key>
        sqlsUtils.appendSql(sqls, ${modelClassName}::get${key?cap_first}, ${key});
        </#list>
        Example example = new Example.Builder(${modelClassName}.class).where(sqls).build();
        return example;
    }
}
