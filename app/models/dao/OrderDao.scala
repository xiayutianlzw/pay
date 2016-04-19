package models.dao

import com.google.inject.{Singleton, Inject}
import play.api.db.slick.{HasDatabaseConfigProvider, DatabaseConfigProvider}
import slick.driver.JdbcProfile
import models.tables.SlickTables
import common.Constants

/**
 * Created by liuziwei on 2016/4/12.
 */
@Singleton
class OrderDao @Inject()(
                          protected val dbConfigProvider: DatabaseConfigProvider
                          ) extends HasDatabaseConfigProvider[JdbcProfile] {

  import slick.driver.MySQLDriver.api._

  private [this] val order = SlickTables.tOrder

  def isProcessed(outTradeNo:String) = db.run(
  order.filter(_.outTradeNo === outTradeNo).map(_.isProcessed == Constants.orderIsProcessed).result.head
  )

  def createOrder(outTradeNo:String,fee:Float,sid:Long,appid:String,inTradeNo:String,tradeMode:Int,timestamp:Long) = db.run(
  order.map(i => (i.outTradeNo,i.fee,i.isProcessed,i.sid,i.appid,i.inTradeNo,i.timestamp,i.tradeMode)) +=
    (outTradeNo,fee,Constants.orderNotProcessed,sid,appid,inTradeNo,timestamp,tradeMode)
  ).mapTo[Int]
}
