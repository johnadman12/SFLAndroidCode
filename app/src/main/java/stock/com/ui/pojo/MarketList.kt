package stock.com.ui.pojo

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.util.ArrayList

class MarketList : BasePojo() {
    @SerializedName("crypto")
    @Expose
    var crypto: ArrayList<Crypto>? = null

    class Crypto() : Parcelable {

        @SerializedName("cryptocurrencyid")
        @Expose
        var cryptocurrencyid: Int = 0
        @SerializedName("name")
        @Expose
        var name: String? = null
        @SerializedName("symbol")
        @Expose
        var symbol: String? = null
        @SerializedName("cmc_rank")
        @Expose
        var cmcRank: Int = 0
        @SerializedName("logo")
        @Expose
        var logo: String? = null
        @SerializedName("marketcapital")
        @Expose
        var marketcapital: String? = null
        @SerializedName("supply")
        @Expose
        var supply: String? = null
        @SerializedName("decimalchange")
        @Expose
        var decimalchange: String? = null
        @SerializedName("latestPrice")
        @Expose
        var latestPrice: String? = null
        @SerializedName("latestVolume")
        @Expose
        var latestVolume: String? = null
        @SerializedName("changeper")
        @Expose
        var changeper: String? = null
        @SerializedName("image")
        @Expose
        var image: String? = null
        @SerializedName("crypto_type")
        @Expose
        var cryptoType: String? = null
        var addedToList = 1

        constructor(parcel: Parcel) : this() {
            cryptocurrencyid = parcel.readInt()
            name = parcel.readString()
            symbol = parcel.readString()
            cmcRank = parcel.readInt()
            logo = parcel.readString()
            marketcapital = parcel.readString()
            supply = parcel.readString()
            decimalchange = parcel.readString()
            latestPrice = parcel.readString()
            latestVolume = parcel.readString()
            changeper = parcel.readString()
            image = parcel.readString()
            cryptoType = parcel.readString()
            addedToList = parcel.readInt()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(cryptocurrencyid)
            parcel.writeString(name)
            parcel.writeString(symbol)
            parcel.writeInt(cmcRank)
            parcel.writeString(logo)
            parcel.writeString(marketcapital)
            parcel.writeString(supply)
            parcel.writeString(decimalchange)
            parcel.writeString(latestPrice)
            parcel.writeString(latestVolume)
            parcel.writeString(changeper)
            parcel.writeString(image)
            parcel.writeString(cryptoType)
            parcel.writeInt(addedToList)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Crypto> {
            override fun createFromParcel(parcel: Parcel): Crypto {
                return Crypto(parcel)
            }

            override fun newArray(size: Int): Array<Crypto?> {
                return arrayOfNulls(size)
            }
        }


    }
}
