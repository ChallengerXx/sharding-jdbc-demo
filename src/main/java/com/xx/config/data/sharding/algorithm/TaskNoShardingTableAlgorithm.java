package com.xx.config.data.sharding.algorithm;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

/**
 * 根据taskNo取模分表算法
 */
@Slf4j
public class TaskNoShardingTableAlgorithm implements PreciseShardingAlgorithm<String> {

    private int tableSize;

    public TaskNoShardingTableAlgorithm(int tableSize) {
        this.tableSize = tableSize;
    }

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {
        String taskNo = preciseShardingValue.getValue();
        String table = preciseShardingValue.getLogicTableName();
        table = table + (Math.abs(taskNo.hashCode()) % tableSize);
        log.info("{}表taskNo:{}使用表:{}", preciseShardingValue.getLogicTableName(), taskNo, table);
        return table;
    }
}
