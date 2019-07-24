package stock.com.ui.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Currency : BasePojo() {
    @SerializedName("currency")
    @Expose
    var currency: ArrayList<Currency>? = null

    inner class Currency {

        @SerializedName("currencyid")
        @Expose
        var currencyid: Int? = null
        @SerializedName("symbol")
        @Expose
        var symbol: String = ""
        @SerializedName("ask")
        @Expose
        var ask: String = ""
        @SerializedName("bid")
        @Expose
        var bid: String = ""
        @SerializedName("daychange")
        @Expose
        var daychange: String = ""
        @SerializedName("name")
        @Expose
        var name: String = ""
        @SerializedName("firstflag")
        @Expose
        var firstflag: String = ""
        @SerializedName("secondflag")
        @Expose
        var secondflag: String = ""
        @SerializedName("latestVolume")
        @Expose
        var latestVolume: String = ""
        @SerializedName("changeper")
        @Expose
        var changeper: String = ""
        @SerializedName("crypto_type")
        @Expose
        var cryptoType: String = ""
    }
}