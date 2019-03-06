package stock.com.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import stock.com.R
import stock.com.constant.PrefConstant
import stock.com.constant.Tags
import stock.com.ui.signup.apiResponse.otpVerify.UserData
import stock.com.utils.AppDelegate

class Prefs(internal var mContext: Context?) {

    private val prefName :String
    private val prefNameTemp:String
    private var mSharedPreferences: SharedPreferences
    private var mSharedPreferencesTemp: SharedPreferences
    init {
        prefName=mContext!!.getString(R.string.app_name)
        prefNameTemp=mContext!!.getString(R.string.app_name)+"Temp"
        mSharedPreferences = mContext!!.getSharedPreferences(
            prefName, Context.MODE_PRIVATE)
        mSharedPreferencesTemp = mContext!!.getSharedPreferences(
            prefNameTemp, Context.MODE_PRIVATE)
    }

    fun putStringValue(editorkey: String, editorvalue: String) {
        mSharedPreferences!!.edit().putString(editorkey, editorvalue).apply()
        AppDelegate.LogP("putStringValue => editorkey = $editorkey, editorvalue = $editorvalue")
    }

    fun putStringValueinTemp(editorkey: String, editorvalue: String) {
        mSharedPreferencesTemp!!.edit().putString(editorkey, editorvalue).apply()
        AppDelegate.LogP("putStringValue => editorkey = $editorkey, editorvalue = $editorvalue")
    }

    fun getStringValuefromTemp(editorkey: String, defValue: String): String {
        val PrefValue = mSharedPreferencesTemp!!.getString(editorkey, defValue)
        AppDelegate.LogP("getStringValue => editorkey = $editorkey, editorvalue = $PrefValue")
        return PrefValue

    }


    /**
     * This method is used to store int value in SharedPreferences
     */

    fun putIntValue(editorkey: String, editorvalue: Int) {
        mSharedPreferences!!.edit().putInt(editorkey, editorvalue).apply()
    }

    fun putIntValueInTemp(editorkey: String, editorvalue: Int) {
        mSharedPreferencesTemp!!.edit().putInt(editorkey, editorvalue).apply()
    }

    /**
     * This method is used to store boolean value in SharedPreferences
     */
    fun putBooleanValue(editorkey: String, editorvalue: Boolean) {
        mSharedPreferences!!.edit().putBoolean(editorkey, editorvalue).apply()
        AppDelegate.LogP("putBooleanValue => editorkey = $editorkey, editorvalue = $editorvalue")
    }

    /**
     * This method is used to get String value from SharedPreferences
     *
     * @return String PrefValue
     */
    fun getStringValue(editorkey: String, defValue: String): String {
        val PrefValue = mSharedPreferences!!.getString(editorkey, defValue)
        AppDelegate.LogP("getStringValue => editorkey = $editorkey, editorvalue = $PrefValue")
        return PrefValue

    }

    /**
     * This method is used to get int value from SharedPreferences
     *
     * @return int PrefValue
     */
    fun getIntValue(editorkey: String, defValue: Int): Int {
        return mSharedPreferences!!.getInt(editorkey, defValue)
    }

    fun getIntValuefromTemp(editorkey: String, defValue: Int): Int {
        return mSharedPreferencesTemp!!.getInt(editorkey, defValue)
    }

    /**
     * This method is used to get boolean value from SharedPreferences
     *
     * @return boolean PrefValue
     */
    fun getBooleanValue(editorkey: String, defValue: Boolean): Boolean {
        val PrefValue = mSharedPreferences!!.getBoolean(editorkey, defValue)
        AppDelegate.LogP("getBooleanValue => editorkey = $editorkey, editorvalue = $PrefValue")
        return PrefValue
    }


    fun putAuthKey(str_AuthKey: String) {
        mSharedPreferences!!.edit().putString(Tags.auth_key, str_AuthKey).apply()
        AppDelegate.LogP("putAuthKey = " + str_AuthKey)
    }

    //      String str_AuthKey = mSharedPreferences.getString(Tags.auth_key, "[B@2ea1911d");
    val authKey: String
        get() {
            val str_AuthKey = mSharedPreferences!!.getString(Tags.auth_key, "")
            AppDelegate.LogP("getAuthKey = " + str_AuthKey!!)
            return str_AuthKey
        }



    val primaryAddress: String
        get() {
            val str_PrimaryAddress = mSharedPreferences!!.getString(Tags.primary_address, "")
            AppDelegate.LogP("getPrimaryAddress = " + str_PrimaryAddress!!)
            return str_PrimaryAddress
        }

    fun putPrimaryAddress(str_PrimaryAddress: String) {
        mSharedPreferences!!.edit().putString(Tags.primary_address, str_PrimaryAddress).apply()
        AppDelegate.LogP("putPrimaryAddress = " + str_PrimaryAddress)
    }


    var userdata: UserData?
        get() {
            val gson = Gson()
            val json = mSharedPreferences!!.getString(Tags.USER_DATA, "")
            val obj: UserData? = gson.fromJson<UserData>(json, UserData::class.java)
            AppDelegate.LogP("getUserdata = " + json!!)
            return obj!!
        }
        set(userdata) {
            val edit = mSharedPreferences!!.edit()
            val gson = Gson()
            val json = gson.toJson(userdata)
            edit.putString(Tags.USER_DATA, json)
            edit.apply()
            AppDelegate.LogP("setUserData = " + json)
        }



    var fcMtoken: String
        get() {
            val token = mSharedPreferences!!.getString(Tags.FCMtoken, "")
            AppDelegate.LogP("getFCMtoken = " + token!!)
            return token
        }
        set(token) {
            mSharedPreferences!!.edit()
                    .putString(Tags.FCMtoken, token)
                    .apply()
            AppDelegate.LogP("setFCMtoken = " + token)
        }
    var fcMtokeninTemp: String
        get() {
            var token = mSharedPreferencesTemp!!.getString(Tags.FCMtoken, "")
            AppDelegate.LogP("getFCMtokenintemp = " + token!!)
            return token
        }
        set(token) {
            mSharedPreferencesTemp!!.edit()
                    .putString(Tags.FCMtoken, token)
                    .apply()
            AppDelegate.LogP("setFCMtokenintemp = " + token)
        }

    fun clearTempPrefs() {
        try {
            mSharedPreferencesTemp!!.edit().clear().apply()
            AppDelegate.LogP("clearTempPrefs")
        } catch (e: Exception) {
            AppDelegate.LogE(e)
        }

    }
    fun clearSharedPreference() {
        try {
            val deviceid = getStringValue(PrefConstant.KEY_DEVICE_ID, "")
            mSharedPreferences!!.edit().clear().apply()
            putStringValue(PrefConstant.KEY_DEVICE_ID, deviceid)
        } catch (e: Exception) {
        }
    }



}
