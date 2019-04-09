package stock.com.ui.pojo

import android.os.Parcel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

 class PriceBreak protected constructor(`in`: Parcel) : Serializable {

    @SerializedName("contest_id")
    @Expose
    var contestId: Int? = null
    @SerializedName("start_num")
    @Expose
    var startNum: Int? = null
    @SerializedName("end_num")
    @Expose
    var endNum: Int? = null
    @SerializedName("price_each")
    @Expose
    var priceEach: String? = null

    init {
        if (`in`.readByte().toInt() == 0) {
            contestId = null
        } else {
            contestId = `in`.readInt()
        }
        if (`in`.readByte().toInt() == 0) {
            startNum = null
        } else {
            startNum = `in`.readInt()
        }
        if (`in`.readByte().toInt() == 0) {
            endNum = null
        } else {
            endNum = `in`.readInt()
        }
        priceEach = `in`.readString()
    }
}