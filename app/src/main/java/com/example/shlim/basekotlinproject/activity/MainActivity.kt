package com.example.shlim.basekotlinproject.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.shlim.basekotlinproject.R
import com.example.shlim.basekotlinproject.Utils.LogUtil
import com.example.shlim.basekotlinproject.Utils.Util
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.simpleName

    val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermission()


        val serialNum = Util.getSerialNumber(this)
        LogUtil.d(TAG, "SerialNum : $serialNum")


        LogUtil.writeLog(this, TAG, "하하하하1")
        LogUtil.writeLog(this, TAG, "하하하하2")
        LogUtil.writeLog(this, TAG, "하하하하3")
        LogUtil.writeLog(this, TAG, "하하하하4")
        LogUtil.writeLog(this, TAG, "하하하하5")


    }


    fun checkPermission() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }

        for(permission : String in permissions) {
            var chk = checkCallingOrSelfPermission(permission)

            if(chk == PackageManager.PERMISSION_DENIED) {
                requestPermissions(permissions, 0)
                break
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        textView.text = ""
        var idx = 0
        for(idx in grantResults.indices) {
            if(grantResults[idx] == PackageManager.PERMISSION_GRANTED) {
                textView.append("${permissions[idx]} : 허용함\n")
            }else {
                textView.append("${permissions[idx]} : 허용하지않음\n")
            }
        }
    }

}
