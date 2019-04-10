package stock.com.ui.pojo

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Country : BasePojo()  {
    @SerializedName("country")
    @Expose
   public var country: ArrayList<CountryPojo>? = null

    @SuppressLint("ParcelCreator")
    inner class CountryPojo protected constructor(`in`: Parcel) : Parcelable {
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


        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeValue(id)
            parcel.writeString(name)
            parcel.writeString(alpha2)
            parcel.writeString(flagUrl1616)
            parcel.writeString(flagUrl6464)
        }

        override fun describeContents(): Int {
            return 0
        }



    }
}