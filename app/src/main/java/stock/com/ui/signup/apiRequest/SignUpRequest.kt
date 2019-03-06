package stock.com.ui.signup.apiRequest

import android.os.Parcel
import android.os.Parcelable

class SignUpRequest() : Parcelable {
    var name: String = ""
    var email: String = ""
    var fb_id: String = ""
    var google_id: String = ""
    var mobile_number: String = ""
    var password: String = ""
    var language: String = ""
    var invite_code: String = ""

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        email = parcel.readString()
        fb_id = parcel.readString()
        google_id = parcel.readString()
        mobile_number = parcel.readString()
        password = parcel.readString()
        language = parcel.readString()
        invite_code = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(fb_id)
        parcel.writeString(google_id)
        parcel.writeString(mobile_number)
        parcel.writeString(password)
        parcel.writeString(language)
        parcel.writeString(invite_code)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SignUpRequest> {
        override fun createFromParcel(parcel: Parcel): SignUpRequest {
            return SignUpRequest(parcel)
        }

        override fun newArray(size: Int): Array<SignUpRequest?> {
            return arrayOfNulls(size)
        }
    }


}