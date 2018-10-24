<template>
  <div class="app-container">
    <base-table
        v-bind="tableConfig">
    </base-table>
  </div>
</template>
<script>
<#assign  controllerConfig=config.controller/>
<#assign  keys=allFieldMap?keys/>
<#assign  keys2=fieldMap?keys/>
<#assign  listConfig=controllerConfig.list/>
<#assign  addConfig=controllerConfig.add/>
<#assign  updateConfig=controllerConfig.update/>
<#assign  deleteConfig=controllerConfig.delete/>
import BaseTable from '@/components/table/BaseTable'
export default {
  name: '${modelClassName}',
  components: { BaseTable },
  data () {
    return {
      tableConfig: {
        tableType: 'baseTable',
        treeTableConfig: {
          expandAll: false,
          relateKey: 'menuCode',
          relateParentKey: 'parentMenu',
          rootKeyValue: 'root'
        },
        columns: [
<#list keys as key>
  <#if listConfig["${key}"]??>
  <#else>
          {text: '${key}', value: '${key}', width: 80, hide: false, hideAdd: false, hideUpdate: false, disableAdd: false, disableUpdate: false, filter: true, sort: true, type: 'input'}<#if keys?size != (key_index + 1)>,</#if>
  </#if>
</#list>
        ],
        list: {
          enabled: ${listConfig.enabled?c},
          url: '/api/${controllerConfig.classRequestMapping}/${controllerConfig.methodRequestMapping}/list'
        },
        add: {
          enabled: ${addConfig.enabled?c},
          url: '/api/${controllerConfig.classRequestMapping}/${controllerConfig.methodRequestMapping}/add'
        },
        update: {
          enabled: ${updateConfig.enabled?c},
          url: '/api/${controllerConfig.classRequestMapping}/${controllerConfig.methodRequestMapping}/update'
        },
        del: {
          enabled: ${deleteConfig.enabled?c},
          url: '/api/${controllerConfig.classRequestMapping}/${controllerConfig.methodRequestMapping}/delete'
        },
        extBtns: [
          // {type: 'success', text: '关联资源', icon: 'el-icon-edit', event: 'showRelateResourcePage', judgeSelectOne: true, judegNoChild: true}
        ]
      }
    }
  },
  methods: {
  },
  created () {
  }
}
</script>
