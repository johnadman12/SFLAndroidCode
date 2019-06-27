package stock.com.ui.pojo

import stock.com.ui.pojo.UserPojo.User
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class OtherProfile : BasePojo() {
    @SerializedName("user")
    @Expose
    var user: ArrayList<User>? = null

    inner class User {

        @SerializedName("contest_invite")
        @Expose
        var contestInvite:  String = ""
        @SerializedName("country_id")
        @Expose
        var countryId:  String = ""
        @SerializedName("level_type")
        @Expose
        var levelType:  String = ""
        @SerializedName("zipcode")
        @Expose
        var zipcode:  String = ""
        @SerializedName("profile_image")
        @Expose
        var profileImage:  String = ""
        @SerializedName("username")
        @Expose
        var username:  String = ""
        @SerializedName("address")
        @Expose
        var address: String = ""
        @SerializedName("phone_number")
        @Expose
        var phoneNumber:  String = ""
        @SerializedName("id")
        @Expose
        var id:  String = ""
        @SerializedName("email")
        @Expose
        var email:  String = ""
        @SerializedName("biography")
        @Expose
        var biography:  String = ""

    }
}