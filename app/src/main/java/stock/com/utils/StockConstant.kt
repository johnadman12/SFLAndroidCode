package stock.com.utils

import android.app.Activity

object StockConstant{
    @JvmStatic
    val PREF_NAME = "com.stock.app"
    val COUNTRYLIST = "country"
    val EXCHANGEID = "exchangeid"
    val DEVICE_TOKEN = ""
    val STOCKLIST = "stocklist"

    val IMAG_BASE_PATH="http://18.188.34.216/webadmin/uploads/user/"

    val RESULT_CODE_SORT=102;
    val RESULT_CODE_FILTER_WATCH=1001;
    val RESULT_CODE_SORT_WATCH=1002;
    val RESULT_CODE_SORT_CREATE_TEAM=1003;
    val REQUEST_WRITE_STORAGE = 112
    val REQUEST_CAMERA = 113
    val REQUEST_GALLARY = 114

    val USERID = ""
    val FLAG = ""
    val USERNAME = "username"
    val USERPHONE = "userphone"
    val USEREMAIL = "useremail"
    val USERIMG = "userimg"
    val USERDATA = ""
    val CONTEST = ""
    val USERFIRSTTIME = "user_first_time"
    val ACCESSTOKEN = "x_access_token"
    val PASSWORD = "password"

    val DEFAULT_VALUE_STRING = ""

    @JvmStatic
    var ACTIVITIES = ArrayList<Activity?>()
}