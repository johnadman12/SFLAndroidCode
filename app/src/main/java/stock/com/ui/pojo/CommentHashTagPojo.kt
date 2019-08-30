package stock.com.ui.pojo

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.linkedin.android.spyglass.mentions.Mentionable
import java.io.Serializable


class CommentHashTagPojo:BasePojo(){
    @SerializedName("market")
    @Expose
    var market: ArrayList<Market>? = null

     class Market() :com.linkedin.android.spyglass.mentions.Mentionable {
        override fun getSuggestibleId(): Int {
        return id_
        }

        override fun getSuggestiblePrimaryText(): String {
           return  symbol
        }

        override fun getTextForDisplayMode(mode: Mentionable.MentionDisplayMode?): String {
            when (mode) {
                Mentionable.MentionDisplayMode.FULL -> return name_
                Mentionable.MentionDisplayMode.PARTIAL -> {
                    return name_
                }
                Mentionable.MentionDisplayMode.NONE -> return ""
                else -> return ""
            }     }

        override fun getDeleteStyle(): Mentionable.MentionDeleteStyle {
            return Mentionable.MentionDeleteStyle.PARTIAL_NAME_DELETE   }

        @SerializedName("id")
        @Expose
        var id_: Int = 0
        @SerializedName("name")
        @Expose
        var name_: String = ""
        @SerializedName("symbol")
        @Expose
        var symbol: String = ""
        @SerializedName("market_id")
        @Expose
        var marketId: Int = 0

        constructor(parcel: Parcel) : this() {
            id_ = parcel.readInt()
            name_ = parcel.readString()
            symbol = parcel.readString()
            marketId = parcel.readInt()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(id_)
            parcel.writeString(name_)
            parcel.writeString(symbol)
            parcel.writeInt(marketId)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Market> {
            override fun createFromParcel(parcel: Parcel): Market {
                return Market(parcel)
            }

            override fun newArray(size: Int): Array<Market?> {
                return arrayOfNulls(size)
            }
        }

    }
}