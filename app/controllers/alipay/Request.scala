package controllers.alipay

import java.net.URLEncoder

import org.slf4j.LoggerFactory
import sdk.alipay.config
import sdk.alipay.util
import play.api.mvc._
import com.google.inject.Inject
import collection.JavaConversions._
import scala.concurrent.Future

/**
 * Created by liuziwei on 2016/3/8.
 */
class Request @Inject() extends Controller{
  private val log = LoggerFactory.getLogger(this.getClass)
  val gateway = "https://mapi.alipay.com/gateway.do"

  def buildPhonePayPara(partner: String,
                         sellerId: String,
                         tradeNo: String,
                         totalFee: String,
                         subject: String) = {
    scala.collection.mutable.Map(
      "service" -> "alipay.wap.create.direct.pay.by.user",
      "partner" -> partner, //支付宝账号
      //  "seller_email" -> config.AlipayConfig.seller_email, // 卖家邮箱
      "_input_charset" -> config.AlipayConfig.input_charset,
      //"notify_url" -> "http://guomao.neoap.com/alipayTest/payasync", //异步通知页面
      //"return_url" -> "http://guomao.neoap.com/alipayTest/paysync", //跳转同步通知
      "seller_id" -> sellerId, //卖家支付宝账号
      "payment_type" -> config.AlipayConfig.payment_type, //支付类型
      "body" -> "body", //商品描述，可空
      "show_url" -> "url", //商品展示网址,可空
      "it_b_pay" -> config.AlipayConfig.it_b_pay, //超时时间  30分钟
      //  "extern_token" -> config.AlipayConfig.extern_token //手机支付宝token,可空
      //  "key" -> config.AlipayConfig.private_key
      "out_trade_no" -> tradeNo,
      "total_fee" -> totalFee,
      "subject" -> subject
    )
  }

  /**手机网站支付**/
  def phonePay(partner: String,
               sellerId: String,
               outTradeNo: String = "test10002",
               totalFee: String = "0.01",
               subject: String = "GuoMao pay test") = Action.async{

    log.debug("outTradeNo:"+outTradeNo+" totalFee:"+totalFee+" subject:"+subject)
    val newOutTradeNo ="test"+System.currentTimeMillis().toString
    val newSubject = "GuoMao pay test"
    val subjectEncode = URLEncoder.encode(newSubject,"utf-8").replaceAll("\\+","%20") // encode方法会把空格编码成加号（而不是%20），解码时会把加号和%20都解码为空格，因此这里编码时手动把加号替换成%20
    log.debug("subjectEncode:"+subjectEncode)//传输的时候需要是编码后的值
    val para = buildPhonePayPara(partner,sellerId,newOutTradeNo,totalFee,subjectEncode)
    log.debug("pay_parameter:"+para)


    val sPara = util.AlipayCore.paraFilter(para)
    val content = util.AlipayCore.createLinkString(para)
    log.debug("content:"+content)
    val mysign = util.AlipaySubmit.buildRequestMysign(sPara)
    log.debug("sign:"+mysign)
    val url = gateway+"?"+content+"&sign="+mysign+"&sign_type="+config.AlipayConfig.sign_type
    log.debug(url)
    Future.successful(Redirect(url))
  }

  /**即时到账**/
  def directPay(partner: String,
                sellerId: String,
                outTradeNo: String = "test10002",
                totalFee: String = "0.01",
                subject: String = "GuoMao pay test") = Action.async{
    log.debug("outTradeNo:"+outTradeNo+" totalFee:"+totalFee+" subject:"+subject)
    val newOutTradeNo ="test"+System.currentTimeMillis().toString
    val newSubject = "GuoMao pay test"
    val subjectEncode = URLEncoder.encode(newSubject,"utf-8").replaceAll("\\+","%20") // encode方法会把空格编码成加号（而不是%20），解码时会把加号和%20都解码为空格，因此这里编码时手动把加号替换成%20
    log.debug("subjectEncode:"+subjectEncode)//传输的时候需要是编码后的值
    val para = buildPhonePayPara(partner,sellerId,newOutTradeNo,totalFee,subjectEncode)
    log.debug("pay_parameter:"+para)


    val sPara = util.AlipayCore.paraFilter(para)
    val content = util.AlipayCore.createLinkString(para)
    log.debug("content:"+content)
    val mysign = util.AlipaySubmit.buildRequestMysign(sPara)
    log.debug("sign:"+mysign)
    val url = gateway+"?"+content+"&sign="+mysign+"&sign_type="+config.AlipayConfig.sign_type
    log.debug(url)
    Future.successful(Redirect(url))
  }

}
