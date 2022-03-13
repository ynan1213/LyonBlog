package com.ynan.config;

import java.util.Arrays;
import java.util.Collection;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

/**
 * @Author yuannan
 * @Date 2022/3/12 12:36
 */
public class MyDbRangeShardingAlgorithm implements RangeShardingAlgorithm<Long> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Long> shardingValue) {
        availableTargetNames.forEach(System.out::println);
        Long upper = shardingValue.getValueRange().upperEndpoint();
        Long lower = shardingValue.getValueRange().lowerEndpoint();
        String logicTableName = shardingValue.getLogicTableName();
        return Arrays.asList("m1", "m2");
    }
}

