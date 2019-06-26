package stock.com.ui.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import stock.com.ui.pojo.UserPojo.User


class HomeSearchPojo : BasePojo() {

    @SerializedName("data")
    @Expose
    var data: Data? = null
    @SerializedName("results")
    @Expose
    private val result: Map<String, Data>? = null

    inner class Data {
        /*@SerializedName("users")
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
        var contests: ArrayList<Contests>? = null*/

        @SerializedName("users")
        @Expose
        var users: ArrayList<Demo>? = null;

        @SerializedName("stocks")
        @Expose
        var stocks: ArrayList<Demo>? = null;
        @SerializedName("currency")
        @Expose
        var currency: ArrayList<Demo>? = null
        @SerializedName("crypto")
        @Expose
        var crypto: ArrayList<Demo>? = null
        @SerializedName("contests")
        @Expose
        var contests: ArrayList<Demo>? = null
    }

}