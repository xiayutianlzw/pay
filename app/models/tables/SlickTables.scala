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
  lazy val schema: profile.SchemaDescription = tAlipay.schema ++ tOrder.schema ++ tRefund.schema
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

  /** Entity class storing rows of table tOrder
    *  @param outTradeNo Database column out_trade_no SqlType(VARCHAR), PrimaryKey, Length(255,true), Default()
    *  @param sid Database column sid SqlType(BIGINT), Default(0)
    *  @param service Database column service SqlType(VARCHAR), Length(255,true), Default()
    *  @param tradeMode Database column trade_mode SqlType(VARCHAR), Length(255,true), Default()
    *  @param fee Database column fee SqlType(FLOAT), Default(0.0)
    *  @param timestamp Database column timestamp SqlType(BIGINT), Default(0)
    *  @param isProcessed Database column is_processed SqlType(INT), Default(0) */
  case class rOrder(outTradeNo: String = "", sid: Long = 0L, service: String = "", tradeMode: String = "", fee: Float = 0.0F, timestamp: Long = 0L, isProcessed: Int = 0)
  /** GetResult implicit for fetching rOrder objects using plain SQL queries */
  implicit def GetResultrOrder(implicit e0: GR[String], e1: GR[Long], e2: GR[Float], e3: GR[Int]): GR[rOrder] = GR{
    prs => import prs._
      rOrder.tupled((<<[String], <<[Long], <<[String], <<[String], <<[Float], <<[Long], <<[Int]))
  }
  /** Table description of table order. Objects of this class serve as prototypes for rows in queries. */
  class tOrder(_tableTag: Tag) extends Table[rOrder](_tableTag, "order") {
    def * = (outTradeNo, sid, service, tradeMode, fee, timestamp, isProcessed) <> (rOrder.tupled, rOrder.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(outTradeNo), Rep.Some(sid), Rep.Some(service), Rep.Some(tradeMode), Rep.Some(fee), Rep.Some(timestamp), Rep.Some(isProcessed)).shaped.<>({r=>import r._; _1.map(_=> rOrder.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column out_trade_no SqlType(VARCHAR), PrimaryKey, Length(255,true), Default() */
    val outTradeNo: Rep[String] = column[String]("out_trade_no", O.PrimaryKey, O.Length(255,varying=true), O.Default(""))
    /** Database column sid SqlType(BIGINT), Default(0) */
    val sid: Rep[Long] = column[Long]("sid", O.Default(0L))
    /** Database column service SqlType(VARCHAR), Length(255,true), Default() */
    val service: Rep[String] = column[String]("service", O.Length(255,varying=true), O.Default(""))
    /** Database column trade_mode SqlType(VARCHAR), Length(255,true), Default() */
    val tradeMode: Rep[String] = column[String]("trade_mode", O.Length(255,varying=true), O.Default(""))
    /** Database column fee SqlType(FLOAT), Default(0.0) */
    val fee: Rep[Float] = column[Float]("fee", O.Default(0.0F))
    /** Database column timestamp SqlType(BIGINT), Default(0) */
    val timestamp: Rep[Long] = column[Long]("timestamp", O.Default(0L))
    /** Database column is_processed SqlType(INT), Default(0) */
    val isProcessed: Rep[Int] = column[Int]("is_processed", O.Default(0))
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
}
