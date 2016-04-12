package util

import org.apache.commons.codec.digest.DigestUtils

/**
 * User: Liboren's. 
 * Date: 2015/12/4.
 * Time: 17:19.
 */
object MD5Util {
  //item:Map[String,String]
  def createSignature(item:List[(String,String)],key:String) = {
    val stringSignTemp = item.sortBy(_._1).map(m => m._1+"="+m._2).mkString("&")
    val stringA = key
    println(stringSignTemp+"&key="+stringA)
    DigestUtils.md5Hex(stringSignTemp+"&key="+stringA).toUpperCase // 最后加上商户秘钥
  }
}