package stock.com.ui.signup.apiResponse.signup

import android.os.Parcel
import android.os.Parcelable

class Data() : Parcelable {
    var first_name: String = ""
    var last_name: String = ""
    var role_id: String = ""
    var email: String = ""
    var phone: String = ""
    var password: String = ""

    var language: String = ""
    var refer_id: String = ""
    var otp: String = ""
    var id: String = ""
    var user_id: String = ""
    var full_name: String = ""

    constructor(parcel: Parcel) : this() {
        first_name = parcel.readString()
        last_name = parcel.readString()
        role_id = parcel.readString()
        email = parcel.readString()
        phone = parcel.readString()
        password = parcel.readString()
        language = parcel.readString()
        refer_id = parcel.readString()
        otp = parcel.readString()
        id = parcel.readString()
        user_id = parcel.readString()
        full_name = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(first_name)
        parcel.writeString(last_name)
        parcel.writeString(role_id)
        parcel.writeString(email)
        parcel.writeString(phone)
        parcel.writeString(password)
        parcel.writeString(language)
        parcel.writeString(refer_id)
        parcel.writeString(otp)
        parcel.writeString(id)
        parcel.writeString(user_id)
        parcel.writeString(full_name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Data> {
        override fun createFromParcel(parcel: Parcel): Data {
            return Data(parcel)
        }

        override fun newArray(size: Int): Array<Data?> {
            return arrayOfNulls(size)
        }
    }

}