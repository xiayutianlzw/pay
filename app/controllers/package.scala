import javax.inject.{Inject, Singleton}

import akka.actor.ActorSystem
import play.api.Logger
import play.api.libs.streams.Accumulator
import play.api.mvc._
import util.SecureUtil

import scala.collection.concurrent.TrieMap
import scala.concurrent.{Future, Await}
import common.errorcode.ApiErrorCode
import scala.concurrent.ExecutionContext.Implicits.global
/**
 * Created by liuziwei on 2016/4/15.
 */
package object controllers {


  @Singleton
  class LoggingAction @Inject() extends ActionBuilder[Request] {
    val log = Logger(this.getClass)

    def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
      log.info(s"access log: ${request.uri}")
      block(request)
    }
  }

  @Singleton
  case class ActionUtils @Inject()(
                                  loggingAction: LoggingAction,
                                    system:ActorSystem
                                    ) {
    import scala.concurrent.duration._


    val log = Logger(this.getClass)

    private[this] val snRecords = new TrieMap[String, Long]()

    private def snLiveTimeInMillis = 3 * 60 * 1000


    def snCacheKey(appId: String, sn: String) = appId + "\u0001" + sn

    //lazy val 初始化在 Application 文件中
    lazy val cacheReducer = { //sn 1分钟刷新一次
      import concurrent.duration._
      system.scheduler.schedule(1 second, snLiveTimeInMillis / 3 millis) {
        val t = System.currentTimeMillis() - snLiveTimeInMillis
        val oldKeys = snRecords.filter(_._2 > t).keys
        snRecords --= oldKeys
      }

    }



    def checkSignature(
                        appid:String,
                        sn:String,
                        params: List[String],
                        signature: String
                        )(action: => EssentialAction): EssentialAction =
      EssentialAction { requestHeader =>
        val key = Some("ffff")
        if(key.isEmpty){ // appid不存在
        val result = Results.Ok(ApiErrorCode.IllegalAppid)
          Accumulator.done(result)
        }
        else{
          val snKey = snCacheKey(appid, sn)
          if (snRecords.contains(snKey)) {
            snRecords(snKey) = System.currentTimeMillis()
            val result = Results.Ok(ApiErrorCode.SnExisted)
            Accumulator.done(result)
          }
          else {
            val secureKey = key.get
            val expected = SecureUtil.generateSignature(params, secureKey)
            val success = expected.equals(signature)
            if (success) {
              snRecords(snKey) = System.currentTimeMillis()
              action(requestHeader)
            }
            else {
              val result = Results.Ok(ApiErrorCode.AuthenticateFail)
              Accumulator.done(result) // 'Done' means the Iteratee has completed its computations
            }
          }
        }
      }
  }

}