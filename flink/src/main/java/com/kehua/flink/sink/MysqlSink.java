package com.kehua.flink.sink;

import com.kehua.flink.source.VideoOrder;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class MysqlSink extends RichSinkFunction<VideoOrder> {

    private Connection conn = null;
    private PreparedStatement ps = null;

    @Override
    public void open(Configuration parameters) throws Exception {
        conn = DriverManager.getConnection("jdbc:mysql://192.168.68.118:3306/necp_test?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=Asia/Shanghai&useSSL=false", "root", "satellite");
        String sql = "INSERT INTO `video_order` (`user_id`, `money`, `title`, `trade_no`, `create_time`) VALUES(?,?,?,?,?);";
        ps = conn.prepareStatement(sql);
    }

    @Override
    public void close() throws Exception {
        if (conn != null) {
            conn.close();
        }
        if (ps != null) {
            ps.close();
        }
    }

    @Override
    public void invoke(VideoOrder videoOrder, Context context) throws Exception {
        ps.setInt(1,videoOrder.getUserId());
        ps.setInt(2,videoOrder.getMoney());
        ps.setString(3,videoOrder.getTitle());
        ps.setString(4,videoOrder.getTradeNo());
        ps.setDate(5,new Date(videoOrder.getCreateTime().getTime()));
        ps.executeUpdate();
    }
}
