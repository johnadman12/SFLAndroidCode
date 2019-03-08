package stock.com.model

import android.os.Parcel
import android.os.Parcelable

class SocialModel() : Parcelable {
    internal var image: String = ""
    internal var first_name: String = ""
    internal var last_name: String = ""
    internal var name: String = ""
    internal var fb_id: String = ""
    internal var google_id: String = ""
    internal var social_id: String = ""
    internal var type: String = ""
    internal var device_type: String = ""
    internal var device_token: String = ""
    internal var email: String = ""
    internal var isSocial: String = ""
    internal var birthday: String = ""

    constructor(parcel: Parcel) : this() {
        image = parcel.readString()
        first_name = parcel.readString()
        last_name = parcel.readString()
        name = parcel.readString()
        fb_id = parcel.readString()
        google_id = parcel.readString()
        social_id = parcel.readString()
        type = parcel.readString()
        device_type = parcel.readString()
        device_token = parcel.readString()
        email = parcel.readString()
        birthday = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(image)
        parcel.writeString(first_name)
        parcel.writeString(last_name)
        parcel.writeString(name)
        parcel.writeString(fb_id)
        parcel.writeString(google_id)
        parcel.writeString(social_id)
        parcel.writeString(type)
        parcel.writeString(device_type)
        parcel.writeString(device_token)
        parcel.writeString(email)
        parcel.writeString(birthday)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SocialModel> {
        override fun createFromParcel(parcel: Parcel): SocialModel {
            return SocialModel(parcel)
        }

        override fun newArray(size: Int): Array<SocialModel?> {
            return arrayOfNulls(size)
        }
    }


}