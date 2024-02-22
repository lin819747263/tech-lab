package com.mik.flink.scala


import org.apache.flink.streaming.api.datastream.DataStream
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment

object Test {

  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment()

    //接受一个socket文本流
    val inputDataStream: DataStream[String] = env.socketTextStream("localhost", 8888)
    val resultDataStream: DataStream[(String,Int)] = inputDataStream
      .map((_, 1))
      .keyBy(0)
      .sum(1)
    resultDataStream.print()

    //启动任务执行
    env.execute("stream word count")





  }



}
