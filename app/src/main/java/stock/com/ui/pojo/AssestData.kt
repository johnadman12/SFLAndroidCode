package stock.com.ui.pojo

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class AssestData : BasePojo() {
    @SerializedName("stock")
    @Expose
    var stock: ArrayList<Stock>? = null

    class Stock() : Serializable {
        @SerializedName("cryptocurrencyid")
        @Expose
        var cryptocurrencyid: String = ""
        @SerializedName("companyName")
        @Expose
        var companyName: String = ""
        @SerializedName("slug")
        @Expose
        var slug: String = ""
        @SerializedName("symbol")
        @Expose
        var symbol: String = ""
        @SerializedName("cmc_rank")
        @Expose
        var cmcRank: String = ""
        @SerializedName("image")
        @Expose
        var image: String = ""
        @SerializedName("marketcapital")
        @Expose
        var marketcapital: String = ""
        @SerializedName("supply")
        @Expose
        var supply: String = ""
        @SerializedName("latestPrice")
        @Expose
        var latestPrice: String = ""
        @SerializedName("latestVolume")
        @Expose
        var latestVolume: String = ""
        @SerializedName("changePercent")
        @Expose
        var changePercent: String = ""
        @SerializedName("marketId")
        @Expose
        var marketId: String = ""
        @SerializedName("marketname")
        @Expose
        var marketname: String = ""
        @SerializedName("stockid")
        @Expose
        var stockid: String = ""
        @SerializedName("previousClose")
        @Expose
        var previousClose: String = ""
        @SerializedName("marketopen")
        @Expose
        var marketopen: String = ""
        @SerializedName("marketclose")
        @Expose
        var marketclose: String = ""
        @SerializedName("sector")
        @Expose
        var sector: String = ""
        @SerializedName("country_id")
        @Expose
        var countryId: String = ""
        @SerializedName("description")
        @Expose
        var cryptodescription: String = ""
        @SerializedName("cryptologo")
        @Expose
        var cryptologo: String = ""
        @SerializedName("urls")
        @Expose
        var urls: Urls? = null
        @SerializedName("marketCap")
        @Expose
        var marketCap: String = ""
        @SerializedName("hVolume")
        @Expose
        var hVolume: String = ""
        @SerializedName("circulating")
        @Expose
        var circulating: String = ""
        @SerializedName("maxSupply")
        @Expose
        var maxSupply: String = ""
        @SerializedName("rank")
        @Expose
        var rank: String = ""
        @SerializedName("change")
        @Expose
        var change: String = ""
        @SerializedName("average")
        @Expose
        var average: String = ""
        @SerializedName("open")
        @Expose
        var open: String = ""
        @SerializedName("close")
        @Expose
        var close: String = ""
        @SerializedName("high")
        @Expose
        var high: String = ""
        @SerializedName("low")
        @Expose
        var low: String = ""
        @SerializedName("stock_type")
        @Expose
        var stock_type: String = ""

        constructor(parcel: Parcel) : this() {
            cryptocurrencyid = parcel.readString()
            companyName = parcel.readString()
            symbol = parcel.readString()
            cmcRank = parcel.readString()
            image = parcel.readString()
            marketcapital = parcel.readString()
            supply = parcel.readString()
            latestPrice = parcel.readString()
            latestVolume = parcel.readString()
            changePercent = parcel.readString()
            marketId = parcel.readString()
            marketname = parcel.readString()
            stockid = parcel.readString()
            previousClose = parcel.readString()
            marketopen = parcel.readString()
            marketclose = parcel.readString()
            sector = parcel.readString()
            countryId = parcel.readString()
            cryptodescription = parcel.readString()
            cryptologo = parcel.readString()
            marketCap = parcel.readString()
            hVolume = parcel.readString()
            circulating = parcel.readString()
            maxSupply = parcel.readString()
            rank = parcel.readString()
            change = parcel.readString()
            average = parcel.readString()
            open = parcel.readString()
            close = parcel.readString()
            high = parcel.readString()
            low = parcel.readString()
            stock_type = parcel.readString()
        }


    }

    public class Urls : Serializable {

        @SerializedName("Website")
        @Expose
        var websiteUrl: List<String>? = null
        @SerializedName("Technical_Doc")
        @Expose
        var technicalDocUrl: List<String>? = null
        @SerializedName("Twitter")
        @Expose
        var twitterUrl: List<String>? = null
        @SerializedName("Reddit")
        @Expose
        var redditUrl: List<String>? = null
        @SerializedName("Source_Code")
        @Expose
        var sourceCodeUrl: List<String>? = null
        @SerializedName("Message_Board")
        @Expose
        var messageBoardUrl: List<String>? = null
        @SerializedName("Explorer")
        @Expose
        var explorerUrl: List<String>? = null

    }
}