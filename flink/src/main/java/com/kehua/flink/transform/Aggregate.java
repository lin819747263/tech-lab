package com.kehua.flink.transform;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Aggregate {

    public static void main(String[] args) throws Exception {
        //构建执行任务环境以及任务的启动的入口, 存储全局相关的参数
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());

        DataStream<String> ds = env.socketTextStream("127.0.0.1",8888);


        DataStream<Tuple2<String, Integer>> flatMapDataStream = ds.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
                Arrays.stream(s.split(",")).forEach(x -> {
                    collector.collect(new Tuple2<>(x, 1));
                });
            }
        });


        SingleOutputStreamOperator<Map<String, Integer>> aggDS = flatMapDataStream.keyBy(x -> x.f0)
                .countWindow(5)
                .aggregate(new AggregateFunction<Tuple2<String, Integer>, Map<String, Integer>, Map<String, Integer>>() {

                    @Override
                    public Map<String, Integer> createAccumulator() {
                        return new HashMap<>();
                    }

                    @Override
                    public Map<String, Integer> add(Tuple2<String, Integer> in, Map<String, Integer> acc) {
                        if(acc.get(in.f0) != null){
                            acc.putIfAbsent(in.f0, in.f1 + acc.get(in.f0));
                        }else {
                            acc.putIfAbsent(in.f0, in.f1);
                        }

                        return acc;
                    }

                    @Override
                    public Map<String, Integer> getResult(Map<String, Integer> acc) {
                        return acc;
                    }

                    @Override
                    public Map<String, Integer> merge(Map<String, Integer> acc0, Map<String, Integer> acc1) {
                        acc0.forEach((k,v) -> {
                            if(acc1.get(k) != null){
                                acc1.put(k,v);
                            }else {
                                acc1.put(k,v + acc1.get(k));
                            }
                        });
                        return acc1;
                    }
                });

        aggDS.print();
        //DataStream需要调用execute,可以取个名称
        env.execute("data stream job");
    }

}
