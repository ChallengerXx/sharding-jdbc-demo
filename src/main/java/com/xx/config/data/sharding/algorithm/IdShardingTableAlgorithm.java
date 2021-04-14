package com.xx.config.data.sharding.algorithm;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

/**
 * user表根据id取模分表算法
 */
@Slf4j
public class IdShardingTableAlgorithm implements PreciseShardingAlgorithm<String> {

    private int tableSize;

    public IdShardingTableAlgorithm(int size){
        this.tableSize = size;
    }

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {
        String id = preciseShardingValue.getValue();
        String table = preciseShardingValue.getLogicTableName();
        table = table + Integer.parseInt(id) % tableSize;
        log.info("{}表id:{}使用表:{}", preciseShardingValue.getLogicTableName(), id, table);
        return table;
    }
}
