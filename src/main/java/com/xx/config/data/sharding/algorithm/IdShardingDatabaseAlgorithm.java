package com.xx.config.data.sharding.algorithm;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

/**
 * 根据id取模分库算法
 */
@Slf4j
public class IdShardingDatabaseAlgorithm implements PreciseShardingAlgorithm<String> {

    private int dbSize;

    public IdShardingDatabaseAlgorithm(int dbSize){
        this.dbSize = dbSize;
    }

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {
        String ds = "ds";
        String id = preciseShardingValue.getValue();
        ds = ds + (Integer.parseInt(id) % dbSize);
        log.info("{}表id:{}使用库:{}", preciseShardingValue.getLogicTableName(), id, ds);
        return ds;
    }
}
