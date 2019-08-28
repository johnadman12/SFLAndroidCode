package stock.com.ui.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class CommentUserPojo : BasePojo() {

    @SerializedName("users")
    @Expose
    var users: ArrayList<User>? = null

    inner class User : Serializable {

        @SerializedName("id")
        @Expose
        var id: Int? = null
        @SerializedName("first_name")
        @Expose
        var firstName: String? = null
        @SerializedName("username")
        @Expose
        var username: String? = null
        @SerializedName("profile_image")
        @Expose
        var profile_image: String? = null

    }
}