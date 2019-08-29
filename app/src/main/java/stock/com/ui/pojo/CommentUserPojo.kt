package stock.com.ui.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.hendraanggrian.appcompat.socialview.Mentionable
import java.io.Serializable


class CommentUserPojo : BasePojo() {
    @SerializedName("users")
    @Expose
    var users: ArrayList<User>? = null

    inner class User : Serializable, Mentionable {
        override val username: String
            get() = username_
        override val displayname: String?
            get() = firstName
        override val avatar: String?
            get() = profile_image

        @SerializedName("id")
        @Expose
        var id: Int? = null
        @SerializedName("first_name")
        @Expose
        var firstName: String? = null
        @SerializedName("username")
        @Expose
        var username_: String = ""
        @SerializedName("profile_image")
        @Expose
        var profile_image: String? = null

    }
}