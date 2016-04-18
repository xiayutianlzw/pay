package util

import com.google.inject.Inject
import org.slf4j.LoggerFactory
import play.api.libs.ws.WSClient
import scala.xml.Elem
import scala.concurrent.ExecutionContext.Implicits.global
/**
 * User: Liboren's. 
 * Date: 2015/12/4.
 * Time: 14:18.
 */
class HttpUtil @Inject()(
                        ws:WSClient
                          )
{

  private val log = LoggerFactory.getLogger(this.getClass)

  import scala.concurrent.duration.Duration
  import java.util.concurrent.TimeUnit

  def postXMLRequestSend(
                          methodName: String,
                          url: String,
                          postData:Elem) = {
    log.info("Get Request [" + methodName + "] Processing...")
    log.debug(methodName + " url=" + url)
    log.debug(methodName + " postData=" + postData)
    val futureResult = ws.
      url(url).
      withFollowRedirects(follow = true).
      withRequestTimeout(Duration(10,TimeUnit.SECONDS)).
      post(postData).map { response =>
      log.debug("getRequestSend response headers:" + response.allHeaders)
      log.debug("getRequestSend response body:" + response.body)
      if (response.status != 200) {
        val msg = s"getRequestSend http failed url = $url, status = ${response.status}, text = ${response.statusText}, body = ${response.body.substring(0, 1024)}"
        log.warn(msg)
      }

      response.xml

    }
    futureResult.onFailure {
      case e: Exception =>
        log.error(methodName + " error:" + e.getMessage, e)
        throw e
    }
    futureResult
  }

  def postAlipayRequestSend(
                             methodName: String,
                             url: String,
                             postData:String) = {
    log.info("Get Request [" + methodName + "] Processing...")
    log.debug(methodName + " url=" + url)
    log.debug(methodName + " postData=" + postData)
    val futureResult = ws.
      url(url).
      withFollowRedirects(follow = true).
      withRequestTimeout(Duration(10,TimeUnit.SECONDS)).
      post(postData).map { response =>
      log.debug("getRequestSend response headers:" + response.allHeaders)
      log.debug("getRequestSend response body:" + response.body)
      if (response.status != 200) {
        val msg = s"getRequestSend http failed url = $url, status = ${response.status}, text = ${response.statusText}, body = ${response.body.substring(0, 1024)}"
        log.warn(msg)
      }

      response.json

    }
    futureResult.onFailure {
      case e: Exception =>
        log.error(methodName + " error:" + e.getMessage, e)
        throw e
    }
    futureResult
  }

  def getJsonRequestSend(
                          methodName: String,
                          url: String,
                          parameters: List[(String, String)]) = {
    log.info("Get Request [" + methodName + "] Processing...")
    log.debug(methodName + " url=" + url)
    log.debug(methodName + " parameters=" + parameters)
    val futureResult = ws.
      url(url).
      withFollowRedirects(follow = true).
      withRequestTimeout(Duration(10, scala.concurrent.duration.SECONDS)).
      withQueryString(parameters: _*).
      get().map { response =>
      log.debug("getRequestSend response headers:" + response.allHeaders)
      log.debug("getRequestSend response body:" + response.body)
      if (response.status != 200) {
        val body = if (response.body.length > 1024) response.body.substring(0, 1024) else response.body
        val msg = s"getRequestSend http failed url = $url, status = ${response.status}, text = ${response.statusText}, body = ${body}"
        log.warn(msg)
      }

      response.body

    }

    futureResult.onFailure {
      case e: Exception =>
        log.error(methodName + " error:" + e.getMessage, e)
        throw e
    }
    futureResult
  }


}
