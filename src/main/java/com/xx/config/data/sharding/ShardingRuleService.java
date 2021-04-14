package com.xx.config.data.sharding;

import com.xx.config.data.sharding.algorithm.IdShardingDatabaseAlgorithm;
import com.xx.config.data.sharding.algorithm.IdShardingTableAlgorithm;
import com.xx.config.data.sharding.algorithm.TaskNoShardingDataBaseAlgorithm;
import com.xx.config.data.sharding.algorithm.TaskNoShardingTableAlgorithm;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import io.shardingsphere.api.config.ShardingRuleConfiguration;
import io.shardingsphere.api.config.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.StandardShardingStrategyConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
@Slf4j
public class ShardingRuleService {

    @Autowired
    ShardingDataSourceProperties shardingDataSourceProperties;

    /**
     * 创建分库规则
     * @param dataSourceKeys
     * @return
     */
    public ShardingRuleConfiguration createShardingRuleConfiguration(final Set<String> dataSourceKeys){
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        Collection<TableRuleConfiguration> tableRuleConfigs = shardingRuleConfiguration.getTableRuleConfigs();
        for (ShardingDataSourceProperties.ShardingTable shardingTable : shardingDataSourceProperties.getShardingTableList()) {
            switch (shardingTable.getShardingColumn()){
                case "id": //根据id分库分表
                    tableRuleConfigs.add(generateShardingRule(shardingTable.getTableName(), shardingTable.getShardingColumn()
                            , new IdShardingDatabaseAlgorithm(dataSourceKeys.size())
                            , new IdShardingTableAlgorithm(shardingTable.getTableSize()), dataSourceKeys.size(), shardingTable.getTableSize()));
                    break;
                case "task_no": //根据task_no分库分表
                    tableRuleConfigs.add(generateShardingRule(shardingTable.getTableName(), shardingTable.getShardingColumn()
                            , new TaskNoShardingDataBaseAlgorithm(dataSourceKeys.size())
                            , new TaskNoShardingTableAlgorithm(shardingTable.getTableSize()), dataSourceKeys.size(), shardingTable.getTableSize()));
                    break;
            }
        }
        //设置默认数据源
        shardingRuleConfiguration.setDefaultDataSourceName("ds0");
        return shardingRuleConfiguration;
    }

    /**
     * 分库规则生成
     * @param tableName 表名
     * @param shardingColumn 划分字段名
     * @param dbAlgorithm 分库规则
     * @param tableAlgorithm 分表规则
     * @param dbSize db数量
     * @param tableSize 单库表数量
     * @return
     */
    private TableRuleConfiguration generateShardingRule(String tableName, String shardingColumn, PreciseShardingAlgorithm dbAlgorithm
            , PreciseShardingAlgorithm tableAlgorithm, int dbSize, int tableSize){
        log.info("表{}拆分为{}库,{}表", tableName, dbSize, tableSize);
        TableRuleConfiguration tableRule = new TableRuleConfiguration();
        tableRule.setLogicTable(tableName);
        String actualDataNodes = "ds${0.." + (dbSize - 1) + "}." + tableName;
        if (tableSize > 1){
            actualDataNodes = actualDataNodes + "${0.." + (tableSize - 1) + "}";
        }
        tableRule.setActualDataNodes(actualDataNodes);
        if (dbAlgorithm != null){ //分库规则不为空
            StandardShardingStrategyConfiguration dbShardingStrategyConfiguration = new StandardShardingStrategyConfiguration(shardingColumn
                    , dbAlgorithm);
            tableRule.setDatabaseShardingStrategyConfig(dbShardingStrategyConfiguration);
        }
        if (tableAlgorithm != null){//分表规则不为空
            StandardShardingStrategyConfiguration tableShardingStrategyConfiguration = new StandardShardingStrategyConfiguration(shardingColumn
                    , tableAlgorithm);
            tableRule.setTableShardingStrategyConfig(tableShardingStrategyConfiguration);
        }
        return tableRule;
    }
}
