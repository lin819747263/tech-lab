package com.kehua.flink.transform;

import com.kehua.flink.source.VideoOrder;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Date;

public class MaxMinBy {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.setParallelism(4);

        DataStream<VideoOrder> ds = env.fromElements(
                new VideoOrder("253","java",30,15,new Date()),
                new VideoOrder("323","java",30,5,new Date()),
                new VideoOrder("42","java",30,5,new Date()),
                new VideoOrder("543","springboot",21,5,new Date()),
                new VideoOrder("423","redis",40,5,new Date()),
                new VideoOrder("15","redis",40,5,new Date()),
                new VideoOrder("312","springcloud",521,5,new Date()),
                new VideoOrder("125","kafka",1,55,new Date())
        );

        SingleOutputStreamOperator<VideoOrder> maxByDS = ds.keyBy(new KeySelector<VideoOrder, String>() {
            @Override
            public String getKey(VideoOrder value) throws Exception {
                return value.getTitle();
            }
        }).maxBy("money");

//        SingleOutputStreamOperator<VideoOrder> maxDS = ds.keyBy(new KeySelector<VideoOrder, String>() {
//            @Override
//            public String getKey(VideoOrder value) throws Exception {
//                return value.getTitle();
//            }
//        }).max("money");

        maxByDS.print("maxByDS:");
//        maxDS.print("maxDS:");

        env.execute("custom source job");
    }

}
