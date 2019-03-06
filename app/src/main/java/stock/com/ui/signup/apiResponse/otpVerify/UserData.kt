package stock.com.ui.signup.apiResponse.otpVerify

import android.os.Parcel
import android.os.Parcelable

class UserData() : Parcelable {
    var id: String = ""
    var first_name: String = ""
    var last_name: String = ""
    var role_id: String = ""
    var gender: String = ""
    var email: String = ""
    var phone: String = ""
    var password: String = ""
    var status: String = ""
    var address: String = ""
    var date_of_bith: String = ""
    var image: String = ""
    var refer_id: String = ""
    var fb_id: String = ""
    var google_id: String = ""
    var otp: String = ""
    var module_access: String = ""
    var is_login: String = ""
    var last_login: String = ""
    var current_password: String = ""
    var language: String = ""
    var device_id: String = ""
    var device_type: String = ""
    var created: String = ""
    var modified: String = ""
    var user_id: String = ""
    var full_name: String = ""

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        first_name = parcel.readString()
        last_name = parcel.readString()
        role_id = parcel.readString()
        gender = parcel.readString()
        email = parcel.readString()
        phone = parcel.readString()
        password = parcel.readString()
        status = parcel.readString()
        address = parcel.readString()
        date_of_bith = parcel.readString()
        image = parcel.readString()
        refer_id = parcel.readString()
        fb_id = parcel.readString()
        google_id = parcel.readString()
        otp = parcel.readString()
        module_access = parcel.readString()
        is_login = parcel.readString()
        last_login = parcel.readString()
        current_password = parcel.readString()
        language = parcel.readString()
        device_id = parcel.readString()
        device_type = parcel.readString()
        created = parcel.readString()
        modified = parcel.readString()
        user_id = parcel.readString()
        full_name = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(first_name)
        parcel.writeString(last_name)
        parcel.writeString(role_id)
        parcel.writeString(gender)
        parcel.writeString(email)
        parcel.writeString(phone)
        parcel.writeString(password)
        parcel.writeString(status)
        parcel.writeString(address)
        parcel.writeString(date_of_bith)
        parcel.writeString(image)
        parcel.writeString(refer_id)
        parcel.writeString(fb_id)
        parcel.writeString(google_id)
        parcel.writeString(otp)
        parcel.writeString(module_access)
        parcel.writeString(is_login)
        parcel.writeString(last_login)
        parcel.writeString(current_password)
        parcel.writeString(language)
        parcel.writeString(device_id)
        parcel.writeString(device_type)
        parcel.writeString(created)
        parcel.writeString(modified)
        parcel.writeString(user_id)
        parcel.writeString(full_name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserData> {
        override fun createFromParcel(parcel: Parcel): UserData {
            return UserData(parcel)
        }

        override fun newArray(size: Int): Array<UserData?> {
            return arrayOfNulls(size)
        }
    }


}