package com.kehua.flink;

import com.kehua.flink.sink.MysqlSink;
import com.kehua.flink.source.CustomerSource;
import com.kehua.flink.source.VideoOrder;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class FlinkMysql {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<VideoOrder> videoOrderDataStream = env.addSource(new CustomerSource());
        videoOrderDataStream.addSink(new MysqlSink());
//        videoOrderDataStream.print();
        //DataStream需要调用execute,可以取个名称
        env.execute("custom source job");
    }
}
