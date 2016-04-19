package models.dao

import com.google.inject.{Singleton, Inject}
import play.api.db.slick.{HasDatabaseConfigProvider, DatabaseConfigProvider}
import slick.driver.JdbcProfile
import models.tables.SlickTables

/**
 * Created by liuziwei on 2016/4/15.
 */
@Singleton
class AlipayDao @Inject()(
                           protected val dbConfigProvider: DatabaseConfigProvider
                           ) extends HasDatabaseConfigProvider[JdbcProfile] {

  import slick.driver.MySQLDriver.api._

  private [this] val alipay = SlickTables.tAlipay


  def createOrder(timestamp:Long,tradeNo:String,outTradeNo:String,state:Int) = db.run(
  alipay.map(i => (i.timestamp,i.tradeNo,i.outTradeNo,i.state)) +=
    (timestamp,tradeNo,outTradeNo,state)
  ).mapTo[Int]

  def findByTradeNo(tradeNo:String) = db.run(
  alipay.filter(_.tradeNo === tradeNo).map(_.state).result.headOption
  )

}
