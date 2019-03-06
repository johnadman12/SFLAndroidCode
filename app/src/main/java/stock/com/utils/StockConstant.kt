package stock.com.utils

import android.app.Activity

object StockConstant{
    @JvmStatic
    val PREF_NAME = "com.stock.app"

    val USERID = "user_id"
    val ACCESSTOKEN = "x_access_token"

    val DEFAULT_VALUE_STRING = ""

    @JvmStatic
    var ACTIVITIES = ArrayList<Activity?>()
}