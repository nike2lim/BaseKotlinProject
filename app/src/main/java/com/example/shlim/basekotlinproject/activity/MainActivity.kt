package com.example.shlim.basekotlinproject.activity

import android.Manifest
import android.accounts.AccountManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.shlim.basekotlinproject.BaseKotlinApplication
import com.example.shlim.basekotlinproject.Common.RequestCode
import com.example.shlim.basekotlinproject.R
import com.example.shlim.basekotlinproject.Utils.DeviceUtil
import com.example.shlim.basekotlinproject.Utils.LogUtil
import com.example.shlim.basekotlinproject.Utils.Util
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val TAG by lazy {
        LogUtil.getTag()
    }

    lateinit var mApp : BaseKotlinApplication

    val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermission()
        mApp = applicationContext as BaseKotlinApplication

        val serialNum = Util.getSerialNumber(this)
        LogUtil.d(TAG, "SerialNum : $serialNum")

        val accountNames = DeviceUtil.getGooleAccount(this)
        for(name in accountNames) {
            LogUtil.d(TAG, "accountName : $name")
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            DeviceUtil.chooseAccountIntent(this)
        }

//        val tt = Util.getExifOrientation(FileUtil.getStoragePath(this) + "profile_1544673647767.jpg")
//        LogUtil.d(TAG, "getExifOrientation degree : $tt" )
        
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RequestCode.REQUEST_CODE_GET_ACCOUNT) {
            if (data != null) {
                val extras = data.getExtras();
                val accountName = extras.getString(AccountManager.KEY_ACCOUNT_NAME);
                val accountType = extras.getString(AccountManager.KEY_ACCOUNT_TYPE);

                LogUtil.d(TAG, "Account Name: " + accountName);
                LogUtil.d(TAG, "Account Type: " + accountType);
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }


    /**
     * 권한 체크
     */
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

    /**
     * 권한체크 결과 처리
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        textView.text = ""
        var idx = 0
        for(idx in grantResults.indices) {
            if(grantResults[idx] == PackageManager.PERMISSION_GRANTED) {
                textView.append("${permissions[idx]} : 허용함\n")

                when(permissions.get(idx)) {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE -> {
                        val result = mApp.createAppDirectory()
                        Toast.makeText(applicationContext, "폴더 생성 : $result", Toast.LENGTH_SHORT).show()
                    }
                }
            }else {
                textView.append("${permissions[idx]} : 허용하지않음\n")
            }
        }
    }

}
