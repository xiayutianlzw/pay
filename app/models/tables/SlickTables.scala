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
  lazy val schema: profile.SchemaDescription = tOrder.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table tOrder
   *  @param id Database column Id SqlType(BIGINT), AutoInc, PrimaryKey
   *  @param sid Database column sid SqlType(BIGINT), Default(0)
   *  @param service Database column service SqlType(VARCHAR), Length(255,true), Default()
   *  @param outTradeNo Database column out_trade_no SqlType(VARCHAR), Length(255,true), Default()
   *  @param tradeState Database column trade_state SqlType(INT), Default(0)
   *  @param tradeMode Database column trade_mode SqlType(VARCHAR), Length(255,true), Default()
   *  @param fee Database column fee SqlType(FLOAT), Default(0.0)
   *  @param timestamp Database column timestamp SqlType(BIGINT), Default(0)
   *  @param tradeNo Database column trade_no SqlType(VARCHAR), Length(255,true), Default() */
  case class rOrder(id: Long, sid: Long = 0L, service: String = "", outTradeNo: String = "", tradeState: Int = 0, tradeMode: String = "", fee: Float = 0.0F, timestamp: Long = 0L, tradeNo: String = "")
  /** GetResult implicit for fetching rOrder objects using plain SQL queries */
  implicit def GetResultrOrder(implicit e0: GR[Long], e1: GR[String], e2: GR[Int], e3: GR[Float]): GR[rOrder] = GR{
    prs => import prs._
    rOrder.tupled((<<[Long], <<[Long], <<[String], <<[String], <<[Int], <<[String], <<[Float], <<[Long], <<[String]))
  }
  /** Table description of table order. Objects of this class serve as prototypes for rows in queries. */
  class tOrder(_tableTag: Tag) extends Table[rOrder](_tableTag, "order") {
    def * = (id, sid, service, outTradeNo, tradeState, tradeMode, fee, timestamp, tradeNo) <> (rOrder.tupled, rOrder.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(sid), Rep.Some(service), Rep.Some(outTradeNo), Rep.Some(tradeState), Rep.Some(tradeMode), Rep.Some(fee), Rep.Some(timestamp), Rep.Some(tradeNo)).shaped.<>({r=>import r._; _1.map(_=> rOrder.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column Id SqlType(BIGINT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("Id", O.AutoInc, O.PrimaryKey)
    /** Database column sid SqlType(BIGINT), Default(0) */
    val sid: Rep[Long] = column[Long]("sid", O.Default(0L))
    /** Database column service SqlType(VARCHAR), Length(255,true), Default() */
    val service: Rep[String] = column[String]("service", O.Length(255,varying=true), O.Default(""))
    /** Database column out_trade_no SqlType(VARCHAR), Length(255,true), Default() */
    val outTradeNo: Rep[String] = column[String]("out_trade_no", O.Length(255,varying=true), O.Default(""))
    /** Database column trade_state SqlType(INT), Default(0) */
    val tradeState: Rep[Int] = column[Int]("trade_state", O.Default(0))
    /** Database column trade_mode SqlType(VARCHAR), Length(255,true), Default() */
    val tradeMode: Rep[String] = column[String]("trade_mode", O.Length(255,varying=true), O.Default(""))
    /** Database column fee SqlType(FLOAT), Default(0.0) */
    val fee: Rep[Float] = column[Float]("fee", O.Default(0.0F))
    /** Database column timestamp SqlType(BIGINT), Default(0) */
    val timestamp: Rep[Long] = column[Long]("timestamp", O.Default(0L))
    /** Database column trade_no SqlType(VARCHAR), Length(255,true), Default() */
    val tradeNo: Rep[String] = column[String]("trade_no", O.Length(255,varying=true), O.Default(""))
  }
  /** Collection-like TableQuery object for table tOrder */
  lazy val tOrder = new TableQuery(tag => new tOrder(tag))
}
