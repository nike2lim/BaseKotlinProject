package com.example.shlim.basekotlinproject.Utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.telephony.SmsManager
import android.text.TextUtils
import java.net.Inet4Address
import java.net.NetworkInterface
import java.util.*
import java.util.logging.Logger

class Util {

    companion object {
        val TAG = Util::class.simpleName

        /**
         * 단말의 시리얼번호를 가져온다.
         * @param cxt
         */
        fun getSerialNumber(cxt : Context) : String? {
            var serialNum : String? = null

            try {
                val c = Class.forName("android.os.SystemProperties")

                val get = c.getMethod("get", String::class.java, String::class.java)
                serialNum = get.invoke(c, "ril.serialnumber", "") as String?
            }catch (e : Exception) {
                LogUtil.e(TAG, e.message)

            }
            return serialNum
        }

        /**
         * ipV4 주소를 리턴한다.
         */
        fun getIp4Address() : String?{
            var address : String? = null

            val en : Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()

            while(en.hasMoreElements()) {
                val intf = en.nextElement()
                val enumIpAdrr = intf.inetAddresses
                while (enumIpAdrr.hasMoreElements()) {
                    val inetAddress = enumIpAdrr.nextElement()

                    if(!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        address = inetAddress.hostAddress
                        return address
                    }
                }
            }
            return null
        }

        /**
         * path에 있는 이미지파일의 방향을 리턴한다.
         * @param path
         */
        fun getExifOrientation(path : String) : Int {
            val exif = ExifInterface(path)

            if(null != exif) {
                val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1)
                if(-1 != orientation) {
                    when(orientation) {
                        ExifInterface.ORIENTATION_ROTATE_90 -> return 90
                        ExifInterface.ORIENTATION_ROTATE_180 -> return 180
                        ExifInterface.ORIENTATION_ROTATE_270 -> return 270
                    }
                }
            }
            return -1
        }

        /**
         * bitmap을 Roate해서 리턴한다.
         * @param bitmap
         * @param degrees
         */
        fun getRotatedBitmap(bitmap : Bitmap, degrees : Int) : Bitmap? {
            var rotatedBitmap : Bitmap? = null
            if(degrees !=0 && null != bitmap) {
                val matrix = Matrix()
                matrix.setRotate(degrees.toFloat(), ((bitmap.width / 2).toFloat()), ((bitmap.height / 2).toFloat()))

                try {
                    val rotatedBitmap = Bitmap.createBitmap(bitmap, 0 , 0, bitmap.width, bitmap.height, matrix, true)
                    if(bitmap != rotatedBitmap) {
                        bitmap.recycle()
                    }
                }catch (e : Exception) {
                    LogUtil.d(TAG, "getRotatedBitmap exception : ${e.message}" )
                }
            }
            return rotatedBitmap
        }


        /**
         * SMS를 전송한다.
         * TODO 국제인 경우 보낼 국가를 설정하도록 하고 설정된 국가에 대한 값(ex 한국: +82)을 붙여줘야 한다.
         * @param cxt
         * @param number
         * @param msg
         */
        fun sendMMS(cxt : Context, number : String, msg : String) : Boolean{
            if(TextUtils.isEmpty(number) || TextUtils.isEmpty(msg)) {
                return false
            }
            var toNumber : String = ""
            val smsManager = SmsManager.getDefault()

            if(number.startsWith("0")) {
                toNumber = number.substring(1)
            }

            val parts = smsManager.divideMessage(msg)
            smsManager.sendMultipartTextMessage(toNumber, null, parts, null, null)

            return true
        }


    }       // companion object
}