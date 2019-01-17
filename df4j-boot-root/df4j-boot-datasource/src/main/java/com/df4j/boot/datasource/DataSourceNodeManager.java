package com.df4j.boot.datasource;

import com.df4j.base.utils.NextKey;
import com.df4j.base.utils.StringUtils;
import com.df4j.base.utils.ValidateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 动态数据源节点管理器
 */
public class DataSourceNodeManager {

    private static Logger logger = LoggerFactory.getLogger(DataSourceNodeManager.class);

    //  用于存放当前数据源Bean 名称
    private static ThreadLocal<String> currentDataSourceKey = new ThreadLocal<>();
    // 默认的数据源key
    private static String defaultDataSource = null;
    // 各数据源的默认节点 key
    private static Map<String,String> defaultNodeMap = new HashMap<>();
    // 节点
    private static Map<String, Map<String, String>> nodesMap = new HashMap<>();
    // 从节点keyMap
    private static Map<String, NextKey<String>> slaveMap = new HashMap<>();


    /**
     * 设置默认的数据源key
     * @param dataSource
     */
    public synchronized static void setDefaultDataSourceKey(String dataSource){
        defaultDataSource = dataSource;
    }

    /**
     * 增加数据源配置
     * @param dataSourceKey
     * @param defaultNodeKey
     * @param nodes
     */
    public synchronized static void addDataSource(String dataSourceKey, String defaultNodeKey, Map<String,String> nodes){
        // 数据库主节点
        defaultNodeMap.put(dataSourceKey, defaultNodeKey);
        // 各节点引用
        nodesMap.put(dataSourceKey, nodes);
        // 从节点引用
        Set<String> nodeKeys = nodes.keySet();
        Set<String> slaveNodeKeys = new HashSet<>();
        slaveNodeKeys.addAll(nodeKeys);
        slaveNodeKeys.remove(defaultNodeKey);
        NextKey<String> slaveKeys = new NextKey<>(slaveNodeKeys);
        slaveMap.put(dataSourceKey, slaveKeys);
    }

    /**
     * 移除数据源配置
     * @param dataSourceKey
     */
    public synchronized static void removeDataSource(String dataSourceKey){
        defaultNodeMap.remove(dataSourceKey);
        nodesMap.remove(dataSourceKey);
    }

    /**
     * 获取默认数据源Bean值
     * @return
     */
    public static String getDefaultDataSourceKey(){
        return nodesMap.get(defaultDataSource).get(defaultNodeMap.get(defaultDataSource));
    }

    /**
     * 设置默认的数据源
     */
    public static void setDefaultDataSource(){
        String selectKey = getDefaultDataSourceKey();
        currentDataSourceKey.set(selectKey);
    }

    /**
     * 移除当前数据源key设置，重置为默认
     */
    public static void removeDataSource(){
        setDefaultDataSource();
    }

    /**
     * 获取数据源的key
     * @return
     */
    public static String getDataSourceKey(){
        String selectKey = currentDataSourceKey.get();
        if(ValidateUtils.isEmptyString(selectKey)){
            selectKey = nodesMap.get(defaultDataSource).get(defaultNodeMap.get(defaultDataSource));
        }
        return selectKey;
    }

    /**
     * 设置当前的数据源
     * @param dataSource
     * @param useMaster
     */
    public static void setDataSource(String dataSource, boolean useMaster){
        String selectKey = null;
        Map<String, String> dataSourceNodes = nodesMap.get(dataSource);
        if(dataSourceNodes == null || dataSourceNodes.isEmpty()){
            dataSourceNodes = nodesMap.get(defaultDataSource);
            dataSource = defaultDataSource;
            logger.info("未找到对应的datasource，使用默认的datasource");
        }
        String nodeKey = null;
        if(!useMaster){
            nodeKey = slaveMap.get(dataSource).nextKey();
            logger.debug("获取{}数据源从节点:{}", dataSource, nodeKey);
        }
        if(ValidateUtils.isEmptyString(nodeKey)){
            nodeKey = defaultNodeMap.get(dataSource);
        }
        selectKey = dataSource + StringUtils.objectNameToClassName(nodeKey) + "DataSource";
        currentDataSourceKey.set(selectKey);
        logger.debug("将当前dataSource设置为:{}, dataSourceKey:{}, useMaster:{}", selectKey, dataSource, useMaster);
    }

    /**
     * 设置当前数据源
     * @param dataSource
     */
    public static void setDataSource(String dataSource){
        setDataSource(dataSource, true);
    }

    public static void cleanDataSource(){
        logger.debug("清理掉当前线程的datasourceKey");
        currentDataSourceKey.remove();
    }
}
