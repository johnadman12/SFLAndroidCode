package stock.com.ui.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class CommentHashTagPojo:BasePojo(){
    @SerializedName("market")
    @Expose
    var market: ArrayList<Market>? = null

    inner class Market {

        @SerializedName("id")
        @Expose
        var id: Int? = null
        @SerializedName("name")
        @Expose
        var name: String? = null
        @SerializedName("symbol")
        @Expose
        var symbol: String? = null
        @SerializedName("market_id")
        @Expose
        var marketId: Int? = null

    }
}