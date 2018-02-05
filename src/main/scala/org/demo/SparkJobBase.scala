package org.demo

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}

object SparkJobBase {

  def main(args: Array[String]): Unit = {
    // SparkSession is the entry point for reading data, similar to the old SQLContext.read.
    implicit val spark = SparkSession.builder().appName("LongTweets").master("local").getOrCreate()

    val tweetsSchema = StructType(Array(StructField("timestamp", LongType, true), StructField("username", StringType, true), StructField("tweet", StringType, true)))

    val lines = spark.read.option("header", "false").schema(tweetsSchema).csv("./src/test/resources/tweets.csv")

    val longTweets = LongTweetsFilter.filterLongTweets(lines)

    longTweets.collect().foreach(println)

    spark.stop()
  }

}
