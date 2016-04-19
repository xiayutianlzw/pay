package common

/**
 * Created by liuziwei on 2016/4/14.
 */
object Constants {

  val errorUrl = "" //第三方支付出现错误的报错页面
  val paySuccessUrl = "" //支付成功跳转提示页面
  val payFailUrl = "" //支付返回状态失败跳转提示页面

  val orderIsProcessed = 1
  val orderNotProcessed = 0

  object tradeMode{
    val alipay = 1
  }

  object alipay{
    val webReturnUrl = "http://buscome.neoap.com"
    val webNotifyUrl = "http://buscome.neoap.com"
    val phoneReturnUrl = "http://buscome.neoap.com"
    val phoneNotifyUrl = "http://buscome.neoap.com"
  }

  object alipayState{
    val success = 1 //可退款
    val finished = 2 //无法退款
    val pending = 3 //等待卖家收款（买家付款后，如果卖家账号被冻结）
    val closed = 4 //在指定时间段内未支付时关闭的交易； 在交易完成全额退款成功时关闭的交易。
    val waitBuyerPay = 5 //交易创建，等待买家付款。
  }
}
