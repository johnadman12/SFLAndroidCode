package stock.com.ui.pojo

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class CandlesticChartmarket() : Parcelable {

    @SerializedName("status")
    @Expose
    var statuss: Status? = null
    @SerializedName("data")
    @Expose
    var data: Data? = null

    constructor(parcel: Parcel) : this() {

    }

    inner class Quote {

        @SerializedName("time_open")
        @Expose
        var timeOpen: String = ""
        @SerializedName("time_close")
        @Expose
        var timeClose: String = ""
        @SerializedName("quote")
        @Expose
        var quote: Quote_? = null

    }

    inner class Data {

        @SerializedName("id")
        @Expose
        var id: Int? = null
        @SerializedName("name")
        @Expose
        var name: String? = null
        @SerializedName("symbol")
        @Expose
        var symbol: String? = null
        @SerializedName("quotes")
        @Expose
        var quotes: ArrayList<Quote>? = null

    }

    inner class Quote_ {

        @SerializedName("USD")
        @Expose
        var uSD: USD? = null

    }

    inner class Status {

        @SerializedName("timestamp")
        @Expose
        var timestamp: String? = null
        @SerializedName("error_code")
        @Expose
        var errorCode: Int? = null
        @SerializedName("error_message")
        @Expose
        var errorMessage: Any? = null
        @SerializedName("elapsed")
        @Expose
        var elapsed: Int? = null
        @SerializedName("credit_count")
        @Expose
        var creditCount: Int? = null

    }

    inner class USD {

        @SerializedName("open")
        @Expose
        var open: Float = 0.0f
        @SerializedName("high")
        @Expose
        var high: Float = 0.0f
        @SerializedName("low")
        @Expose
        var low: Float = 0.0f
        @SerializedName("close")
        @Expose
        var close: Float = 0.0f
        @SerializedName("volume")
        @Expose
        var volume: Float = 0.0f
        @SerializedName("market_cap")
        @Expose
        var marketCap: Float = 0.0f
        @SerializedName("timestamp")
        @Expose
        var timestamp: String = ""

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CandlesticChartmarket> {
        override fun createFromParcel(parcel: Parcel): CandlesticChartmarket {
            return CandlesticChartmarket(parcel)
        }

        override fun newArray(size: Int): Array<CandlesticChartmarket?> {
            return arrayOfNulls(size)
        }
    }
}