package com.ynan.config;

import java.util.Collection;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

/**
 * @Author yuannan
 * @Date 2022/3/12 12:36
 */
public class MyDbPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        String logicTableName = shardingValue.getLogicTableName();
        String uid = shardingValue.getColumnName();
        Long value = shardingValue.getValue();
        long i = value % 2;
        long suffix = i + 1;
        return "m" + suffix;
    }
}