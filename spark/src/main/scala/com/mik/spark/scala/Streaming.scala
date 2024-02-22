package com.mik.spark.scala

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object Streaming {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]").setAppName("stream")
    val sc = new StreamingContext(conf, Seconds(10))
    val data = sc.socketTextStream("localhost", 8888)

    data
      .flatMap(_.split(","))
      .map((_,1))
      .groupByKey()
      .print()

    sc.start()
    sc.awaitTermination()

  }

}
