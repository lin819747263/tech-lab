package com.kehua.flink.transform;

import com.kehua.flink.source.VideoOrder;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.util.Date;

public class RichMapAndFlatMap {

    public static void main(String[] args) throws Exception {
        flatMap();
    }


    static void map() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
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
//      map转换，来一个记录一个，方便后续统计
        DataStream<Tuple2<String,Integer>> mapDS = ds.map(new RichMapFunction<VideoOrder, Tuple2<String, Integer>>() {
            @Override
            public void open(Configuration parameters) throws Exception {
                System.out.println("open====");
            }
            @Override
            public void close() throws Exception {
                System.out.println("close====");
            }
            @Override
            public Tuple2<String, Integer> map(VideoOrder value) throws Exception {
                return new Tuple2<>(value.getTitle(),1);
            }
        });
        mapDS.print();

        env.execute("custom source job");
    }

    static void flatMap() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<VideoOrder> ds = env.fromElements(
                new VideoOrder("253","java",30,15,new Date()),
                new VideoOrder("323","java",30,5,new Date()),
                new VideoOrder("42","java",30,5,new Date()),
                new VideoOrder("543","springboot",21,5,new Date()),
                new VideoOrder("423","redis",40,5,new Date()),
                new VideoOrder("15","redis",40,5,new Date()),
                new VideoOrder("312","springcloud",521,5,new Date()),
                new VideoOrder("125","kafka",1,55,new Date()));

//      map转换，来一个记录一个，方便后续统计
        DataStream<Tuple2<String,Integer>> mapDS = ds.flatMap(new RichFlatMapFunction<VideoOrder, Tuple2<String,Integer>>() {
            @Override
            public void open(Configuration parameters) throws Exception {
                System.out.println("open====");
            }
            @Override
            public void close() throws Exception {
                System.out.println("close====");
            }
            @Override
            public void flatMap(VideoOrder value, Collector<Tuple2<String, Integer>> out) throws Exception {
                out.collect(new Tuple2<>(value.getTitle(),1));
            }
        });
        mapDS.print();

        env.execute("custom source job");
    }
}
