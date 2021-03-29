package config.data.sharding;

import lombok.Data;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据源配置信息
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.demo.datasource")
@PropertySource({"classpath:properties/dbShardingProperty.properties"})
public class ShardingDataSourceProperties {

    private final int initialSize = 5;
    private final int minIdle = 5;
    private final int maxActive = 10;
    private final Long minEvictableIdleTimeMillis = 1000L * 60L * 5L;
    private final Long maxEvictableIdleTimeMillis = 1000L * 60L * 20L; //最大保持空闲时间小于数据wait_time(1500秒)
    private final String validationQuery = "SELECT 1";
    private final String driverClass = "com.mysql.jdbc.Driver";

    private List<Pro> master = new ArrayList<>();
    private List<ShardingTable> shardingTableList = new ArrayList<>();

    @Data
    public static class Pro extends DataSourceProperties{
        private String database;
    }

    @Data
    public static class ShardingTable{
        private String tableName;
        private Integer tableSize;
        private String shardingColumn;
    }
}
