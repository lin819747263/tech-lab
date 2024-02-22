package com.mik.flink.scala

import lombok.Data

import java.util.Date

@Data
class VideoOrder(p_tradeNo : String, p_title :String, p_money :Int, p_userId :Int, p_createTime : Date) {
  private var tradeNo = p_tradeNo
  private var title = p_title
  private var money = p_money
  private var userId = p_userId
  private var createTime = p_createTime
}
