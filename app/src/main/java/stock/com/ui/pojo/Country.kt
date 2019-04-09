package stock.com.ui.pojo

import android.os.Parcel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Country : BasePojo()  {
    @SerializedName("country")
    @Expose
    var country: ArrayList<CountryPojo>? = null

    inner class CountryPojo protected constructor(`in`: Parcel) : Serializable {
        @SerializedName("id")
        @Expose
        var id: Int? = null
        @SerializedName("name")
        @Expose
        var name: String? = null
        @SerializedName("alpha_2")
        @Expose
        var alpha2: String? = null
        @SerializedName("flag_url_1616")
        @Expose
        var flagUrl1616: String? = null
        @SerializedName("flag_url_6464")
        @Expose
        var flagUrl6464: String? = null


        init {
            if (`in`.readByte().toInt() == 0) {
                id = null
            } else {
                name = `in`.readString()
            }
            if (`in`.readByte().toInt() == 0) {
                alpha2 = null
            } else {
                flagUrl1616 = `in`.readString()
            }
            if (`in`.readByte().toInt() == 0) {
                flagUrl6464 = null
            } else {
                flagUrl6464 = `in`.readString()
            }
        }
    }
}