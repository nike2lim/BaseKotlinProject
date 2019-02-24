package com.example.shlim.basekotlinproject.Utils

import android.content.Context
import android.os.Environment
import java.io.File

class FileUtil {

    companion object {

        /**
         * 디렉토리를 만든다.
         * @param path
         */
        fun createDirectory(path : String) : Boolean {
            var result = false
            val file = File(path)
            if(!file.exists())  {
                result = file.mkdirs()
            }
            return result
        }

        /**
         * App에서 사용할 스토리지 경로를 리턴한다.
         * @param cxt
         */
        fun getStoragePath(cxt : Context) : String?{
            val exist = Environment.getExternalStorageState()
            if(exist.equals(Environment.MEDIA_MOUNTED)) {
                return Environment.getExternalStorageDirectory().absolutePath + "/"
            }else {
                return cxt.cacheDir.absolutePath + "/"
            }
        }

        /**
         * path에 해당하는 파일을 삭제한다.
         * @param path
         */
        fun deleteFile(path : String) : Boolean {
            val file = File(path)
            return file.delete()
        }




    }

}