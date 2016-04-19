package common.errorcode

import protocols.BaseJsonProtocols
/**
 * Created by liuziwei on 2016/4/15.
 */
object ApiErrorCode extends BaseJsonProtocols{

  /**api 错误码  1001000 --- 1001999**/

  def IllegalAppid = jsonResult(1001000,"illegal appId")
  def SnExisted = jsonResult(1001001,"this sn is used in one minute")
  def AuthenticateFail = jsonResult(1001002,"authenticate fail")
  def AddOrderFail = jsonResult(1001003,"add order to orderDao fail")

}
