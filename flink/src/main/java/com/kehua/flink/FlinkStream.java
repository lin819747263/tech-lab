package com.kehua.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class FlinkStream {

    public static void main(String[] args) throws Exception {
        //构建执行任务环境以及任务的启动的入口, 存储全局相关的参数
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //相同类型元素的数据流
        DataStream<String> stringDataStream = env.fromElements("java,springboot","java,springcloud");
        // FlatMapFunction<String, String>, key是输入类型，value是Collector响应的收集的类型，看源码注释，也是 DataStream<String>里面泛型类型
        DataStream<String> flatMapDataStream = stringDataStream.flatMap((FlatMapFunction<String, String>) (value, out) -> {
            String[] arr = value.split(",");
            for (String word : arr) {
                out.collect(word);
            }
        }).returns(Types.STRING);
        flatMapDataStream.print("结果");
        //DataStream需要调用execute,可以取个名称
        env.execute("data stream job");
    }
}
