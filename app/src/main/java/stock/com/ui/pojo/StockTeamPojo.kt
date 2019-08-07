package stock.com.ui.pojo

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.util.ArrayList

class StockTeamPojo() : BasePojo() {
    @SerializedName("stock")
    @Expose
    var stock: ArrayList<Stock>? = null

    class Stock() : Parcelable {

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
        @SerializedName("changePercent")
        @Expose
        var changePercent: String? = null
        @SerializedName("stock_type")
        @Expose
        var stock_type: String? = null
        @SerializedName("sector")
        @Expose
        var sector: String? = null
        @SerializedName("addedStock")
        @Expose
        var addedStock: String? = null
        var addedToList = 1
        var flagAddStockToList: Boolean = false

        constructor(parcel: Parcel) : this() {
            stockid = parcel.readInt()
            symbol = parcel.readString()
            image = parcel.readString()
            companyName = parcel.readString()
            previousClose = parcel.readString()
            latestVolume = parcel.readString()
            marketopen = parcel.readString()
            marketclose = parcel.readString()
            latestPrice = parcel.readString()
            changePercent = parcel.readString()
            stock_type = parcel.readString()
            sector = parcel.readString()
            addedStock = parcel.readString()
            addedToList = parcel.readInt()
            flagAddStockToList = parcel.readByte() != 0.toByte()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(stockid)
            parcel.writeString(symbol)
            parcel.writeString(image)
            parcel.writeString(companyName)
            parcel.writeString(previousClose)
            parcel.writeString(latestVolume)
            parcel.writeString(marketopen)
            parcel.writeString(marketclose)
            parcel.writeString(latestPrice)
            parcel.writeString(changePercent)
            parcel.writeString(stock_type)
            parcel.writeString(sector)
            parcel.writeString(addedStock)
            parcel.writeInt(addedToList)
            parcel.writeByte(if (flagAddStockToList) 1 else 0)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Stock> {
            override fun createFromParcel(parcel: Parcel): Stock {
                return Stock(parcel)
            }

            override fun newArray(size: Int): Array<Stock?> {
                return arrayOfNulls(size)
            }
        }

    }


}
