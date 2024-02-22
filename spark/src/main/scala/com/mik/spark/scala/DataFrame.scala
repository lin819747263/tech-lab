package com.mik.spark.scala

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{DateType, IntegerType, StringType, StructField, StructType}

object DataFrame {

  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().master("local").appName("demo_spark").getOrCreate()
    val sc = sparkSession.sparkContext
    val sqlContext = sparkSession.sparkContext

    val schema = StructType(List(
      StructField("integer_column", IntegerType, nullable = false),
      StructField("string_column", StringType, nullable = true),
      StructField("date_column", DateType, nullable = true)
    ))



    val rdd = sc.parallelize(Seq(
      Row(1, "First Value", java.sql.Date.valueOf("2010-01-01")),
      Row(2, "Second Value", java.sql.Date.valueOf("2010-02-01"))
    ))


    val df = sparkSession.createDataFrame(rdd, schema)

    df.show()
  }

}
