package stock.com.utils

import android.app.Activity

object StockConstant {
    @JvmStatic
    val PREF_NAME = "com.stock.app"
    val COUNTRYLIST = "country"
    val EXCHANGEID = "exchangeid"
    val MARKETID = "marketid"
    val CONTESTID = "contestid"
    val CONTESTFEE = "contestfee"
    val UUID = "uuid"
    val IDENTIFIRE = "identifire"
    val DEVICE_TOKEN = ""
    val STOCKLIST = "stocklist"
    val MARKETLIST = "marketlist"
    val NEWSLIST = "newslist"
    val SELECTEDSTOCK = "selectedstock"
    val SYMBOL = "symbol"
    val TEAMID = "teamid"
    val FRIENDID = "friend_id"
    val TOTALCHANGE = "totalchange"

    val IMAG_BASE_PATH = "https://www.dfxchange.com/dfxchange/webadmin/webroot/uploads/"
    val NEWS_ACCESS_TOKEN = "3ff9fe7e393fde1cc21e7bb3f9153431cd2456a10aac18191cc3e9401cf4c5ce"

    val CITYFALCON_URL="https://www.cityfalcon.com"
    val CHART_IQ="https://dfxchange.com/dfxchange/api/controllers/graphs.php?slug="
    val SOCKET="https://www.dfxchange.com:4000"

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
    val RESULT_CODE_SORT_MARKET_TEAM = 1021;
    val RESULT_CODE_SORT_MARKETVIEW_TEAM = 1022;
    val RESULT_CODE_MARKET_REMOVE_TEAM = 1023;
    val RESULT_CODE_SORT_MARKET_EDIT_TEAM = 1024;
    val RESULT_CODE_EDIT_MARKET = 1025;
    val RESULT_CODE_SORT_MARKET = 1026;
    val RESULT_CODE_HOME_SEARCH = 1027;
    val REQUEST_WRITE_STORAGE = 112
    val REQUEST_CAMERA = 113
    val REQUEST_GALLARY = 114
    val REDIRECT_UPCOMING_MARKET = 123
    val REDIRECT_CREATED = 124
    val RESULT_CODE_MARKET_FILTER = 125

    val USERID = ""
   public final val FLAG = ""
    val USERNAME = "username"
    val USERPHONE = "userphone"
    val USERCOUNTRYCODE = "usercountry"
    val USEREMAIL = "useremail"
    val USERIMG = "userimg"
    val USERLEVEL = "userlevel"
    val USERDATA = ""
    val NEWS = "news"
    val USERFIRSTTIME = "user_first_time"
    val ACCESSTOKEN = "x_access_token"
    val PASSWORD = "password"
    val CONTESTCODE = "contestcode"
    val CURRENCYID = "currencyid"

    val DEFAULT_VALUE_STRING = ""

    @JvmStatic
    var ACTIVITIES = ArrayList<Activity?>()
    val CONTEST_TYPE = "contesttype"
    val SECTOR_TYPE = "sectortype"
    val MARKET_TYPE = "market_type"
    val COUNTRY_TYPE = "country_type"
    val ACTIVE_CURRENCY_TYPE = "active_currency"

    val COUNTRY_WATCHLIST_TYPE = "countrywatchlist"
    val SECTOR_WATCHLIST_TYPE = "sectorwatchlist"
    val ASSETS_WATCHLIST_TYPE = "assetwatchlist"
    val MARKET_WATCHLIST_TYPE = "marketwatchlist"
    val STOCKID= "stock_id"
    val CHART="chart"
    val TEAMNAME="teamname"
}