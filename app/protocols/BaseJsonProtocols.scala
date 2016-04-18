package protocols

import play.api.libs.json.{JsObject, Json}

/**
 * User: Liboren's. 
 * Date: 2016/3/4.
 * Time: 11:13.
 */
trait BaseJsonProtocols {
  def jsonResult(errorCode: Int, errorMsg: String) = {
    Json.obj("errCode" -> errorCode, "msg" -> errorMsg)
  }

  def jsonResult(errorCode: Int, errorMsg: String, data: JsObject) = {
    Json.obj("errCode" -> errorCode, "msg" -> errorMsg) ++ data
  }

  def successResult(data: JsObject) = success ++ data

  val success = jsonResult(0, "ok")
}