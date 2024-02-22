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

public class WindowFunction {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<VideoOrder> ds = env.addSource(new CustomerSource());

        SingleOutputStreamOperator<VideoOrder> aggDS = ds.keyBy(new KeySelector<VideoOrder, String>() {
            @Override
            public String getKey(VideoOrder value) throws Exception {
                return value.getTitle();
            }
        }).window(SlidingProcessingTimeWindows.of(Time.seconds(10),Time.seconds(5)))
                .apply( new org.apache.flink.streaming.api.functions.windowing.WindowFunction<VideoOrder, VideoOrder, String, TimeWindow>() {
                    @Override
                    public void apply(String key, TimeWindow window, Iterable<VideoOrder> input, Collector<VideoOrder> out) throws Exception {
                        List<VideoOrder> list = IteratorUtils.toList(input.iterator());
                        int total =list.stream().collect(Collectors.summingInt(VideoOrder::getMoney)).intValue();
                        VideoOrder videoOrder = new VideoOrder();
                        videoOrder.setMoney(total);
                        videoOrder.setCreateTime(list.get(0).getCreateTime());
                        videoOrder.setTitle(list.get(0).getTitle());
                        out.collect(videoOrder);
                    }
                });

        aggDS.print();
        //DataStream需要调用execute,可以取个名称
        env.execute("data stream job");
    }
}
