package com.example.shlim.basekotlinproject.Utils

import android.content.Context
import android.util.Log
import com.example.shlim.basekotlinproject.BuildConfig
import com.example.shlim.basekotlinproject.Common.Constant
import java.io.*


class LogUtil {

    companion object {
        fun i(tag : String?, msg : String?) {
            if(BuildConfig.DEBUG) {
                Log.i(tag, msg)
            }
        }

        fun d(tag : String?, msg : String?) {
            if(BuildConfig.DEBUG) {
                Log.d(tag, msg)
            }
        }

        fun v(tag : String?, msg : String?) {
            if(BuildConfig.DEBUG) {
                Log.v(tag, msg)
            }
        }

        /**
         * Error log
         * @param tag
         * @param msg
         */
        fun e(tag : String?, msg : String?) {
            if(BuildConfig.DEBUG) {
                Log.e(tag, msg)
            }
        }

        /**
         * TAG를 가져온다.
         * @return
         */
        fun getTag() : String {
            val level = 4
            val trace = Thread.currentThread().stackTrace[level]
            val fileName = trace.fileName
            val classPath = trace.className
            val className = classPath.substring(classPath.lastIndexOf(".") + 1)
            val methodName = trace.methodName
            val lineNumber = trace.lineNumber

            return  "APP#" + className + "." + methodName + "(" + fileName + ":" + lineNumber + ")"
        }


        fun writeLog(cxt : Context, tag : String?, msg : String?) {
            val time = System.currentTimeMillis()
//            val logPath = FileUtil.getStoragePath(cxt) + Constant.DEFAULT_APP_FILE_DIR + "/" + Constant.FILE_LOG_NAME_PREFIX + time + Constant.FILE_EXT_TXT
            val logPath = FileUtil.getStoragePath(cxt) + Constant.DEFAULT_APP_FILE_DIR + "/" + Constant.FILE_LOG_NAME

            LogUtil.d(tag, "writeLog() -> path:" + logPath);

            val file = File(logPath)
            if (!file.exists()) {
                try {
                    file.createNewFile()
                } catch (ioe : IOException) {
                    LogUtil.e(tag, ioe.message)
                }
            }
            if (file.exists()) {
                try {
                    val bfw = BufferedWriter( FileWriter(logPath, true))
                    bfw.write(DateUtil.getLogTime())
                    bfw.write(" ");
                    bfw.write(tag);
                    bfw.write(" ");
                    bfw.write(msg);
                    bfw.write("\n");
                    bfw.flush();
                    bfw.close();
                } catch (ffe : FileNotFoundException) {
                    LogUtil.e(tag, ffe.message)
                } catch (ioe : IOException) {
                    LogUtil.e(tag, ioe.message);
                }
            }
        }
    }


}