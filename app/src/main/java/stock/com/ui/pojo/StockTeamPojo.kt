package stock.com.ui.pojo

import stock.com.ui.pojo.StockPojo.Stock
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class StockTeamPojo :BasePojo(){
    @SerializedName("stock")
    @Expose
    var stock: List<Stock>? = null

    inner class Stock {

        @SerializedName("stockid")
        @Expose
        var stockid: Int? = null
        @SerializedName("symbol")
        @Expose
        var symbol: String? = null
        @SerializedName("image")
        @Expose
        var image: String? = null
        @SerializedName("companyName")
        @Expose
        var companyName: String? = null
        @SerializedName("previousClose")
        @Expose
        var previousClose: Any? = null
        @SerializedName("latestVolume")
        @Expose
        var latestVolume: Any? = null
        @SerializedName("marketopen")
        @Expose
        var marketopen: Any? = null
        @SerializedName("marketclose")
        @Expose
        var marketclose: Any? = null
        @SerializedName("latestPrice")
        @Expose
        var latestPrice: Any? = null
        @SerializedName("changePercent")
        @Expose
        var changePercent: Any? = null
        @SerializedName("sector")
        @Expose
        var sector: String? = null
        @SerializedName("addedStock")
        @Expose
        var addedStock: String? = null

    }
}