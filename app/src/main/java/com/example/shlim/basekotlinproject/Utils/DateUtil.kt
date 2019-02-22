package com.example.shlim.basekotlinproject.Utils

import java.text.SimpleDateFormat
import java.util.Date

class DateUtil {

    companion object {
        fun getLogTime() : String{
            val now = System.currentTimeMillis()
            val date = Date(now)
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return simpleDateFormat.format(date)
        }

    }
}