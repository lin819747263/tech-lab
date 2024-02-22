package com.kehua.flink.transform;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.util.Arrays;

public class Window {
    public static void main(String[] args) throws Exception {
        //构建执行任务环境以及任务的启动的入口, 存储全局相关的参数
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());

        DataStream<String> stringDataStream = env.socketTextStream("127.0.0.1",8888);

        DataStream<Tuple2<String, Integer>> flatMapDataStream = stringDataStream.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
                Arrays.stream(s.split(",")).forEach(x -> {
                    collector.collect(new Tuple2<>(x, 1));
                });
            }
        });

//        SingleOutputStreamOperator<Tuple2<String, Integer>> sum = flatMapDataStream.keyBy(x -> x.f0)
//                .window(SlidingProcessingTimeWindows.of(Time.seconds(20),Time.seconds(5)))
//                .sum("f1");

//        SingleOutputStreamOperator<Tuple2<String, Integer>> sum = flatMapDataStream.keyBy(x -> x.f0)
//                .window(TumblingProcessingTimeWindows.of(Time.seconds(5)))
//                .sum("f1");

//        SingleOutputStreamOperator<Tuple2<String, Integer>> sum = flatMapDataStream.keyBy(x -> x.f0)
//                .countWindow(5)
//                .sum("f1");

        SingleOutputStreamOperator<Tuple2<String, Integer>> sum = flatMapDataStream.keyBy(x -> x.f0)
                .countWindow(10,5)
                .sum("f1");


        sum.print();

        //DataStream需要调用execute,可以取个名称
        env.execute("data stream job");
    }
}
