package stock.com.ui.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class LobbyContestPojo : BasePojo() {
    @SerializedName("contest")
    @Expose
    var contest: List<Contest>? = null

    inner class Contest {

        @SerializedName("schedule_id")
        @Expose
        var scheduleId: String? = null
        @SerializedName("contest_id")
        @Expose
        var contestId: String? = null
        @SerializedName("exchangeid")
        @Expose
        var exchangeid: String? = null
        @SerializedName("exchangename")
        @Expose
        var exchangename: String? = null
        @SerializedName("exchangeimage")
        @Expose
        var exchangeimage: String = ""
        @SerializedName("scheduleid")
        @Expose
        var scheduleid: String? = null
        @SerializedName("schedule_name")
        @Expose
        var scheduleName: String? = null
        @SerializedName("schedule_start")
        @Expose
        var scheduleStart: String = ""
        @SerializedName("winning_amount")
        @Expose
        var winningAmount: String? = null
        @SerializedName("contestid")
        @Expose
        var contestid: String? = null
        @SerializedName("category_id")
        @Expose
        var categoryId: String? = null
        @SerializedName("contest_size")
        @Expose
        var contestSize: String= ""
        @SerializedName("contest_type")
        @Expose
        var contestType: String? = null
        @SerializedName("entry_fees")
        @Expose
        var entryFees: String? = null
        @SerializedName("catname")
        @Expose
        var catname: String? = null
        @SerializedName("description")
        @Expose
        var description: String? = null
        @SerializedName("teams_joined")
        @Expose
        var teamsJoined: String = ""
        @SerializedName("price_break")
        @Expose
        var priceBreak: List<PriceBreak>? = null
        @SerializedName("total_winners")
        @Expose
        var totalWinners: String? = null

    }

    inner class PriceBreak {

        @SerializedName("contest_id")
        @Expose
        var contestId: String? = null
        @SerializedName("start_num")
        @Expose
        var startNum: String? = null
        @SerializedName("end_num")
        @Expose
        var endNum: String? = null
        @SerializedName("price_each")
        @Expose
        var priceEach: String? = null

    }
}