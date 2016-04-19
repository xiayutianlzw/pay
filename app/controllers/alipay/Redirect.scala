package controllers.alipay

import play.api.Logger
import play.api.mvc.Action
import play.api.mvc.Controller
import com.google.inject.{Inject,Singleton}
import models.dao.{OrderDao,AlipayDao}
import collection.JavaConversions._
import sdk.alipay.util.AlipayNotify
import common.Constants
import scala.concurrent.Future
/**
 * Created by liuziwei on 2016/4/12.
 */
@Singleton
class Redirect @Inject()(
                        orderDao: OrderDao,
                        alipayDao: AlipayDao
                          ) extends Controller{

  private[this] val log = Logger(this.getClass)

  def webPayReturnUrl = Action.async{ implicit request =>
    val requestPara = request.queryString.map(i => (i._1,i._2.head))
    val outTradeNo = request.getQueryString("out_trade_no").getOrElse("")
    val tradeStatus = request.getQueryString("trade_status").getOrElse("")
    val tradeNoOpt = request.getQueryString("trade_no")
    val varifyRes = AlipayNotify.verify(requestPara)
    if(varifyRes){
      if(tradeNoOpt.isDefined){
        val tradeNo = tradeNoOpt.get
        alipayDao.findByTradeNo(tradeNo).flatMap{find =>
          if(find.isDefined){
            val state = find.get
            if(state == Constants.alipayState.success || state == Constants.alipayState.finished){
              Future.successful(Redirect(Constants.paySuccessUrl))
            }else{
              Future.successful(Redirect(Constants.payFailUrl))
            }
          }else{
            tradeStatus match{
              case "TRADE_FINISHED" =>
                alipayDao.createOrder(System.currentTimeMillis(),tradeNo,outTradeNo,Constants.alipayState.finished).map{
                  res =>
                    if(res <= 0) {
                      log.error(s"add alipay order tradeNo=$tradeNo outTradeNo=$outTradeNo to database fail")
                    }
                    Redirect(Constants.paySuccessUrl)
                }
              case "TRADE_SUCCESS" =>
                alipayDao.createOrder(System.currentTimeMillis(),tradeNo,outTradeNo,Constants.alipayState.success).map{
                  res =>
                    if(res <= 0) {
                      log.error(s"add alipay order tradeNo=$tradeNo outTradeNo=$outTradeNo to database fail")
                    }
                    Redirect(Constants.paySuccessUrl)
                }
              case "WAIT_BUYER_PAY" =>
                log.debug(s"the tradeNo=$tradeNo outTradeNo=$outTradeNo state is WAIT_BUYER_PAY")
                alipayDao.createOrder(System.currentTimeMillis(),tradeNo,outTradeNo,Constants.alipayState.waitBuyerPay).map{
                  res =>
                    if(res <= 0) {
                      log.error(s"add alipay order tradeNo=$tradeNo outTradeNo=$outTradeNo to database fail")
                    }
                    Redirect(Constants.payFailUrl)
                }
              case "TRADE_CLOSED" =>
                log.debug(s"the tradeNo=$tradeNo outTradeNo=$outTradeNo state is TRADE_CLOSED")
                alipayDao.createOrder(System.currentTimeMillis(),tradeNo,outTradeNo,Constants.alipayState.closed).map{
                  res =>
                    if(res <= 0) {
                      log.error(s"add alipay order tradeNo=$tradeNo outTradeNo=$outTradeNo to database fail")
                    }
                    Redirect(Constants.payFailUrl)
                }
              case "TRADE_PENDING" =>
                log.debug(s"the tradeNo=$tradeNo outTradeNo=$outTradeNo state is TRADE_PENDING")
                alipayDao.createOrder(System.currentTimeMillis(),tradeNo,outTradeNo,Constants.alipayState.pending).map{
                  res =>
                    if(res <= 0) {
                      log.error(s"add alipay order tradeNo=$tradeNo outTradeNo=$outTradeNo to database fail")
                    }
                    Redirect(Constants.payFailUrl)
                }
            }
          }
        }
      }else{
        log.debug("the alipay redirect did not give the trade_no")
        Future.successful(Redirect(Constants.errorUrl))
      }
    }else{
      log.debug("the alipay redirect verify failed")
      Future.successful(Redirect(Constants.errorUrl))
    }

  }

  def webPayNotifyUrl = Action.async{implicit request =>
    val requestPara = request.queryString.map(i => (i._1,i._2.head))
    val outTradeNo = request.getQueryString("out_trade_no").getOrElse("")
    val tradeStatus = request.getQueryString("trade_status").getOrElse("")
    val tradeNoOpt = request.getQueryString("trade_no")
    val varifyRes = AlipayNotify.verify(requestPara)
    if(varifyRes){
      if(tradeNoOpt.isDefined){
        val tradeNo = tradeNoOpt.get
        alipayDao.findByTradeNo(tradeNo).map{find =>
          if(find.isEmpty){
            tradeStatus match{
              case "TRADE_FINISHED" =>
                alipayDao.createOrder(System.currentTimeMillis(),tradeNo,outTradeNo,Constants.alipayState.finished).map{
                  res =>
                    if(res <= 0) {
                      log.error(s"add alipay order tradeNo=$tradeNo outTradeNo=$outTradeNo to database fail")
                    }
                }
              case "TRADE_SUCCESS" =>
                alipayDao.createOrder(System.currentTimeMillis(),tradeNo,outTradeNo,Constants.alipayState.success).map{
                  res =>
                    if(res <= 0) {
                      log.error(s"add alipay order tradeNo=$tradeNo outTradeNo=$outTradeNo to database fail")
                    }
                }
              case "WAIT_BUYER_PAY" =>
                log.debug(s"the tradeNo=$tradeNo outTradeNo=$outTradeNo state is WAIT_BUYER_PAY")
                alipayDao.createOrder(System.currentTimeMillis(),tradeNo,outTradeNo,Constants.alipayState.waitBuyerPay).map{
                  res =>
                    if(res <= 0) {
                      log.error(s"add alipay order tradeNo=$tradeNo outTradeNo=$outTradeNo to database fail")
                    }
                }
              case "TRADE_CLOSED" =>
                log.debug(s"the tradeNo=$tradeNo outTradeNo=$outTradeNo state is TRADE_CLOSED")
                alipayDao.createOrder(System.currentTimeMillis(),tradeNo,outTradeNo,Constants.alipayState.closed).map{
                  res =>
                    if(res <= 0) {
                      log.error(s"add alipay order tradeNo=$tradeNo outTradeNo=$outTradeNo to database fail")
                    }
                }
              case "TRADE_PENDING" =>
                log.debug(s"the tradeNo=$tradeNo outTradeNo=$outTradeNo state is TRADE_PENDING")
                alipayDao.createOrder(System.currentTimeMillis(),tradeNo,outTradeNo,Constants.alipayState.pending).map{
                  res =>
                    if(res <= 0) {
                      log.error(s"add alipay order tradeNo=$tradeNo outTradeNo=$outTradeNo to database fail")
                    }
                }
              case e =>
                log.debug(s"the tradeNo=$tradeNo outTradeNo=$outTradeNo state is unreasonable state=$e")
            }
          }
        }
        Future.successful(Ok("success"))
      }else{
        log.debug("the alipay redirect did not give the trade_no")
        Future.successful(Ok("fail"))
      }
    }else{
      log.debug("the alipay redirect verify failed")
      Future.successful(Ok("fail"))
    }
  }


  def phonePayReturnUrl = Action.async{ implicit request =>
    val requestPara = request.queryString.map(i => (i._1,i._2.head))
    val outTradeNo = request.getQueryString("out_trade_no").getOrElse("")
    val tradeStatus = request.getQueryString("trade_status").getOrElse("")
    val tradeNoOpt = request.getQueryString("trade_no")
    val varifyRes = AlipayNotify.verify(requestPara)
    if(varifyRes){
      if(tradeNoOpt.isDefined){
        val tradeNo = tradeNoOpt.get
        alipayDao.findByTradeNo(tradeNo).flatMap{find =>
          if(find.isDefined){
            val state = find.get
            if(state == Constants.alipayState.success || state == Constants.alipayState.finished){
              Future.successful(Redirect(Constants.paySuccessUrl))
            }else{
              Future.successful(Redirect(Constants.payFailUrl))
            }
          }else{
            tradeStatus match{
              case "TRADE_FINISHED" =>
                alipayDao.createOrder(System.currentTimeMillis(),tradeNo,outTradeNo,Constants.alipayState.finished).map{
                  res =>
                    if(res <= 0) {
                      log.error(s"add alipay order tradeNo=$tradeNo outTradeNo=$outTradeNo to database fail")
                    }
                    Redirect(Constants.paySuccessUrl)
                }
              case "TRADE_SUCCESS" =>
                alipayDao.createOrder(System.currentTimeMillis(),tradeNo,outTradeNo,Constants.alipayState.success).map{
                  res =>
                    if(res <= 0) {
                      log.error(s"add alipay order tradeNo=$tradeNo outTradeNo=$outTradeNo to database fail")
                    }
                    Redirect(Constants.paySuccessUrl)
                }
              case "WAIT_BUYER_PAY" =>
                log.debug(s"the tradeNo=$tradeNo outTradeNo=$outTradeNo state is WAIT_BUYER_PAY")
                alipayDao.createOrder(System.currentTimeMillis(),tradeNo,outTradeNo,Constants.alipayState.waitBuyerPay).map{
                  res =>
                    if(res <= 0) {
                      log.error(s"add alipay order tradeNo=$tradeNo outTradeNo=$outTradeNo to database fail")
                    }
                    Redirect(Constants.payFailUrl)
                }
              case "TRADE_CLOSED" =>
                log.debug(s"the tradeNo=$tradeNo outTradeNo=$outTradeNo state is TRADE_CLOSED")
                alipayDao.createOrder(System.currentTimeMillis(),tradeNo,outTradeNo,Constants.alipayState.closed).map{
                  res =>
                    if(res <= 0) {
                      log.error(s"add alipay order tradeNo=$tradeNo outTradeNo=$outTradeNo to database fail")
                    }
                    Redirect(Constants.payFailUrl)
                }
              case "TRADE_PENDING" =>
                log.debug(s"the tradeNo=$tradeNo outTradeNo=$outTradeNo state is TRADE_PENDING")
                alipayDao.createOrder(System.currentTimeMillis(),tradeNo,outTradeNo,Constants.alipayState.pending).map{
                  res =>
                    if(res <= 0) {
                      log.error(s"add alipay order tradeNo=$tradeNo outTradeNo=$outTradeNo to database fail")
                    }
                    Redirect(Constants.payFailUrl)
                }
            }
          }
        }
      }else{
        log.debug("the alipay redirect did not give the trade_no")
        Future.successful(Redirect(Constants.errorUrl))
      }
    }else{
      log.debug("the alipay redirect verify failed")
      Future.successful(Redirect(Constants.errorUrl))
    }

  }

  def phonePayNotifyUrl = Action.async{implicit request =>
    val requestPara = request.queryString.map(i => (i._1,i._2.head))
    val outTradeNo = request.getQueryString("out_trade_no").getOrElse("")
    val tradeStatus = request.getQueryString("trade_status").getOrElse("")
    val tradeNoOpt = request.getQueryString("trade_no")
    val varifyRes = AlipayNotify.verify(requestPara)
    if(varifyRes){
      if(tradeNoOpt.isDefined){
        val tradeNo = tradeNoOpt.get
        alipayDao.findByTradeNo(tradeNo).map{find =>
          if(find.isEmpty){
            tradeStatus match{
              case "TRADE_FINISHED" =>
                alipayDao.createOrder(System.currentTimeMillis(),tradeNo,outTradeNo,Constants.alipayState.finished).map{
                  res =>
                    if(res <= 0) {
                      log.error(s"add alipay order tradeNo=$tradeNo outTradeNo=$outTradeNo to database fail")
                    }
                }
              case "TRADE_SUCCESS" =>
                alipayDao.createOrder(System.currentTimeMillis(),tradeNo,outTradeNo,Constants.alipayState.success).map{
                  res =>
                    if(res <= 0) {
                      log.error(s"add alipay order tradeNo=$tradeNo outTradeNo=$outTradeNo to database fail")
                    }
                }
              case "WAIT_BUYER_PAY" =>
                log.debug(s"the tradeNo=$tradeNo outTradeNo=$outTradeNo state is WAIT_BUYER_PAY")
                alipayDao.createOrder(System.currentTimeMillis(),tradeNo,outTradeNo,Constants.alipayState.waitBuyerPay).map{
                  res =>
                    if(res <= 0) {
                      log.error(s"add alipay order tradeNo=$tradeNo outTradeNo=$outTradeNo to database fail")
                    }
                }
              case "TRADE_CLOSED" =>
                log.debug(s"the tradeNo=$tradeNo outTradeNo=$outTradeNo state is TRADE_CLOSED")
                alipayDao.createOrder(System.currentTimeMillis(),tradeNo,outTradeNo,Constants.alipayState.closed).map{
                  res =>
                    if(res <= 0) {
                      log.error(s"add alipay order tradeNo=$tradeNo outTradeNo=$outTradeNo to database fail")
                    }
                }
              case "TRADE_PENDING" =>
                log.debug(s"the tradeNo=$tradeNo outTradeNo=$outTradeNo state is TRADE_PENDING")
                alipayDao.createOrder(System.currentTimeMillis(),tradeNo,outTradeNo,Constants.alipayState.pending).map{
                  res =>
                    if(res <= 0) {
                      log.error(s"add alipay order tradeNo=$tradeNo outTradeNo=$outTradeNo to database fail")
                    }
                }
              case e =>
                log.debug(s"the tradeNo=$tradeNo outTradeNo=$outTradeNo state is unreasonable state=$e")
            }
          }
        }
        Future.successful(Ok("success"))
      }else{
        log.debug("the alipay redirect did not give the trade_no")
        Future.successful(Ok("fail"))
      }
    }else{
      log.debug("the alipay redirect verify failed")
      Future.successful(Ok("fail"))
    }
  }
}
