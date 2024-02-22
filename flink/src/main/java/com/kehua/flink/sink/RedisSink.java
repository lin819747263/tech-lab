package com.kehua.flink.sink;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommand;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommandDescription;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisMapper;

public class RedisSink implements RedisMapper<Tuple2<String, Integer>> {

    @Override
    public RedisCommandDescription getCommandDescription() {
        return new RedisCommandDescription(RedisCommand.HSET, "VIDEO_ORDER_COUNTER");
    }

    @Override
    public String getKeyFromData(Tuple2<String, Integer> value) {
        return value.f0;
    }

    @Override
    public String getValueFromData(Tuple2<String, Integer> value) {
        return value.f1.toString();
    }
}
