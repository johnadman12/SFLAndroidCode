package stock.com.ui.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import stock.com.ui.pojo.HomePojo.Banner
import stock.com.ui.pojo.HomePojo.FeatureContest


class HomePojo : BasePojo() {
    @SerializedName("featureContest")
    @Expose
    var featureContest: ArrayList<FeatureContest>? = null
    @SerializedName("banner")
    @Expose
    var banner: ArrayList<Banner>? = null

    @SerializedName("news")
    @Expose
    var news: ArrayList<News>? = null
    @SerializedName("exchange")
    @Expose
    var exchange: ArrayList<Exchange>? = null

    inner class Banner {

        @SerializedName("id")
        @Expose
        var id: String = ""
        @SerializedName("type")
        @Expose
        var type: Int = 0
        @SerializedName("name")
        @Expose
        var name: String = ""
        @SerializedName("image")
        @Expose
        var image: String = ""

    }

    inner class FeatureContest {

        @SerializedName("contestid")
        @Expose
        var contestid: Int = 0
        @SerializedName("category_id")
        @Expose
        var categoryId: String = ""
        @SerializedName("winning_amount")
        @Expose
        var winningAmount: String = ""
        @SerializedName("contest_size")
        @Expose
        var contestSize: String = ""
        @SerializedName("contest_type")
        @Expose
        var contestType: String = ""
        @SerializedName("entry_fees")
        @Expose
        var entryFees: String = ""
        @SerializedName("scheduleid")
        @Expose
        var scheduleid: String = ""
        @SerializedName("schedule_name")
        @Expose
        var scheduleName: String = ""
        @SerializedName("schedule_start")
        @Expose
        var scheduleStart: String = ""
        @SerializedName("catname")
        @Expose
        var catname: String = ""
        @SerializedName("description")
        @Expose
        var description: String = ""
        @SerializedName("confirm_winning")
        @Expose
        var confirm_winning: String = ""
        @SerializedName("join_multiple")
        @Expose
        var join_multiple: String = ""
        @SerializedName("exchangeid")
        @Expose
        var exchangeid: Int = 0
        @SerializedName("contest_teamremaining")
        @Expose
        var contest_teamremaining: Int = 0
        @SerializedName("exchangename")
        @Expose
        var exchangename: String = ""
        @SerializedName("exchangeimage")
        @Expose
        var exchangeimage: String = ""
        @SerializedName("teams_joined")
        @Expose
        var teamsJoined: String = ""
        @SerializedName("total_winners")
        @Expose
        var totalwinners: String = ""
        @SerializedName("mid")
        @Expose
        var mid: String = ""
        @SerializedName("marketname")
        @Expose
        var marketname: String = ""
        @SerializedName("price_break")
        @Expose
        var priceBreak: ArrayList<PriceBreak>? = null

    }

    inner class News {
        @SerializedName("id")
        @Expose
        var id: Int? = null
        @SerializedName("title")
        @Expose
        var title: String? = null
        @SerializedName("channel")
        @Expose
        var channel: String? = null
        @SerializedName("description")
        @Expose
        var description: String? = null
        @SerializedName("image")
        @Expose
        var image: String? = null
        @SerializedName("newstime")
        @Expose
        var newstime: String? = null
        @SerializedName("newspercentage")
        @Expose
        var newspercentage: String? = null
        @SerializedName("timeleft")
        @Expose
        var timeleft: String? = null
    }

    inner class Exchange {
        @SerializedName("id")
        @Expose
        var id: Int? = null
        @SerializedName("name")
        @Expose
        var name: String = ""
        @SerializedName("changePercent")
        @Expose
        var changePercent: String? = null
        @SerializedName("latestPrice")
        @Expose
        var latestPrice: String? = null
        @SerializedName("image_url")
        @Expose
        var image_url: String? = null

    }

}
