package com.example.shlim.basekotlinproject.Utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo

class NetworkUtil {
    val TAG = NetworkUtil::class.simpleName

    companion object {

        /**
         * type에 따른 Connection인지 리턴한다.
         * @param cxt
         * @param type
         */
        fun isTypeConnected(cxt : Context, type : Int) : Boolean {
            val cm = cxt.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo

            return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                val network = cm.activeNetwork
                val capabilities = cm.getNetworkCapabilities(network)
                capabilities != null && (capabilities.hasTransport(type))
//                capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
            } else {
                cm.activeNetworkInfo.type == type
            }
        }

        fun convertType(type : Int) : Int {
            var convertType : Int = -1
            when(type) {
                ConnectivityManager.TYPE_WIFI -> convertType = NetworkCapabilities.TRANSPORT_WIFI
                ConnectivityManager.TYPE_MOBILE -> convertType = NetworkCapabilities.TRANSPORT_CELLULAR
            }
            return convertType
        }



    }


}