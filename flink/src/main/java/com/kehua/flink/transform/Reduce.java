package com.kehua.flink.transform;

import com.kehua.flink.source.VideoOrder;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Date;

public class Reduce {

    public static void main(String[] args) throws Exception {
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
        KeyedStream<VideoOrder, Integer> keyedStream = ds.keyBy(VideoOrder::getUserId);

        DataStream<VideoOrder> sumDS =   keyedStream.reduce(new ReduceFunction<VideoOrder>() {
            @Override
            public VideoOrder reduce(VideoOrder t0, VideoOrder t1) throws Exception {
                VideoOrder order = new VideoOrder();
                order.setTitle(t1.getTitle());
                order.setMoney(t0.getMoney() + t1.getMoney());
                return order;
            }
        });
        sumDS.print();

        env.execute("custom source job");
    }
}
