package controllers.api

import com.google.inject.{Inject, Singleton}
import controllers.ActionUtils
import play.api.mvc.{Controller,Action}
import play.api.Logger
import common.Constants
import scala.concurrent.Future
import util.{SecureUtil,HttpUtil}
import models.dao.OrderDao
import common.errorcode.ApiErrorCode
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by liuziwei on 2016/4/15.
 */
@Singleton
class PayApi @Inject()(
                        val actionUtils: ActionUtils,
                      httpUtil: HttpUtil,
                      orderDao: OrderDao
                        ) extends Controller{

  import actionUtils._

  private [this] val log = Logger(this.getClass)

  def pay(
         appid:String,
         service:String,
         sid:Long,
         inTradeNo:String,
         fee:Float,
         tradeMode:Int,
         sn:String,
         nonce:String,
         signature:String) = checkSignature(
  appid,sn,List(appid,service,sid.toString,inTradeNo,fee.toString,tradeMode.toString,sn,nonce),signature
  ){
    loggingAction.async{
      val outTradeNo = appid+inTradeNo
      orderDao.createOrder(outTradeNo,fee,sid,appid,inTradeNo,tradeMode,System.currentTimeMillis()).map{add =>
        if(add > 0){
          tradeMode match{
            case Constants.tradeMode.alipay =>
              val url = "http://localhost:9000/pay/alipay/payment?partner=2088411898385492" +
                "&sellerId=2088411898385492" +
                s"&outTradeNo=$outTradeNo" +
                s"&totalFee=$fee" +
                "&subject=测试"
              Redirect(url)
          }
        }else{
          Ok(ApiErrorCode.AddOrderFail)
        }
      }
    }
  }

  def test = Action.async{
    val appid = "l"
    val service = "l"
    val sid = 10001
    val outTradeNo = "11111"
    val fee = 0.01
    val tradeMode = Constants.tradeMode.alipay
    val sn = SecureUtil.nonceStr(3)
    val nonce=SecureUtil.nonceStr(4)
    val param = List(appid,service,sid.toString,outTradeNo,fee.toString,tradeMode.toString,sn,nonce)
    val signature = SecureUtil.generateSignature(param,"ffff")
    val url = s"http://localhost:9000/pay/api/payment?appid=$appid" +
      s"&service=$service" +
      s"&sid=$sid" +
      s"&outTradeNo=$outTradeNo" +
      s"&fee=$fee" +
      s"&tradeMode=$tradeMode" +
      s"&sn=$sn" +
      s"&nonce=$nonce" +
      s"&signature=$signature"
    Future.successful(Redirect(url))
  }
}
