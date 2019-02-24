package com.example.shlim.basekotlinproject.Extensions

import android.app.NotificationManager
import android.content.Context
import android.widget.Toast


val Context.notificationmanger get() = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

fun Context.toast(msg : String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
fun Context.toast(stringRes : Int) =  toast(getString(stringRes))