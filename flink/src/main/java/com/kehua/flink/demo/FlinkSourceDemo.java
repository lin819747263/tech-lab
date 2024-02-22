package com.kehua.flink.demo;

import com.kehua.flink.source.CustomerSource;
import com.kehua.flink.source.VideoOrder;
import org.apache.flink.shaded.guava18.com.google.common.collect.Lists;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class FlinkSourceDemo {

    public static void main(String[] args) {

        //构建执行任务环境以及任务的启动的入口, 存储全局相关的参数
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //预定义来源
        DataStream<String> stringDataStream01 = env.fromElements("java,springboot","java,springcloud");
        DataStream<String> stringDataStream02 = env.fromCollection(Lists.newArrayList("java,springboot","java,springcloud"));
        DataStream<Long> stringDataStream03 = env.fromSequence(10,100);







        //自定义来源
        DataStream<String> textDS01 = env.readTextFile("/Users/xdclass/Desktop/xdclass_access.log");
        DataStream<String> textDS02 = env.readTextFile("hdfs://xdclass_node:8010/file/log/words.txt");
        DataStream<String> stringDataStream = env.socketTextStream("127.0.0.1",8888);
        DataStream<VideoOrder> videoOrderDataStream = env.addSource(new CustomerSource());

        //1.是什么，解决了什么问题


        //2.核心组件，概念


        //3.


    }
}
