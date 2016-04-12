package util

import java.sql.Date
import java.text.SimpleDateFormat


/**
 * User: Liboren's. 
 * Date: 2015/12/4.
 * Time: 17:11.
 */
object TimeUtil {

  def format(timeMs:Long,format:String = "yyyy-MM-dd HH:mm:ss") ={
    val data  = new Date(timeMs)
    val simpleDateFormat = new SimpleDateFormat(format)
    simpleDateFormat.format(data)
  }

  /**得到时间戳的年份*/
  def yearOfTimeStamp(timeStampMs:Long)={
    val timeMs=if(timeStampMs < 10000000000L) timeStampMs*1000 else timeStampMs
    val data  = new Date(timeMs)
    val format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    format.format(data).split("-")(0)
  }

  /**得到时间戳的月份*/
  def monthOfTimeStamp(timeStampMs:Long)={
    val timeMs=if(timeStampMs < 10000000000L) timeStampMs*1000 else timeStampMs
    val data  = new Date(timeMs)
    val format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    format.format(data).split("-")(1)
  }

  /**得到时间戳的天*/
  def dayOfTimeStamp(timeStampMs:Long)={
    //判断时间戳是否为毫秒 毫秒为13位
    val timeMs=if(timeStampMs < 10000000000L) timeStampMs*1000 else timeStampMs
    val data  = new Date(timeMs)
    val format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
    format.format(data).split("-")(2)
  }

  /**得到时间戳的小时*/
  def hourOfTimeStamp(timeStampMs:Long)={
    //判断时间戳是否为毫秒 毫秒为13位
    val timeMs=if(timeStampMs < 10000000000L) timeStampMs*1000 else timeStampMs
    val data  = new Date(timeMs)
    val format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
    format.format(data).split("-")(3)
  }
}
