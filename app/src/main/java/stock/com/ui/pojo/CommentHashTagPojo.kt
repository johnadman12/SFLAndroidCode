package stock.com.ui.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class CommentHashTagPojo:BasePojo(){
    @SerializedName("market")
    @Expose
    var market: ArrayList<Market>? = null

    inner class Market:Serializable,Hashtagable {
        override fun getId(): Int {
            return id_
        }

        override fun getName(): String {
           return name_
        }

        override fun getsymbol(): String {
          return symbol
        }

        override fun getmarketId(): Int {
            return marketId
        }

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

    }
}