package com.xx.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.xx.config.data.sharding.ShardingDataSourceProperties;
import com.xx.config.data.sharding.ShardingRuleService;
import io.shardingsphere.api.config.ShardingRuleConfiguration;
import io.shardingsphere.core.util.InlineExpressionParser;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@Slf4j
public class DataSourceConfig {

    @Autowired
    ShardingDataSourceProperties shardingDataSourceProperties;
    @Autowired
    Environment env;
    @Autowired
    ShardingRuleService shardingRuleService;

    @Bean
    @ConditionalOnProperty(name = "sharding.jdbc.enabled", havingValue = "true", matchIfMissing = true)
    public DataSource dataSource() throws SQLException{
        Map<String, DataSource> dataSourceMap = getDataSourceMap(); //获取数据源配置
        //分库分规则
        ShardingRuleConfiguration shardingRuleConfiguration = shardingRuleService.createShardingRuleConfiguration(dataSourceMap.keySet());
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfiguration
                , new ConcurrentHashMap(), new Properties());
//        HintShardingStrategy hintShardingStrategy = new HintShardingStrategy(new HintShardingStrategyConfiguration(new HintShardingStrategyAlgorithm()));
//        DataSource dataSource = new ShardingDataSource(dataSourceMap
//                , new AppShardingRule(shardingRuleConfiguration, dataSourceMap.keySet(), hintShardingStrategy), new ConcurrentHashMap(), new Properties());
        return dataSource;
    }

    /**
     * 获取数据源
     * @return
     */
    private Map<String, DataSource> getDataSourceMap(){
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        //Binder获取配置参数
//        Binder binder = Binder.get(env);
//        Map<String, Object> map = binder.bind("spring.demo.datasource", Map.class).get();
        int dsIndex = 0;
        for (ShardingDataSourceProperties.Pro pro : shardingDataSourceProperties.getMaster()) {
            InlineExpressionParser expressionParser = new InlineExpressionParser(pro.getDatabase());
            List<String> dataNodes = expressionParser.splitAndEvaluate();
            for (String dataNode : dataNodes) {
                DruidDataSource dataSource = new DruidDataSource();
                dataSource.setUsername(pro.determineUsername());
                dataSource.setPassword(pro.determinePassword());
                dataSource.setDriverClassName(shardingDataSourceProperties.getDriverClass());
                dataSource.setInitialSize(shardingDataSourceProperties.getInitialSize());
                dataSource.setMinIdle(shardingDataSourceProperties.getMinIdle());
                dataSource.setMaxActive(shardingDataSourceProperties.getMaxActive());
                dataSource.setValidationQuery(shardingDataSourceProperties.getValidationQuery());
                dataSource.setMinEvictableIdleTimeMillis(shardingDataSourceProperties.getMinEvictableIdleTimeMillis());
                dataSource.setMaxEvictableIdleTimeMillis(shardingDataSourceProperties.getMaxEvictableIdleTimeMillis());
                dataSource.setUrl(String.format(pro.determineUrl(), dataNode));
                dataSourceMap.put("ds" + dsIndex, dataSource);
                dsIndex++;
            }
        }

        return dataSourceMap;
    }

}
