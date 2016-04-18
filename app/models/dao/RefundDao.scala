package models.dao

import com.google.inject.{Inject,Singleton}
import play.api.db.slick.{HasDatabaseConfigProvider, DatabaseConfigProvider}
import slick.driver.JdbcProfile
import models.tables.SlickTables
import common.Constants

/**
 * Created by liuziwei on 2016/4/14.
 */
@Singleton
class RefundDao @Inject()(
                           protected val dbConfigProvider: DatabaseConfigProvider
                           ) extends HasDatabaseConfigProvider[JdbcProfile] {

  import slick.driver.MySQLDriver.api._

  private[this] val refund = SlickTables.tRefund


}
