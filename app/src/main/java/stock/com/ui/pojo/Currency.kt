package stock.com.ui.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Currency : BasePojo() {
    @SerializedName("currency")
    @Expose
    var currency: List<Currency>? = null

    inner class Currency {

        @SerializedName("currencyid")
        @Expose
        var currencyid: Int? = null
        @SerializedName("symbol")
        @Expose
        var symbol: String? = null
        @SerializedName("name")
        @Expose
        var name: String? = null
        @SerializedName("firstflag")
        @Expose
        var firstflag: String? = null
        @SerializedName("secondflag")
        @Expose
        var secondflag: String? = null
        @SerializedName("latestVolume")
        @Expose
        var latestVolume: String? = null
        @SerializedName("changeper")
        @Expose
        var changeper: String? = null
        @SerializedName("crypto_type")
        @Expose
        var cryptoType: String? = null
    }
}