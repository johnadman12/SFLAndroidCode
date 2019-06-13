package stock.com.ui.pojo

import android.os.Parcel
import android.os.Parcelable

class SignupDataPojo() : Parcelable {

    var id: String = ""
    var role_id: String = ""
    var device_type: String = ""
    var devic: String = ""
    var device_token: String = ""
    var phone_number: String = ""
    var name: String = ""
    var profile_image: String = ""
    var email: String = ""
    var password: String = ""
    var origional_password: String = ""
    var gender: String = ""
    var dob: String = ""
    var level_type: String = ""
    var invite_code: String = ""
    var otp_code: String = ""
    var registration_date: String = ""
    var login_time: String = ""
    var status: String = ""
    var is_verified: String = ""
    var created: String = ""
    var notification: String = ""
    var modified: String = ""
    var username: String = ""
    var country_id: String = ""

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        role_id = parcel.readString()
        device_type = parcel.readString()
        devic = parcel.readString()
        device_token = parcel.readString()
        phone_number = parcel.readString()
        name = parcel.readString()
        profile_image = parcel.readString()
        email = parcel.readString()
        password = parcel.readString()
        level_type = parcel.readString()
        origional_password = parcel.readString()
        gender = parcel.readString()
        dob = parcel.readString()
        invite_code = parcel.readString()
        otp_code = parcel.readString()
        registration_date = parcel.readString()
        login_time = parcel.readString()
        status = parcel.readString()
        is_verified = parcel.readString()
        created = parcel.readString()
        notification = parcel.readString()
        modified = parcel.readString()
        username = parcel.readString()
        country_id = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(role_id)
        parcel.writeString(device_type)
        parcel.writeString(devic)
        parcel.writeString(device_token)
        parcel.writeString(phone_number)
        parcel.writeString(name)
        parcel.writeString(profile_image)
        parcel.writeString(email)
        parcel.writeString(password)
        parcel.writeString(origional_password)
        parcel.writeString(gender)
        parcel.writeString(dob)
        parcel.writeString(invite_code)
        parcel.writeString(otp_code)
        parcel.writeString(level_type)
        parcel.writeString(registration_date)
        parcel.writeString(login_time)
        parcel.writeString(status)
        parcel.writeString(is_verified)
        parcel.writeString(created)
        parcel.writeString(notification)
        parcel.writeString(modified)
        parcel.writeString(username)
        parcel.writeString(country_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SignupDataPojo> {
        override fun createFromParcel(parcel: Parcel): SignupDataPojo {
            return SignupDataPojo(parcel)
        }

        override fun newArray(size: Int): Array<SignupDataPojo?> {
            return arrayOfNulls(size)
        }
    }
}