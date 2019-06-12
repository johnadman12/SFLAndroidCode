package stock.com.ui.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class StatisticsPojo : BasePojo() {
    @SerializedName("staticprofile")
    @Expose
    var staticprofile: List<Staticprofile>? = null

    inner class Staticprofile {
        @SerializedName("contest_joined")
        @Expose
        var contestJoined: String? = null
        @SerializedName("contest_won")
        @Expose
        var contestWon: String? = null
        @SerializedName("total_spent")
        @Expose
        var totalSpent: String? = null
        @SerializedName("total_winning")
        @Expose
        var totalWinning: String? = null
        @SerializedName("userId")
        @Expose
        var userId: String? = null
        @SerializedName("username")
        @Expose
        var username: String? = null
        @SerializedName("profile_image")
        @Expose
        var profileImage: String? = null
        @SerializedName("address")
        @Expose
        var address: String? = null
        @SerializedName("winning_per")
        @Expose
        var winningPer: String? = null
        @SerializedName("contest_lost")
        @Expose
        var contestLost: String? = null
        @SerializedName("level_type")
        @Expose
        var levelType: String? = null

    }
}