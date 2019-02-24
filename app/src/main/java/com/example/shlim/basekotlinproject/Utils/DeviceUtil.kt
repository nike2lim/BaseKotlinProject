package com.example.shlim.basekotlinproject.Utils

import android.accounts.AccountManager
import android.annotation.SuppressLint
import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.media.AudioManager
import android.os.Build
import android.support.annotation.RequiresApi
import android.telephony.ServiceState
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import com.example.shlim.basekotlinproject.Common.RequestCode


class DeviceUtil {

    companion object {
        val TAG = DeviceUtil::class.simpleName

        /**
         * 단말 번호를 가져온다.
         * @param cxt
         */
        @SuppressLint("MissingPermission")
        fun getDevicePhoneNumber(cxt : Context) : String?{
            var phoneNumber = ""

            val telephonyMnager = cxt.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

            try {
                phoneNumber = telephonyMnager.line1Number

                if(!phoneNumber.isEmpty()) {
                    if(phoneNumber.startsWith("+82")) {
                        phoneNumber = "0" + phoneNumber.substring(3)
                    }
                }
            }catch (e : SecurityException) {
                LogUtil.e(TAG, "getDevicePhoneNumber SecurityException error : ${e.message}" )
            }catch (e : Exception) {
                LogUtil.e(TAG, "getDevicePhoneNumber Exception error : ${e.message}" )
            }
            return phoneNumber
        }


        /**
         * Sim에서 국가 코드를 가져온다
         * @param cxt
         */
        fun getSimCountryISO(cxt : Context) : String {
            var simCountryISO = ""
            val telephoneManager = cxt.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            simCountryISO = telephoneManager.simCountryIso
            return simCountryISO
        }


        fun isLockScreen(cxt : Context) : Boolean {
            val keyGuardManager = cxt.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            if(keyGuardManager.isKeyguardLocked()) {
                return true
            }else {
                return false
            }
        }

        /**
         * 구글 계정 리스트를 리턴한다.
         * Android 22버전 이하에서만 사용가능
         * @param cxt
         */
        fun getGooleAccount(cxt : Context) : List<String> {
            val accountManager = AccountManager.get(cxt)
            val accounts = accountManager.accounts

            val arrylist = mutableListOf<String>()
            for(account in accounts) {
                if("com.google" == account.type) {
                    arrylist.add(account.name)
                }
            }
            return arrylist
        }

        /**
         * 구글 계정정보 가져오기위한 Intent를 전달한다.
         * @param act
         */
        @RequiresApi(Build.VERSION_CODES.M)
        fun chooseAccountIntent(act : Activity) {
            val intent = AccountManager.newChooseAccountIntent(null, null, arrayOf("com.google"), null, null, null, null)
            act.startActivityForResult(intent, RequestCode.REQUEST_CODE_GET_ACCOUNT)
        }


        /**
         * Ringer Mode를 리턴한다.
         * Ringer Mode(RINGER_MODE_VIBRATE, RINGER_MODE_NORMAL, RINGER_MODE_SILENT)
         * @param cxt
         */
        fun getAudioRingerMode(cxt : Context) : Int {
            val audioManager = cxt.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            return audioManager.ringerMode
        }

        /**
         * Ringer Mode를 설정한다.
         * @param cxt
         * @param mode (RINGER_MODE_VIBRATE, RINGER_MODE_NORMAL, RINGER_MODE_SILENT)
         */
        fun setAudioRingerMode(cxt : Context, mode : Int) {
            val audioManager = cxt.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audioManager.ringerMode = mode
        }

        /**
         * Stream Volume을 리턴한다.
         * @param cxt
         */
        fun getStreamMusicVolume(cxt : Context) {
            val audioManager = cxt.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        }

        /**
         * Stream Volume을 설정한다.
         * @param cxt
         * @param vol
         */
        fun setStreamMusicVolume(cxt : Context, vol : Int) {
            val audioManager = cxt.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, vol, AudioManager.FLAG_PLAY_SOUND)
        }

        /**
         * 네트워크 로밍 중인지 리턴한다.
         * @param cxt
         */
        fun isNetworkRomming(cxt : Context) : Boolean {
            val telephonyManager = cxt.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            return telephonyManager.isNetworkRoaming
        }

        /**
         * 로밍중인지 리턴한다.
         */
        fun isRomming(): Boolean {
            val serviceState = ServiceState()
            return serviceState.roaming
        }

    }
}