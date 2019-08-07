package stock.com.ui.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable
import java.util.ArrayList

class WatchlistPojo : BasePojo() {
    @SerializedName("stock")
    @Expose
    var stock: ArrayList<WatchStock>? = null

    inner class WatchStock : Serializable {

        @SerializedName("id")
        @Expose
        var id: String? = null
        @SerializedName("stockid")
        @Expose
        var stockid: String? = null
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
        @SerializedName("cryptocurrencyid")
        @Expose
        var cryptocurrencyid: String? = null
        @SerializedName("cmc_rank")
        @Expose
        var cmcRank: String? = null

        @SerializedName("crypto_logo")
        @Expose
        var crypto_logo: String? = null

        @SerializedName("marketcapital")
        @Expose
        var marketcapital: String? = null
        @SerializedName("supply")
        @Expose
        var supply: String? = null
        @SerializedName("country_id")
        @Expose
        var countryId: String? = null
        @SerializedName("marketId")
        @Expose
        var marketId: String? = null
        @SerializedName("marketname")
        @Expose
        var marketname: String? = null
        @SerializedName("currencyid")
        @Expose
        var currencyid: String? = null
        @SerializedName("ask")
        @Expose
        var ask: String? = null
        @SerializedName("bid")
        @Expose
        var bid: String? = null

        @SerializedName("currency_netchange")
        @Expose
        var currency_netchange: String? = null
        @SerializedName("currency_perchange")
        @Expose
        var currency_perchange: String? = null
        @SerializedName("currency_volume")
        @Expose
        var currency_volume: String? = null
        @SerializedName("crypto_market_id")
        @Expose
        var crypto_market_id: String? = null
        @SerializedName("currency_market_id")
        @Expose
        var currency_market_id: String? = null
        @SerializedName("currency_symbol")
        @Expose
        var currency_symbol: String? = null

        @SerializedName("currency_firstflag")
        @Expose
        var currency_firstflag: String? = null
        @SerializedName("currency_secondflag")
        @Expose
        var currency_secondflag: String? = null

        @SerializedName("symbol")
        @Expose
        var symbol: String? = null

        @SerializedName("image")
        @Expose
        var image: String? = null
        @SerializedName("latestVolume")
        @Expose
        var latestVolume: String? = null
        @SerializedName("latestPrice")
        @Expose
        var latestPrice: String? = null
        @SerializedName("changePercent")
        @Expose
        var changePercent: String? = null
        @SerializedName("companyName")
        @Expose
        var companyName: String? = null


    }
}
