package com.xx.config.data.sharding.algorithm;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

/**
 * 根据taskNo取模分库算法
 */
@Slf4j
public class TaskNoShardingDataBaseAlgorithm implements PreciseShardingAlgorithm<String> {

    private int dbSize;

    public TaskNoShardingDataBaseAlgorithm(int dbSize) {
        this.dbSize = dbSize;
    }

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {
        String ds = "ds";
        String taskNo = preciseShardingValue.getValue();
        ds = ds + Math.abs(taskNo.hashCode()) % dbSize;
        log.info("{}表taskNo:{}使用库:{}", preciseShardingValue.getLogicTableName(), taskNo, ds);
        return ds;
    }
}
