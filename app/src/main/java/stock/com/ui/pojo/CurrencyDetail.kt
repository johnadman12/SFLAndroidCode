package stock.com.ui.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class CurrencyDetail:BasePojo(){
    @SerializedName("stock")
    @Expose
    var stock: ArrayList<Stock>? = null

    inner class Stock {

        @SerializedName("currencyid")
        @Expose
        var currencyid: Int? = null
        @SerializedName("symbol")
        @Expose
        var symbol: String? = null
        @SerializedName("ric")
        @Expose
        var ric: String? = null
        @SerializedName("firstflag")
        @Expose
        var firstflag: String? = null
        @SerializedName("secondflag")
        @Expose
        var secondflag: String? = null
        @SerializedName("source")
        @Expose
        var source: String? = null
        @SerializedName("latestPrice")
        @Expose
        var latestPrice: String? = null
        @SerializedName("hVolume")
        @Expose
        var hVolume: String? = null
        @SerializedName("currencyname")
        @Expose
        var currencyname: String? = null
        @SerializedName("changePercent")
        @Expose
        var changePercent: String? = null
        @SerializedName("CF_OPEN")
        @Expose
        var cFOPEN: String? = null
        @SerializedName("CF_CLOSE")
        @Expose
        var cFCLOSE: String? = null
        @SerializedName("CF_HIGH")
        @Expose
        var cFHIGH: String? = null
        @SerializedName("CF_LOW")
        @Expose
        var cFLOW: String? = null
        @SerializedName("marketId")
        @Expose
        var marketId: Int? = null
        @SerializedName("marketname")
        @Expose
        var marketname: String? = null
        @SerializedName("stockid")
        @Expose
        var stockid: String? = null
        @SerializedName("day_range")
        @Expose
        var day_range: String? = null
        @SerializedName("52week_range")
        @Expose
        var week_range: String? = null
        @SerializedName("previousClose")
        @Expose
        var previousClose: String? = null
        @SerializedName("marketopen")
        @Expose
        var marketopen: String? = null
        @SerializedName("marketclose")
        @Expose
        var marketclose: String? = null
        @SerializedName("sector")
        @Expose
        var sector: String? = null
        @SerializedName("country_id")
        @Expose
        var countryId: String? = null
        @SerializedName("stock_type")
        @Expose
        var stock_type: String? = null

    }
}