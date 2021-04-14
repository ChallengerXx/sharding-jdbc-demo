package com.xx.config.data.sharding;

import io.shardingsphere.api.config.ShardingRuleConfiguration;
import io.shardingsphere.core.hint.HintManagerHolder;
import io.shardingsphere.core.routing.strategy.ShardingStrategy;
import io.shardingsphere.core.rule.ShardingRule;

import java.util.Collection;

public class AppShardingRule extends ShardingRule {

    private ShardingStrategy hintShardingStrategy;

    public AppShardingRule(ShardingRuleConfiguration shardingRuleConfig, Collection<String> dataSourceNames, ShardingStrategy hintShardingStrategy) {
        super(shardingRuleConfig, dataSourceNames);
        this.hintShardingStrategy = hintShardingStrategy;
    }

    @Override
    public ShardingStrategy getDefaultDatabaseShardingStrategy() {
        if (HintManagerHolder.isDatabaseShardingOnly()){
            return hintShardingStrategy;
        } else {
            return super.getDefaultDatabaseShardingStrategy();
        }
    }
}
