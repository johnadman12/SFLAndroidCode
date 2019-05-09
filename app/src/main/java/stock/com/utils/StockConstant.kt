package stock.com.utils

import android.app.Activity

object StockConstant{
    @JvmStatic
    val PREF_NAME = "com.stock.app"
    val COUNTRYLIST = "country"
    val EXCHANGEID = "exchangeid"
    val CONTESTID = "contestid"
    val DEVICE_TOKEN = ""
    val STOCKLIST = "stocklist"
    val SELECTEDSTOCK = "selectedstock"
    val TEAMID = "teamid"

    val IMAG_BASE_PATH="http://18.188.34.216/webadmin/uploads/user/"

    val RESULT_CODE_SORT=102;
    val RESULT_CODE_FILTER_WATCH=1001;
    val RESULT_CODE_SORT_WATCH=1002;
    val RESULT_CODE_SORT_CREATE_TEAM=1003;
    val RESULT_CODE_CREATE_TEAM=1004;
    val RESULT_CODE_VIEW_TEAM=1005;
    val RESULT_CODE_VIEW_REMOVE_TEAM=1006;
    val RESULT_CODE_EDIT_TEAM=1007;
    val RESULT_CODE_SORT_EDIT_TEAM=1008;
    val RESULT_CODE_FILTER_TEAM=1009;
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