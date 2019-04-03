package stock.com.ui.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class StockPojo :BasePojo(){


    @SerializedName("stock")
    @Expose
    var stockList: List<Stock>? = null


    inner class Stock{
        @SerializedName("id")
        @Expose
        var id:String="";

        @SerializedName("stockid")
        @Expose
        var stockid:String="";

        @SerializedName("symbol")
        @Expose
        var symbol:String="";

        @SerializedName("image")
        @Expose
        var image:String="";

        @SerializedName("previousClose")
        @Expose
        var previousClose:String="";

        @SerializedName("latestVolume")
        @Expose
        var latestVolume:String="";

        @SerializedName("marketopen")
        @Expose
        var marketopen:String=""

        @SerializedName("marketclose")
        @Expose
        var marketclose:String="";

        @SerializedName("latestPrice")
        @Expose
        var latestPrice:String="";

        @SerializedName("changePercent")
        @Expose
        var changePercent:String="";

        @SerializedName("sector")
        @Expose
        var sector:String=""

        @SerializedName("companyName")
        @Expose
        var companyName:String="";



    }



}