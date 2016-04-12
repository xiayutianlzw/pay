package models.dao

import com.google.inject.Inject
import play.api.db.slick.{HasDatabaseConfigProvider, DatabaseConfigProvider}
import slick.driver.JdbcProfile
import models.tables.SlickTables

/**
 * Created by liuziwei on 2016/4/12.
 */
class OrderDao @Inject()(
                          protected val dbConfigProvider: DatabaseConfigProvider
                          ) extends HasDatabaseConfigProvider[JdbcProfile] {

  import slick.driver.MySQLDriver.api._

  private [this] val order = SlickTables.rOrder
}
