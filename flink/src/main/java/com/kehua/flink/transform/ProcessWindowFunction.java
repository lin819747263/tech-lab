package com.kehua.flink.transform;

import com.kehua.flink.source.CustomerSource;
import com.kehua.flink.source.VideoOrder;
import org.apache.commons.collections.IteratorUtils;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.List;
import java.util.stream.Collectors;

public class ProcessWindowFunction {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<VideoOrder> ds = env.addSource(new CustomerSource());

//        DataStream<VideoOrder> ds = env.fromElements(
//                new VideoOrder("253","java",30,15,new Date()),
//                new VideoOrder("323","java",30,5,new Date()),
//                new VideoOrder("42","java",30,5,new Date()),
//                new VideoOrder("543","springboot",21,5,new Date()),
//                new VideoOrder("423","redis",40,5,new Date()),
//                new VideoOrder("15","redis",40,5,new Date()),
//                new VideoOrder("312","springcloud",521,5,new Date()),
//                new VideoOrder("125","kafka",1,55,new Date())
//        );

        SingleOutputStreamOperator<VideoOrder> aggDS = ds.keyBy(new KeySelector<VideoOrder, String>() {
            @Override
            public String getKey(VideoOrder value) throws Exception {
                return value.getTitle();
            }
        }).window(SlidingProcessingTimeWindows.of(Time.seconds(10),Time.seconds(5)))
                .process(new org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction<VideoOrder, VideoOrder, String, TimeWindow>() {
                    @Override
                    public void process(String s, Context context, Iterable<VideoOrder> iterable, Collector<VideoOrder> collector) throws Exception {
                        List<VideoOrder> list = IteratorUtils.toList(iterable.iterator());
                        int total =list.stream().collect(Collectors.summingInt(VideoOrder::getMoney)).intValue();
                        VideoOrder videoOrder = new VideoOrder();
                        videoOrder.setMoney(total);
                        videoOrder.setTitle(list.get(0).getTitle());
                        videoOrder.setCreateTime(list.get(0).getCreateTime());
                        collector.collect(videoOrder);
                    }
                });

        aggDS.print();
        //DataStream需要调用execute,可以取个名称
        env.execute("data stream job");
    }
}
