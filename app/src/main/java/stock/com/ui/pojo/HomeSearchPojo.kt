package stock.com.ui.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import stock.com.ui.pojo.UserPojo.User


class HomeSearchPojo : BasePojo() {

    @SerializedName("data")
    @Expose
    var data: Data? = null

    inner class Data {
        @SerializedName("users")
        @Expose
        var users: ArrayList<User>? = null
        @SerializedName("stocks")
        @Expose
        var stocks: ArrayList<Stock>? = null
        @SerializedName("currency")
        @Expose
        var currency: ArrayList<Currency>? = null
        @SerializedName("crypto")
        @Expose
        var crypto: ArrayList<Crypto>? = null
        @SerializedName("contests")
        @Expose
        var contests: ArrayList<Contests>? = null
    }

    inner class Stock {

        @SerializedName("name")
        @Expose
        var name: String = ""
        @SerializedName("id")
        @Expose
        var id: String = ""
        @SerializedName("type")
        @Expose
        var type: String = ""

    }

    inner class User {

        @SerializedName("name")
        @Expose
        var name: String = ""
        @SerializedName("id")
        @Expose
        var id: String = ""
        @SerializedName("type")
        @Expose
        var type: String = ""

    }

    inner class Crypto {

        @SerializedName("id")
        @Expose
        var id: String = ""
        @SerializedName("name")
        @Expose
        var name: String = ""
        @SerializedName("type")
        @Expose
        var type: String = ""

    }

    inner class Currency {

        @SerializedName("id")
        @Expose
        var id: String = ""
        @SerializedName("name")
        @Expose
        var name: String = ""
        @SerializedName("type")
        @Expose
        var type: String = ""

    }

    inner class Contests {

        @SerializedName("id")
        @Expose
        var id: String = ""
        @SerializedName("name")
        @Expose
        var name: String = ""
        @SerializedName("type")
        @Expose
        var type: String = ""

    }
}