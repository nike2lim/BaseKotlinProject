package com.example.shlim.basekotlinproject

import android.app.Application
import com.example.shlim.basekotlinproject.Common.Constant
import com.example.shlim.basekotlinproject.Utils.FileUtil

class BaseKotlinApplication : Application(){

    override fun onCreate() {
        super.onCreate()
    }

    /**
     * App에 필요한 기본 디렉토리를 생성한다.
     */
    fun createAppDirectory() : Boolean {
        val path = FileUtil.getStoragePath(this) + Constant.DEFAULT_APP_FILE_DIR
        return FileUtil.createDirectory(path)
    }

}