package stock.com.ui.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class HomePojo : BasePojo() {
    @SerializedName("featureContest")
    @Expose
    var featureContest: List<FeatureContest>? = null
    @SerializedName("banner")
    @Expose
    var banner: List<Banner>? = null

    inner class Banner {
        @SerializedName("id")
        @Expose
        var id: Int = 0
        @SerializedName("type")
        @Expose
        var type: Int = 0
        @SerializedName("name")
        @Expose
        var name: String? = null
        @SerializedName("image")
        @Expose
        var image: String? = null

    }

    inner class FeatureContest {
        @SerializedName("id")
        @Expose
        var id: Int = 0
        @SerializedName("category_id")
        @Expose
        var categoryId: Int = 0
        @SerializedName("winning_amount")
        @Expose
        var winningAmount: Int = 0
        @SerializedName("contest_size")
        @Expose
        var contestSize: Int = 0
        @SerializedName("contest_type")
        @Expose
        var contestType: String? = null
        @SerializedName("entry_fees")
        @Expose
        var entryFees: Int = 0
        @SerializedName("scheduleid")
        @Expose
        var scheduleid: Int = 0
        @SerializedName("schedule_name")
        @Expose
        var scheduleName: String? = null
        @SerializedName("schedule_start")
        @Expose
        var scheduleStart: String? = null
        @SerializedName("catname")
        @Expose
        var catname: String? = null
        @SerializedName("description")
        @Expose
        var description: String? = null
        @SerializedName("exchangeid")
        @Expose
        var exchangeid: Int = 0
        @SerializedName("exchangename")
        @Expose
        var exchangename: String? = null

    }
}
