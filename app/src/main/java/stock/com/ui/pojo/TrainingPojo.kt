package stock.com.ui.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TrainingPojo : BasePojo() {
    @SerializedName("trainingContest")
    @Expose
    var traniningContest: List<TraniningContest>? = null

    inner class TraniningContest {

        @SerializedName("contestid")
        @Expose
        var contestid: Int = 0
        @SerializedName("contest_teamremaining")
        @Expose
        var contest_teamremaining: Int = 0
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
        @SerializedName("exchangeid")
        @Expose
        var exchangeid: Int = 0
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
        @SerializedName("price_break")
        @Expose
        var priceBreak: ArrayList<PriceBreak>? = null

    }

}
