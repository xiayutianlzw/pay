package models.tables
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object SlickTables extends {
  val profile = slick.driver.MySQLDriver
} with SlickTables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait SlickTables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = tAlipay.schema ++ tApp.schema ++ tOrder.schema ++ tRefund.schema ++ tUser.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table tAlipay
    *  @param outTradeNo Database column out_trade_no SqlType(VARCHAR), Length(255,true), Default()
    *  @param tradeNo Database column trade_no SqlType(VARCHAR), PrimaryKey, Length(255,true), Default()
    *  @param state Database column state SqlType(INT), Default(0)
    *  @param fee Database column fee SqlType(FLOAT), Default(0.0)
    *  @param timestamp Database column timestamp SqlType(BIGINT), Default(0) */
  case class rAlipay(outTradeNo: String = "", tradeNo: String = "", state: Int = 0, fee: Float = 0.0F, timestamp: Long = 0L)
  /** GetResult implicit for fetching rAlipay objects using plain SQL queries */
  implicit def GetResultrAlipay(implicit e0: GR[String], e1: GR[Int], e2: GR[Float], e3: GR[Long]): GR[rAlipay] = GR{
    prs => import prs._
      rAlipay.tupled((<<[String], <<[String], <<[Int], <<[Float], <<[Long]))
  }
  /** Table description of table alipay. Objects of this class serve as prototypes for rows in queries. */
  class tAlipay(_tableTag: Tag) extends Table[rAlipay](_tableTag, "alipay") {
    def * = (outTradeNo, tradeNo, state, fee, timestamp) <> (rAlipay.tupled, rAlipay.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(outTradeNo), Rep.Some(tradeNo), Rep.Some(state), Rep.Some(fee), Rep.Some(timestamp)).shaped.<>({r=>import r._; _1.map(_=> rAlipay.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column out_trade_no SqlType(VARCHAR), Length(255,true), Default() */
    val outTradeNo: Rep[String] = column[String]("out_trade_no", O.Length(255,varying=true), O.Default(""))
    /** Database column trade_no SqlType(VARCHAR), PrimaryKey, Length(255,true), Default() */
    val tradeNo: Rep[String] = column[String]("trade_no", O.PrimaryKey, O.Length(255,varying=true), O.Default(""))
    /** Database column state SqlType(INT), Default(0) */
    val state: Rep[Int] = column[Int]("state", O.Default(0))
    /** Database column fee SqlType(FLOAT), Default(0.0) */
    val fee: Rep[Float] = column[Float]("fee", O.Default(0.0F))
    /** Database column timestamp SqlType(BIGINT), Default(0) */
    val timestamp: Rep[Long] = column[Long]("timestamp", O.Default(0L))

    /** Foreign key referencing tOrder (database name out_trade_no) */
    lazy val tOrderFk = foreignKey("out_trade_no", outTradeNo, tOrder)(r => r.outTradeNo, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table tAlipay */
  lazy val tAlipay = new TableQuery(tag => new tAlipay(tag))

  /** Entity class storing rows of table tApp
    *  @param id Database column Id SqlType(BIGINT), AutoInc, PrimaryKey
    *  @param appid Database column appId SqlType(VARCHAR), Length(255,true), Default()
    *  @param sid Database column sid SqlType(BIGINT), Default(0)
    *  @param secureKey Database column secure_key SqlType(VARCHAR), Length(255,true), Default() */
  case class rApp(id: Long, appid: String = "", sid: Long = 0L, secureKey: String = "")
  /** GetResult implicit for fetching rApp objects using plain SQL queries */
  implicit def GetResultrApp(implicit e0: GR[Long], e1: GR[String]): GR[rApp] = GR{
    prs => import prs._
      rApp.tupled((<<[Long], <<[String], <<[Long], <<[String]))
  }
  /** Table description of table app. Objects of this class serve as prototypes for rows in queries. */
  class tApp(_tableTag: Tag) extends Table[rApp](_tableTag, "app") {
    def * = (id, appid, sid, secureKey) <> (rApp.tupled, rApp.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(appid), Rep.Some(sid), Rep.Some(secureKey)).shaped.<>({r=>import r._; _1.map(_=> rApp.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column Id SqlType(BIGINT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("Id", O.AutoInc, O.PrimaryKey)
    /** Database column appId SqlType(VARCHAR), Length(255,true), Default() */
    val appid: Rep[String] = column[String]("appId", O.Length(255,varying=true), O.Default(""))
    /** Database column sid SqlType(BIGINT), Default(0) */
    val sid: Rep[Long] = column[Long]("sid", O.Default(0L))
    /** Database column secure_key SqlType(VARCHAR), Length(255,true), Default() */
    val secureKey: Rep[String] = column[String]("secure_key", O.Length(255,varying=true), O.Default(""))

    /** Foreign key referencing tUser (database name sid) */
    lazy val tUserFk = foreignKey("sid", sid, tUser)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table tApp */
  lazy val tApp = new TableQuery(tag => new tApp(tag))

  /** Entity class storing rows of table tOrder
    *  @param outTradeNo Database column out_trade_no SqlType(VARCHAR), PrimaryKey, Length(255,true), Default()
    *  @param sid Database column sid SqlType(BIGINT), Default(0)
    *  @param tradeMode Database column trade_mode SqlType(INT), Default(0)
    *  @param fee Database column fee SqlType(FLOAT), Default(0.0)
    *  @param timestamp Database column timestamp SqlType(BIGINT), Default(0)
    *  @param isProcessed Database column is_processed SqlType(INT), Default(0)
    *  @param appid Database column appid SqlType(VARCHAR), Length(255,true), Default()
    *  @param inTradeNo Database column in_trade_no SqlType(VARCHAR), Length(255,true), Default() */
  case class rOrder(outTradeNo: String = "", sid: Long = 0L, tradeMode: Int = 0, fee: Float = 0.0F, timestamp: Long = 0L, isProcessed: Int = 0, appid: String = "", inTradeNo: String = "")
  /** GetResult implicit for fetching rOrder objects using plain SQL queries */
  implicit def GetResultrOrder(implicit e0: GR[String], e1: GR[Long], e2: GR[Int], e3: GR[Float]): GR[rOrder] = GR{
    prs => import prs._
      rOrder.tupled((<<[String], <<[Long], <<[Int], <<[Float], <<[Long], <<[Int], <<[String], <<[String]))
  }
  /** Table description of table order. Objects of this class serve as prototypes for rows in queries. */
  class tOrder(_tableTag: Tag) extends Table[rOrder](_tableTag, "order") {
    def * = (outTradeNo, sid, tradeMode, fee, timestamp, isProcessed, appid, inTradeNo) <> (rOrder.tupled, rOrder.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(outTradeNo), Rep.Some(sid), Rep.Some(tradeMode), Rep.Some(fee), Rep.Some(timestamp), Rep.Some(isProcessed), Rep.Some(appid), Rep.Some(inTradeNo)).shaped.<>({r=>import r._; _1.map(_=> rOrder.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column out_trade_no SqlType(VARCHAR), PrimaryKey, Length(255,true), Default() */
    val outTradeNo: Rep[String] = column[String]("out_trade_no", O.PrimaryKey, O.Length(255,varying=true), O.Default(""))
    /** Database column sid SqlType(BIGINT), Default(0) */
    val sid: Rep[Long] = column[Long]("sid", O.Default(0L))
    /** Database column trade_mode SqlType(INT), Default(0) */
    val tradeMode: Rep[Int] = column[Int]("trade_mode", O.Default(0))
    /** Database column fee SqlType(FLOAT), Default(0.0) */
    val fee: Rep[Float] = column[Float]("fee", O.Default(0.0F))
    /** Database column timestamp SqlType(BIGINT), Default(0) */
    val timestamp: Rep[Long] = column[Long]("timestamp", O.Default(0L))
    /** Database column is_processed SqlType(INT), Default(0) */
    val isProcessed: Rep[Int] = column[Int]("is_processed", O.Default(0))
    /** Database column appid SqlType(VARCHAR), Length(255,true), Default() */
    val appid: Rep[String] = column[String]("appid", O.Length(255,varying=true), O.Default(""))
    /** Database column in_trade_no SqlType(VARCHAR), Length(255,true), Default() */
    val inTradeNo: Rep[String] = column[String]("in_trade_no", O.Length(255,varying=true), O.Default(""))
  }
  /** Collection-like TableQuery object for table tOrder */
  lazy val tOrder = new TableQuery(tag => new tOrder(tag))

  /** Entity class storing rows of table tRefund
    *  @param id Database column Id SqlType(BIGINT), AutoInc, PrimaryKey
    *  @param outTradeNo Database column out_trade_no SqlType(VARCHAR), Length(255,true), Default()
    *  @param batchNo Database column batch_no SqlType(VARCHAR), Length(255,true), Default()
    *  @param reason Database column reason SqlType(VARCHAR), Length(255,true), Default()
    *  @param state Database column state SqlType(INT), Default(0)
    *  @param timestamp Database column timestamp SqlType(BIGINT), Default(0)
    *  @param sid Database column sid SqlType(BIGINT), Default(0)
    *  @param service Database column service SqlType(VARCHAR), Length(255,true), Default() */
  case class rRefund(id: Long, outTradeNo: String = "", batchNo: String = "", reason: String = "", state: Int = 0, timestamp: Long = 0L, sid: Long = 0L, service: String = "")
  /** GetResult implicit for fetching rRefund objects using plain SQL queries */
  implicit def GetResultrRefund(implicit e0: GR[Long], e1: GR[String], e2: GR[Int]): GR[rRefund] = GR{
    prs => import prs._
      rRefund.tupled((<<[Long], <<[String], <<[String], <<[String], <<[Int], <<[Long], <<[Long], <<[String]))
  }
  /** Table description of table refund. Objects of this class serve as prototypes for rows in queries. */
  class tRefund(_tableTag: Tag) extends Table[rRefund](_tableTag, "refund") {
    def * = (id, outTradeNo, batchNo, reason, state, timestamp, sid, service) <> (rRefund.tupled, rRefund.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(outTradeNo), Rep.Some(batchNo), Rep.Some(reason), Rep.Some(state), Rep.Some(timestamp), Rep.Some(sid), Rep.Some(service)).shaped.<>({r=>import r._; _1.map(_=> rRefund.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column Id SqlType(BIGINT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("Id", O.AutoInc, O.PrimaryKey)
    /** Database column out_trade_no SqlType(VARCHAR), Length(255,true), Default() */
    val outTradeNo: Rep[String] = column[String]("out_trade_no", O.Length(255,varying=true), O.Default(""))
    /** Database column batch_no SqlType(VARCHAR), Length(255,true), Default() */
    val batchNo: Rep[String] = column[String]("batch_no", O.Length(255,varying=true), O.Default(""))
    /** Database column reason SqlType(VARCHAR), Length(255,true), Default() */
    val reason: Rep[String] = column[String]("reason", O.Length(255,varying=true), O.Default(""))
    /** Database column state SqlType(INT), Default(0) */
    val state: Rep[Int] = column[Int]("state", O.Default(0))
    /** Database column timestamp SqlType(BIGINT), Default(0) */
    val timestamp: Rep[Long] = column[Long]("timestamp", O.Default(0L))
    /** Database column sid SqlType(BIGINT), Default(0) */
    val sid: Rep[Long] = column[Long]("sid", O.Default(0L))
    /** Database column service SqlType(VARCHAR), Length(255,true), Default() */
    val service: Rep[String] = column[String]("service", O.Length(255,varying=true), O.Default(""))

    /** Foreign key referencing tOrder (database name refund_ibfk_1) */
    lazy val tOrderFk = foreignKey("refund_ibfk_1", outTradeNo, tOrder)(r => r.outTradeNo, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table tRefund */
  lazy val tRefund = new TableQuery(tag => new tRefund(tag))

  /** Entity class storing rows of table tUser
    *  @param id Database column Id SqlType(BIGINT), AutoInc, PrimaryKey
    *  @param name Database column name SqlType(VARCHAR), Length(255,true), Default()
    *  @param secure Database column secure SqlType(VARCHAR), Length(255,true), Default()
    *  @param ip Database column ip SqlType(VARCHAR), Length(255,true), Default()
    *  @param timestamp Database column timestamp SqlType(BIGINT), Default(0) */
  case class rUser(id: Long, name: String = "", secure: String = "", ip: String = "", timestamp: Long = 0L)
  /** GetResult implicit for fetching rUser objects using plain SQL queries */
  implicit def GetResultrUser(implicit e0: GR[Long], e1: GR[String]): GR[rUser] = GR{
    prs => import prs._
      rUser.tupled((<<[Long], <<[String], <<[String], <<[String], <<[Long]))
  }
  /** Table description of table user. Objects of this class serve as prototypes for rows in queries. */
  class tUser(_tableTag: Tag) extends Table[rUser](_tableTag, "user") {
    def * = (id, name, secure, ip, timestamp) <> (rUser.tupled, rUser.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name), Rep.Some(secure), Rep.Some(ip), Rep.Some(timestamp)).shaped.<>({r=>import r._; _1.map(_=> rUser.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column Id SqlType(BIGINT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("Id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(255,true), Default() */
    val name: Rep[String] = column[String]("name", O.Length(255,varying=true), O.Default(""))
    /** Database column secure SqlType(VARCHAR), Length(255,true), Default() */
    val secure: Rep[String] = column[String]("secure", O.Length(255,varying=true), O.Default(""))
    /** Database column ip SqlType(VARCHAR), Length(255,true), Default() */
    val ip: Rep[String] = column[String]("ip", O.Length(255,varying=true), O.Default(""))
    /** Database column timestamp SqlType(BIGINT), Default(0) */
    val timestamp: Rep[Long] = column[Long]("timestamp", O.Default(0L))
  }
  /** Collection-like TableQuery object for table tUser */
  lazy val tUser = new TableQuery(tag => new tUser(tag))
}
