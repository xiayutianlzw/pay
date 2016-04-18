package controllers.alipay

import play.api.Logger
import play.api.mvc.Action
import play.api.mvc.Controller
import com.google.inject.{Inject,Singleton}
import models.dao.OrderDao
import collection.JavaConversions._
import sdk.alipay.util.AlipayNotify
import common.Constants
import scala.concurrent.Future
/**
 * Created by liuziwei on 2016/4/12.
 */
@Singleton
class Redirect @Inject()(
                        orderDao: OrderDao
                          ) extends Controller{

  private[this] val log = Logger(this.getClass)

  def webPayReturnUrl = Action.async{ implicit request =>
    val requestPara = request.queryString.map(i => (i._1,i._2.head))
    val outTradeNo = request.getQueryString("out_trade_no").getOrElse("")
    val tradeStatus = request.getQueryString("trade_status").getOrElse("")
    val varifyRes = AlipayNotify.verify(requestPara)
    if(varifyRes){
      if(tradeStatus == "TRADE_FINISHED" || tradeStatus == "TRADE_SUCCESS"){

      }else{

      }
      orderDao.isProcessed(outTradeNo)
      Future.successful(Redirect(Constants.errorUrl))
    }else{
      Future.successful(Redirect(Constants.errorUrl))
    }
  }
}
