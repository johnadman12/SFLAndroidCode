package stock.com.ui.pojo

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class CandlesticChartmarket() {

    /*    @SerializedName("status")
        @Expose
        var statuss: Status? = null*/
    @SerializedName("data")
    @Expose
    var data: Data? = null

    class Data() :Serializable {

        @SerializedName("id")
        @Expose
        var id: Int = 0
        @SerializedName("name")
        @Expose
        var name: String? = null
        @SerializedName("symbol")
        @Expose
        var symbol: String? = null
        @SerializedName("quotes")
        @Expose
        var quotes: ArrayList<Quote>? = null





    class Quote():Serializable   {

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

    class Quote_() :Serializable {

        @SerializedName("USD")
        @Expose
        var uSD: USD? = null


    }

    class Status():Serializable  {

        @SerializedName("timestamp")
        @Expose
        var timestamp: String? = null
        @SerializedName("error_code")
        @Expose
        var errorCode: Int? = null
        @SerializedName("error_message")
        @Expose
        var errorMessage: String = ""
        @SerializedName("elapsed")
        @Expose
        var elapsed: Int? = null
        @SerializedName("credit_count")
        @Expose
        var creditCount: Int? = null


    }

    class USD():Serializable  {

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
}

}
