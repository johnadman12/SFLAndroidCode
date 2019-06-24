package stock.com.ui.pojo

import stock.com.ui.pojo.FriendsList.UserDatum
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class PendingList : BasePojo() {
    @SerializedName("user_data")
    @Expose
    var userData: ArrayList<UserDatum>? = null

    inner class UserDatum {

        @SerializedName("manage_friendId")
        @Expose
        var manageFriendId: String = ""
        @SerializedName("user_id")
        @Expose
        var userId: String = ""
        @SerializedName("username")
        @Expose
        var username: String? = null
        @SerializedName("email")
        @Expose
        var email: String = ""
        @SerializedName("profile_image")
        @Expose
        var profileImage: String? = null
        @SerializedName("invite_status")
        @Expose
        var inviteStatus: String = ""
        @SerializedName("level_type")
        @Expose
        var levelType: String = ""

    }
}