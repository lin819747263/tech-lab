package com.mik.spark.scala

import org.apache.spark.sql.SparkSession

object WordCount {
  def main(args: Array[String]): Unit = {

    //
    val sparkSession = SparkSession.builder().master("local").appName("demo_spark").getOrCreate()
    val spark = sparkSession.sparkContext


//    val data = Array(12,15,56,89,45,400)
//    val dataset = sparkSession.sparkContext.parallelize(data)


//    val sparkConf = new SparkConf()
//    sparkConf.setMaster("local")   //本地单线程运行
//    sparkConf.setAppName("testJob")
//
//    val spark = SparkContext.getOrCreate(sparkConf)


    val data = spark.textFile("E:\\test\\111.txt")

    val result = data
      .flatMap(_.split(","))
      .map((_,1))
      .countByKey()

    println(result)
  }
}
