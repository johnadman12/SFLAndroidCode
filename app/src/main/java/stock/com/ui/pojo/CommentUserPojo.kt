package stock.com.ui.pojo

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.linkedin.android.spyglass.mentions.Mentionable


class CommentUserPojo : BasePojo() {
    @SerializedName("users")
    @Expose
    var users: ArrayList<User>? = null

    @SuppressLint("ParcelCreator")
    public class User() : com.linkedin.android.spyglass.mentions.Mentionable {
        override fun getDeleteStyle(): Mentionable.MentionDeleteStyle {
            return Mentionable.MentionDeleteStyle.PARTIAL_NAME_DELETE
        }

        override fun getTextForDisplayMode(mode: Mentionable.MentionDisplayMode?): String {
            when (mode) {
                Mentionable.MentionDisplayMode.FULL -> return firstName
                Mentionable.MentionDisplayMode.PARTIAL -> {
                    return firstName
                }
                Mentionable.MentionDisplayMode.NONE -> return ""
                else -> return ""
            } }

        override fun getSuggestiblePrimaryText(): String {
           return username_
        }

        override fun getSuggestibleId(): Int {
           return id
        }
        /* override val username: String
             get() = username_
         override val displayname: String?
             get() = firstName
         override val avatar: String?
             get() = profile_image*/

        @SerializedName("id")
        @Expose
        var id: Int = 0
        @SerializedName("first_name")
        @Expose
        var firstName: String = ""
        @SerializedName("username")
        @Expose
        var username_: String = ""
        @SerializedName("profile_image")
        @Expose
        var profile_image: String? = null

        constructor(parcel: Parcel) : this() {
            id = parcel.readInt()
            firstName = parcel.readString()
            username_ = parcel.readString()
            profile_image = parcel.readString()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeValue(id)
            parcel.writeString(firstName)
            parcel.writeString(username_)
            parcel.writeString(profile_image)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<User> {
            override fun createFromParcel(parcel: Parcel): User {
                return User(parcel)
            }

            override fun newArray(size: Int): Array<User?> {
                return arrayOfNulls(size)
            }
        }

    }
}