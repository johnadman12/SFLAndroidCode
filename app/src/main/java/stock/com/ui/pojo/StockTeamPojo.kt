package stock.com.ui.pojo

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

import java.util.ArrayList

class StockTeamPojo() : BasePojo() {
    @SerializedName("stock")
    @Expose
    var stock: ArrayList<Stock>? = null

    class Stock() : Serializable {

        @SerializedName("stockid")
        @Expose
        var stockid: Int = 0
        @SerializedName("symbol")
        @Expose
        var symbol: String? = null
        @SerializedName("image")
        @Expose
        var image: String? = null
        @SerializedName("companyName")
        @Expose
        var companyName: String? = null
        @SerializedName("slug")
        @Expose
        var slug: String? = null
        @SerializedName("previousClose")
        @Expose
        var previousClose: String? = null
        @SerializedName("latestVolume")
        @Expose
        var latestVolume: String? = null
        @SerializedName("marketopen")
        @Expose
        var marketopen: String? = null
        @SerializedName("marketclose")
        @Expose
        var marketclose: String? = null
        @SerializedName("latestPrice")
        @Expose
        var latestPrice: String? = null
            set(value) {
                field = value
            }
        @SerializedName("changePercent")
        @Expose
        var changePercent: String? = ""
        @SerializedName("stock_type")
        @Expose
        var stock_type: String? = ""
        @SerializedName("sector")
        @Expose
        var sector: String? = null
        @SerializedName("addedStock")
        @Expose
        var addedStock: String? = null
        var addedToList = 1
        var flagAddStockToList: Boolean = false




    }


}
