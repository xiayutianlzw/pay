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


}
