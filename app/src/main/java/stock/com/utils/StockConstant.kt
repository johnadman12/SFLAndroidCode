package stock.com.utils

import android.app.Activity

object StockConstant {
    @JvmStatic
    val PREF_NAME = "com.stock.app"
    val COUNTRYLIST = "country"
    val EXCHANGEID = "exchangeid"
    val CONTESTID = "contestid"
    val UUID = "uuid"
    val IDENTIFIRE = "identifire"
    val DEVICE_TOKEN = ""
    val STOCKLIST = "stocklist"
    val NEWSLIST = "newslist"
    val SELECTEDSTOCK = "selectedstock"
    val TEAMID = "teamid"

    val IMAG_BASE_PATH = "http://18.188.34.216/webadmin/uploads/user/"
    val NEWS_ACCESS_TOKEN = "3ff9fe7e393fde1cc21e7bb3f9153431cd2456a10aac18191cc3e9401cf4c5ce"

    val CITYFALCON_URL="https://www.cityfalcon.com"

    val RESULT_CODE_SORT = 102;
    val RESULT_CODE_FILTER = 103;
    val RESULT_CODE_FILTER_WATCH = 1001;
    val RESULT_CODE_SORT_WATCH = 1002;
    val RESULT_CODE_SORT_CREATE_TEAM = 1003;
    val RESULT_CODE_SORT_VIEW_TEAM = 1012;
    val RESULT_CODE_CREATE_TEAM = 1004;
    val RESULT_CODE_VIEW_TEAM = 1005;
    val RESULT_CODE_VIEW_REMOVE_TEAM = 1006;
    val RESULT_CODE_EDIT_TEAM = 1007;
    val RESULT_CODE_SORT_EDIT_TEAM = 1008;
    val RESULT_CODE_WATCHLIST_FILTER = 1020;
    val RESULT_CODE_FILTER_TEAM = 1009;
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
    val NEWS = "news"
    val USERFIRSTTIME = "user_first_time"
    val ACCESSTOKEN = "x_access_token"
    val PASSWORD = "password"

    val DEFAULT_VALUE_STRING = ""

    @JvmStatic
    var ACTIVITIES = ArrayList<Activity?>()
    val CONTEST_TYPE = "contesttype"
    val SECTOR_TYPE = "sectortype"
    val MARKET_TYPE = "market_type"
    val COUNTRY_TYPE = "country_type"

    val COUNTRY_WATCHLIST_TYPE = "countrywatchlist"
    val SECTOR_WATCHLIST_TYPE = "sectorwatchlist"
    val ASSETS_WATCHLIST_TYPE = "assetwatchlist"
    val MARKET_WATCHLIST_TYPE = "marketwatchlist"
}