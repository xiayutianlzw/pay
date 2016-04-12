package sdk.weixin

import scala.xml.{PCData, Elem}

/**
 * User: Liboren's.
 * Date: 2015/12/4.
 * Time: 15:50.
 */
object XmlUtil {

  /**统一下单接口请求参数**/
  def buildUnifiedOrderResponse(param:UnifiedOrderParam) = {
    <xml>
      <appid>{new PCData(param.appid)}</appid>
      <mch_id>{new PCData(param.mch_id)}</mch_id>
      <device_info>{new PCData(param.device_info)}</device_info>
      <nonce_str>{new PCData(param.nonce_str)}</nonce_str>
      <sign>{new PCData(param.sign)}</sign>
      <body>{new PCData(param.body)}</body>
      <detail>{new PCData(param.detail)}</detail>
      <attach>{new PCData(param.attach)}</attach>
      <out_trade_no>{new PCData(param.out_trade_no)}</out_trade_no>
      <fee_type>{new PCData(param.fee_type)}</fee_type>
      <total_fee>{new PCData(param.total_fee)}</total_fee>
      <spbill_create_ip>{new PCData(param.spbill_create_ip)}</spbill_create_ip>
      <time_start>{new PCData(param.time_start)}</time_start>
      <time_expire>{new PCData(param.time_expire)}</time_expire>
      <goods_tag>{new PCData(param.goods_tag)}</goods_tag>
      <notify_url>{new PCData(param.notify_url)}</notify_url>
      <trade_type>{new PCData(param.trade_type)}</trade_type>
      <product_id>{new PCData(param.product_id)}</product_id>
      <limit_pay>{new PCData(param.limit_pay)}</limit_pay>
      <openid>{new PCData(param.openid)}</openid>
    </xml>
  }

  /**查询订单接口请求参数**/
  def buildSearchOrderResponse(param:SearchOrderParam) = {
    <xml>
      <appid>{new PCData(param.appid)}</appid>
      <mch_id>{new PCData(param.mch_id)}</mch_id>
      <transaction_id>{new PCData(param.transaction_id)}</transaction_id>
      <out_trade_no>{new PCData(param.out_trade_no)}</out_trade_no>
      <nonce_str>{new PCData(param.nonce_str)}</nonce_str>
      <sign>{new PCData(param.sign)}</sign>
    </xml>
  }

  /**支付结果返回参数**/
  def buildInformResponse(code:String,msg:String) = {
    <xml>
      <return_code>{new PCData(code)}</return_code>
      <return_msg>{new PCData(msg)}</return_msg>
    </xml>
  }


  //  def toXML(elem:(String,String)) = {
  //    s"<${elem._1}>${new PCData(elem._2)}</${elem._1}>"
  //  }
}

case class UnifiedOrderParam(
                              appid:String,
                              mch_id:String,
                              device_info:String,
                              nonce_str:String,
                              sign:String,
                              body:String,
                              detail:String,
                              attach:String,
                              out_trade_no:String,
                              fee_type:String,
                              total_fee:String,
                              spbill_create_ip:String,
                              time_start:String,
                              time_expire:String,
                              goods_tag:String,
                              notify_url:String,
                              trade_type:String,
                              product_id:String,
                              limit_pay:String,
                              openid:String
                              )

case class SearchOrderParam(
                             appid:String,
                             mch_id:String,
                             transaction_id:String,
                             nonce_str:String,
                             sign:String,
                             out_trade_no:String
                             )

