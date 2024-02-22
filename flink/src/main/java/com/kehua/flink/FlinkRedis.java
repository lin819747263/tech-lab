package com.kehua.flink;

import com.kehua.flink.source.VideoOrder;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.redis.RedisSink;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig;
import org.apache.flink.util.Collector;

import java.util.Date;

public class FlinkRedis {

    public static void main(String[] args) throws Exception {
        //构建执行任务环境以及任务的启动的入口, 存储全局相关的参数
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<VideoOrder> ds = env.fromElements(new VideoOrder("21312","java",32,5,new Date()),
                new VideoOrder("314","java",32,5,new Date()),
                new VideoOrder("542","springboot",32,5,new Date()),
                new VideoOrder("42","redis",32,5,new Date()),
                new VideoOrder("52","java",32,5,new Date()),
                new VideoOrder("523","redis",32,5,new Date())
        );
        //map转换，来一个记录一个，方便后续统计
//        DataStream<Tuple2<String,Integer>> mapDS = ds.map(new MapFunction<VideoOrder, Tuple2<String,Integer>>() {
//            @Override
//            public Tuple2<String, Integer> map(VideoOrder value) throws Exception {
//                return new Tuple2<>(value.getTitle(),1);
//            }
//        });
        //只是一对一记录而已，没必要使用flatMap
        DataStream<Tuple2<String,Integer>> mapDS = ds.flatMap(new FlatMapFunction<VideoOrder, Tuple2<String,Integer>>() {
            @Override
            public void flatMap(VideoOrder value, Collector<Tuple2<String, Integer>> out) throws Exception {
                out.collect(new Tuple2<>(value.getTitle(),1));
            }
        });
        //key是返回的类型，value是分组key的类型; DataSet里面分组是groupBy， 流处理里面分组用 keyBy
        KeyedStream<Tuple2<String,Integer>,String> keyByDS =  mapDS.keyBy(new KeySelector<Tuple2<String,Integer>, String>() {
            @Override
            public String getKey(Tuple2<String, Integer> value) throws Exception {
                return value.f0;
            }
        });
        //对各个组内的数据按照数量(value)进行聚合就是求sum, 1表示按照tuple中的索引为1的字段也就是按照数量进行聚合累加
        DataStream<Tuple2<String, Integer>> sumDS = keyByDS.sum(1);
        sumDS.print();
        FlinkJedisPoolConfig conf = new FlinkJedisPoolConfig.Builder().setHost("192.168.68.118").setPassword("satellite").setDatabase(11).build();
        sumDS.addSink(new RedisSink<>(conf, new com.kehua.flink.sink.RedisSink()));
        //DataStream需要调用execute,可以取个名称
        env.execute("custom sink job");
    }
}
