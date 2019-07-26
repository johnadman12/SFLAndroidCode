package stock.com.ui.pojo

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class CurrencyPojo : BasePojo() {
    @SerializedName("currency")
    @Expose
    var currency: ArrayList<Currency>? = null

    public class Currency() :Parcelable {

        @SerializedName("currencyid")
        @Expose
        var currencyid: Int = 0
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
        @SerializedName("price")
        @Expose
        var price: String = ""
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
        var addedToList = 1

        constructor(parcel: Parcel) : this() {
            currencyid = parcel.readInt()
            symbol = parcel.readString()
            ask = parcel.readString()
            bid = parcel.readString()
            daychange = parcel.readString()
            name = parcel.readString()
            firstflag = parcel.readString()
            secondflag = parcel.readString()
            latestVolume = parcel.readString()
            changeper = parcel.readString()
            cryptoType = parcel.readString()
            price = parcel.readString()
            addedToList = parcel.readInt()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(currencyid)
            parcel.writeString(symbol)
            parcel.writeString(ask)
            parcel.writeString(bid)
            parcel.writeString(daychange)
            parcel.writeString(name)
            parcel.writeString(firstflag)
            parcel.writeString(secondflag)
            parcel.writeString(latestVolume)
            parcel.writeString(changeper)
            parcel.writeString(price)
            parcel.writeString(cryptoType)
            parcel.writeInt(addedToList)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Currency> {
            override fun createFromParcel(parcel: Parcel): Currency {
                return Currency(parcel)
            }

            override fun newArray(size: Int): Array<Currency?> {
                return arrayOfNulls(size)
            }
        }
    }
}