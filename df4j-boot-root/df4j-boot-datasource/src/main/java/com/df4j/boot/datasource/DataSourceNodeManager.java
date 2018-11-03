package com.df4j.boot.datasource;

import com.df4j.base.utils.ValidateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

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
        defaultNodeMap.put(dataSourceKey, defaultNodeKey);
        nodesMap.put(dataSourceKey, nodes);
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
     * 设置当前数据源
     * @param dataSourceKey
     * @param nodeKey
     */
    public static void setDataSource(String dataSourceKey, String nodeKey){
        String selectKey = null;
        Map<String, String> dataSourceNodes = nodesMap.get(dataSourceKey);
        if(dataSourceNodes == null && dataSourceNodes.isEmpty()){
            dataSourceNodes = nodesMap.get(defaultDataSource);
            selectKey = dataSourceNodes.get(defaultNodeMap.get(defaultDataSource));
        } else {
            selectKey = dataSourceNodes.get(nodeKey);
            if(ValidateUtils.isEmptyString(selectKey)){
                selectKey = dataSourceNodes.get(defaultNodeMap.get(dataSourceKey));
            }
        }
        currentDataSourceKey.set(selectKey);
        logger.info("将当前dataSource设置为:{}, dataSourceKey:{}, nodeKey:{}", selectKey, dataSourceKey, nodeKey);
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
}