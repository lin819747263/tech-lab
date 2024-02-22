package com.kehua.flink.watermark;

import com.kehua.flink.source.CustomerSource;
import com.kehua.flink.source.VideoOrder;
import org.apache.flink.runtime.state.hashmap.HashMapStateBackend;
import org.apache.flink.runtime.state.storage.FileSystemCheckpointStorage;
import org.apache.flink.runtime.state.storage.JobManagerCheckpointStorage;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class WaterMark {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<VideoOrder> ds = env.addSource(new CustomerSource());
        env.getStateBackend();

        HashMapStateBackend hbk = new HashMapStateBackend();
        JobManagerCheckpointStorage jobManagerCheckpointStorage = new JobManagerCheckpointStorage();
        FileSystemCheckpointStorage systemCheckpointStorage = new FileSystemCheckpointStorage("");

//        SingleOutputStreamOperator<VideoOrder> aggDS = ds.keyBy(new KeySelector<VideoOrder, String>() {
//            @Override
//            public String getKey(VideoOrder value) throws Exception {
//                return value.getTitle();
//            }
//        }).window(SlidingProcessingTimeWindows.of(Time.seconds(10),Time.seconds(5))).allowedLateness(Time.seconds(30)).;

//        aggDS.print();

        //DataStream需要调用execute,可以取个名称
        env.execute("data stream job");
    }
}
