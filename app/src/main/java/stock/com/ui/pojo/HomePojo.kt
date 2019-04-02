package stock.com.ui.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import stock.com.ui.pojo.HomePojo.Banner
import stock.com.ui.pojo.HomePojo.FeatureContest


class HomePojo : BasePojo() {
    @SerializedName("featureContest")
    @Expose
    var featureContest: List<FeatureContest>? = null
    @SerializedName("traniningContest")
    @Expose
    var traniningContest: List<TraniningContest>? = null
    @SerializedName("banner")
    @Expose
    var banner: List<Banner>? = null

    inner class Banner {

        @SerializedName("id")
        @Expose
        var id: String =""
        @SerializedName("type")
        @Expose
        var type: Int =0
        @SerializedName("name")
        @Expose
        var name: String =""
        @SerializedName("image")
        @Expose
        var image: String =""

    }

    inner class FeatureContest {

        @SerializedName("contestid")
        @Expose
        var contestid: String =""
        @SerializedName("category_id")
        @Expose
        var categoryId: String =""
        @SerializedName("winning_amount")
        @Expose
        var winningAmount: String =""
        @SerializedName("contest_size")
        @Expose
        var contestSize: String =""
        @SerializedName("contest_type")
        @Expose
        var contestType: String =""
        @SerializedName("entry_fees")
        @Expose
        var entryFees: String =""
        @SerializedName("scheduleid")
        @Expose
        var scheduleid: String =""
        @SerializedName("schedule_name")
        @Expose
        var scheduleName: String =""
        @SerializedName("schedule_start")
        @Expose
        var scheduleStart: String =""
        @SerializedName("catname")
        @Expose
        var catname: String =""
        @SerializedName("description")
        @Expose
        var description: String =""
        @SerializedName("exchangeid")
        @Expose
        var exchangeid: String =""
        @SerializedName("exchangename")
        @Expose
        var exchangename: String =""
        @SerializedName("exchangeimage")
        @Expose
        var exchangeimage: String=""
        @SerializedName("teams_joined")
        @Expose
        var teamsJoined: String =""
        @SerializedName("price_break")
        @Expose
        var priceBreak: List<PriceBreak>? = null

    }

    inner class PriceBreak {

        @SerializedName("name")
        @Expose
        var name: String =""
        @SerializedName("start_num")
        @Expose
        var startNum: String =""
        @SerializedName("end_num")
        @Expose
        var endNum: String =""
        @SerializedName("price_each")
        @Expose
        var priceEach: String =""

    }

    inner class TraniningContest {

        @SerializedName("contestid")
        @Expose
        var contestid: String =""
        @SerializedName("category_id")
        @Expose
        var categoryId: String =""
        @SerializedName("winning_amount")
        @Expose
        var winningAmount: String =""
        @SerializedName("contest_size")
        @Expose
        var contestSize: String =""
        @SerializedName("contest_type")
        @Expose
        var contestType: String =""
        @SerializedName("entry_fees")
        @Expose
        var entryFees: String =""
        @SerializedName("scheduleid")
        @Expose
        var scheduleid: String =""
        @SerializedName("schedule_name")
        @Expose
        var scheduleName: String =""
        @SerializedName("schedule_start")
        @Expose
        var scheduleStart: String =""
        @SerializedName("catname")
        @Expose
        var catname: String =""
        @SerializedName("description")
        @Expose
        var description: String =""
        @SerializedName("exchangeid")
        @Expose
        var exchangeid: String =""
        @SerializedName("exchangename")
        @Expose
        var exchangename: String =""
        @SerializedName("exchangeimage")
        @Expose
        var exchangeimage: String =""
        @SerializedName("teams_joined")
        @Expose
        var teamsJoined: String =""
        @SerializedName("price_break")
        @Expose
        var priceBreak: List<PriceBreak>? = null

    }
}
