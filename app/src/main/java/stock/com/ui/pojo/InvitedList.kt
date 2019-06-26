package stock.com.ui.pojo

import stock.com.ui.pojo.CreateContest.Usercontest
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class InvitedList : BasePojo() {
    @SerializedName("usercontest")
    @Expose
    var usercontest: List<Usercontest>? = null

    inner class Usercontest {

        @SerializedName("contest_teamremaining")
        @Expose
        var contestTeamremaining:  String= ""
        @SerializedName("teams_joined")
        @Expose
        var teamsJoined:  String= ""
        @SerializedName("contestid")
        @Expose
        var contestid:  String= ""
        @SerializedName("category_id")
        @Expose
        var categoryId:  String= ""
        @SerializedName("winning_amount")
        @Expose
        var winningAmount:  String= ""
        @SerializedName("invited_code")
        @Expose
        var invitedCode:  String= ""
        @SerializedName("contest_size")
        @Expose
        var contestSize: String= ""
        @SerializedName("contest_type")
        @Expose
        var contestType:  String= ""
        @SerializedName("entry_fees")
        @Expose
        var entryFees:  String= ""
        @SerializedName("join_multiple")
        @Expose
        var joinMultiple:  String= ""
        @SerializedName("scheduleid")
        @Expose
        var scheduleid: String= ""
        @SerializedName("schedule_name")
        @Expose
        var scheduleName:  String= ""
        @SerializedName("schedule_start")
        @Expose
        var scheduleStart:  String= ""
        @SerializedName("schedule_end")
        @Expose
        var scheduleEnd:  String= ""
        @SerializedName("catname")
        @Expose
        var catname: String? = null
        @SerializedName("description")
        @Expose
        var description:  String= ""
        @SerializedName("exchangeid")
        @Expose
        var exchangeid:  String= ""
        @SerializedName("exchangename")
        @Expose
        var exchangename:  String= ""
        @SerializedName("exchangeimage")
        @Expose
        var exchangeimage:  String= ""
        @SerializedName("mid")
        @Expose
        var mid:  String= ""
        @SerializedName("marketname")
        @Expose
        var marketname: String= ""
        @SerializedName("manageInvitedId")
        @Expose
        var manageInvitedId:  String= ""
        @SerializedName("invite_status")
        @Expose
        var inviteStatus:  String= ""
        @SerializedName("invite_user_id")
        @Expose
        var inviteUserId:  String= ""
        @SerializedName("price_break")
        @Expose
        var priceBreak: ArrayList<PriceBreak>? = null
        @SerializedName("total_winners")
        @Expose
        var totalWinners:  String= ""

    }

    inner class PriceBreak {

        @SerializedName("contest_id")
        @Expose
        var contestId:  String= ""
        @SerializedName("start_num")
        @Expose
        var startNum:  String= ""
        @SerializedName("end_num")
        @Expose
        var endNum:  String= ""
        @SerializedName("price_each")
        @Expose
        var priceEach:  String= ""

    }
}