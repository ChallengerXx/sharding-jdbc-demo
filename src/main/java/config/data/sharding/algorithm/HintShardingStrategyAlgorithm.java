package config.data.sharding.algorithm;

import io.shardingsphere.api.algorithm.sharding.ListShardingValue;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.api.algorithm.sharding.hint.HintShardingAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Collections;

@Slf4j
public class HintShardingStrategyAlgorithm implements HintShardingAlgorithm {

    @Override
    public Collection<String> doSharding(Collection<String> collection, ShardingValue shardingValue) {
        ListShardingValue listShardingValue = (ListShardingValue) shardingValue;
        int tableIndex = (int)listShardingValue.getValues().iterator().next();
        String database = "ds" + tableIndex;
        log.info("使用分库信息:{}", database);
        return Collections.singletonList(database);
    }
}
