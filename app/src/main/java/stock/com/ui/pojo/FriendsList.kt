package stock.com.ui.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class FriendsList : BasePojo() {
    var pending_request: Int = 0
    @SerializedName("user_data")
    @Expose
    var userData: ArrayList<UserDatum>? = null

    inner class UserDatum {

        @SerializedName("id")
        @Expose
        var id: String = ""
        @SerializedName("username")
        @Expose
        var username: String = ""
        @SerializedName("email")
        @Expose
        var email: String = ""
        @SerializedName("profile_image")
        @Expose
        var profileImage: String = ""
        @SerializedName("level_type")
        @Expose
        var levelType: String = ""
        @SerializedName("invite_status")
        @Expose
        var invite_status: String = ""

    }
}