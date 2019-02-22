package com.example.shlim.basekotlinproject.Utils

import android.content.Context
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



    }       // companion object
}