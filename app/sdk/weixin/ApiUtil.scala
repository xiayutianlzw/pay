package sdk.weixin

import com.google.inject.Inject
import org.slf4j.LoggerFactory
import play.api.libs.json.JsResultException
import scala.util.Random
import scala.xml._
import scala.concurrent.ExecutionContext.Implicits.global
import XmlUtil._
import util.TimeUtil._
import util.HttpUtil
/**
 * Created by liuziwei on 2016/4/12.
 */
class ApiUtil @Inject()(
                       httpUtil: HttpUtil
                         ) {


    private val log = LoggerFactory.getLogger(this.getClass)


    /**统一下单API接口，返回预支付交易回话标识(prepay_id)**/
    def unifiedOrderAPI(appid:String,mchID:String,outTradeNo:String,ip:String,goodsTag:String,openid:String) = {
      val url = "https://api.mch.weixin.qq.com/pay/unifiedorder"


      val curTime = System.currentTimeMillis()
      val timeStart = format(curTime,"yyyMMddHHmmss")
      val timeExpire = format(curTime + 10 * 60 * 1000,"yyyMMddHHmmss")
      val sign = util.MD5Util.createSignature(List(("appid","wxd930ea5d5a258f4f"),("mchID","10000100"),("body","iphone"),("detail","32g white")),"")
      //生成对应的XML格式
      val param = UnifiedOrderParam(
        appid, //微信分配的公众账号ID
        mchID, //微信支付分配的商户号
        "WEB", //终端设备号，注意：PC网页或公众号内支付请传"WEB"
        Random.nextString(30), //随即字符串，不长于32位，用随机数生成算法
        sign, //签名，用签名生成算法
        "body", //商品简要描述
        "detail", //商品详细描述
        "attch", //自定义数据
        outTradeNo, //商户内部订单号，不大于32，可包含字母，后台系统订单唯一标识
        "CNY", //货币类型
        "total_fee", //订单总金额
        ip, //用户ip地址
        timeStart, //订单生成时间，格式为yyyyMMddHHmmss
        timeExpire, //订单失效时间，生成时间-失效时间>5分钟
        "goods_tag", //商品标记，代金券或立减优惠功能的参数
        "notify_url", //接受微信支付异步通知的回调地址
        "trade_type", //交易类型
        "produce_id", //二维码中包含的商品ID，商户自行定义
        "no_credit", //不能使用信用卡支付
        openid //trade_type为JSAPI,必传、用户在公众号下的唯一标识
      )
      val postData = XmlUtil.buildUnifiedOrderResponse(param)
      httpUtil.postXMLRequestSend("unifiedOrderAPI", url, postData).map { xml =>
        log.debug("unifiedOrderAPI xml: " + xml)
        try {
          val returnCode = (xml \\ "return_code").text // SUCCESS\FAIL，通信标识，非交易标识，交易是否成功需要查看result_code来判断
          val returnMessage = (xml \\ "return_msg").text
          if (returnCode == "FAIL") {
            log.error("unifiedOrderAPI server error:" +returnMessage)
            Left(xml)
          } else {
            val appid = (xml \\ "appid").text
            val mchID = (xml \\ "mch_id").text
            val deviceInfo = (xml \\ "device_info").text
            val nonceStr = (xml \\ "nonce_str").text
            val sign = (xml \\ "sign").text //此处需校验签名
            val resultCode = (xml \\ "result_code").text

            if(resultCode == "SUCCESS"){
              val tradeType = (xml \\ "trade_type").text //接口调用提交的交易类型，有JSAPI,NATIVE,APP
              val prepayID = (xml \\ "prepay_id").text //预支付回话标识，有效期2小时
              val codeURL = (xml \\ "code_url").text //trade_type为NATIVE时有返回
              Right(prepayID)
            }
            else{
              val errCode = (xml \\ "err_code").text
              val errCodeDes = (xml \\ "err_code_des").text
              log.error(s"unifiedOrderAPI server error: err_code:${errCode} err_code_des:${errCodeDes}")
              Left(xml)
            }
          }
        } catch {
          case ex: JsResultException =>
            log.warn("unifiedOrderAPI xml parse error:" + xml)
            Left(xml)
        }
      }
    }

      /** 查询订单接口
        * 该接口提供所有微信支付订单的查询，商户可以通过该接口主动查询订单状态，完成下一步的业务逻辑。
          需要调用查询接口的情况：
          ◆ 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知；
          ◆ 调用支付接口后，返回系统错误或未知交易状态情况；
          ◆ 调用被扫支付API，返回USERPAYING的状态；
          ◆ 调用关单或撤销接口API之前，需确认支付状态；
        */
    def SearchOrderAPI(appid:String,mchID:String,outTradeNo:String,ip:String,goodsTag:String,openid:String) = {
      val url = "https://api.mch.weixin.qq.com/pay/orderquery"
      val sign = util.MD5Util.createSignature(List(("appid","wxd930ea5d5a258f4f"),("mchID","10000100"),("body","iphone"),("detail","32g white")),"")

        //生成对应的XML格式
      val param = SearchOrderParam(
        appid, //微信分配的公众账号ID
        mchID, //微信支付分配的商户号
        "transaction_id", //微信的订单号，优先使用，与mchID二选一
        "out_trade_no", //商户系统内部的订单号，当没提供transaction_id时需要传这个
        Random.nextString(30), //随即字符串，不长于32位，用随机数生成算法
        sign //签名，用签名生成算法
      )
      val postData = buildSearchOrderResponse(param)
      httpUtil.postXMLRequestSend("SearchOrderAPI", url, postData).map { xml =>
        log.debug("SearchOrderAPI xml: " + xml)
        try {
          val returnCode = (xml \\ "return_code").text // SUCCESS\FAIL，通信标识，非交易标识，交易是否成功需要查看result_code来判断
          val returnMessage = (xml \\ "return_msg").text
          if (returnCode == "FAIL") {
            log.error("SearchOrderAPI server error:" +returnMessage)
            Left(xml)
          } else {
            val appid = (xml \\ "appid").text
            val mchID = (xml \\ "mch_id").text

            val nonceStr = (xml \\ "nonce_str").text
            val sign = (xml \\ "sign").text //此处需校验签名
            val resultCode = (xml \\ "result_code").text

            if(resultCode == "SUCCESS"){
              val deviceInfo = (xml \\ "device_info").text //微信支付分配的终端设备号
              val openid = (xml \\ "openid").text //用户在商户appid下的唯一标识
              val isSubscribe = (xml \\ "is_subscribe").text //用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
              val tradeType = (xml \\ "trade_type").text //调用接口提交的交易类型，取值如下：JSAPI，NATIVE，APP，MICROPAY
              val tradeState = (xml \\ "trade_state").text //SUCCESS—支付成功 REFUND—转入退款 NOTPAY—未支付 CLOSED—已关闭 REVOKED—已撤销（刷卡支付） USERPAYING--用户支付中 PAYERROR--支付失败(其他原因，如银行返回失败)
              val bankType = (xml \\ "bank_type").text //银行类型
              val totalFee = (xml \\ "total_fee").text //订单总金额，单位为分
              val feeType = (xml \\ "fee_type").text //货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY
              val cashFee = (xml \\ "cash_fee").text //现金支付金额订单现金支付金额
              val cashFeeType = (xml \\ "cash_fee_type").text //货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY
              val couponFee = (xml \\ "coupon_fee").text //代金券或立减优惠”金额<=订单总金额，订单总金额-“代金券或立减优惠”金额=现金支付金额
              val couponCount = (xml \\ "coupon_count").text //代金券或立减优惠使用数量
              val couponBatchId$n = (xml \\ "coupon_batch_id_$n").text //代金券或立减优惠批次ID ,$n为下标，从0开始编号
              val couponId$n = (xml \\ "coupon_id_$n").text //代金券或立减优惠ID, $n为下标，从0开始编号
              val couponFee$n = (xml \\ "coupon_fee_$n").text //单个代金券或立减优惠支付金额, $n为下标，从0开始编号
              val transactionId = (xml \\ "transaction_id").text //微信支付订单号
              val outTradeNo = (xml \\ "out_trade_no").text //商户系统的订单号，与请求一致
              val attach = (xml \\ "attach").text //附加数据，原样返回
              val timeEnd = (xml \\ "time_end").text //订单支付时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010
              val tradeStateDesc = (xml \\ "trade_state_desc").text //对当前查询订单状态的描述和下一步操作的指引


              tradeState match {
                case "SUCCESS" => //支付成功
                  // TODO 返回支付结果

                case "REFUND" => //转入退款

                case "NOTPAY" => //未支付

                case "CLOSED" => //已关闭

                case "REVOKED" => //已撤销（刷卡支付）

                case "USERPAYING" => //用户支付中

                case "PAYERROR" => //支付失败(其他原因，如银行返回失败)

                case _ =>
              }
              Right("Ok")
            }
            else{
              val errCode = (xml \\ "err_code").text
              val errCodeDes = (xml \\ "err_code_des").text
              log.error(s"SearchOrderAPI server error: err_code:${errCode} err_code_des:${errCodeDes}")
              Left(xml)
            }
          }
        } catch {
          case ex: JsResultException =>
            log.warn("SearchOrderAPI xml parse error:" + xml)
            Left(xml)
        }
      }
    }

}
