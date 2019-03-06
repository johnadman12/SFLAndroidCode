package stock.com.ui.signup.apiRequest

import android.os.Parcel
import android.os.Parcelable

class VerifyOtpRequest() : Parcelable {
    var otp: String = ""
    var device_id: String = ""
    var device_type: String = ""
    var language: String = ""
    var user_id: String = ""

    constructor(parcel: Parcel) : this() {
        otp = parcel.readString()
        device_id = parcel.readString()
        device_type = parcel.readString()
        language = parcel.readString()
        user_id = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(otp)
        parcel.writeString(device_id)
        parcel.writeString(device_type)
        parcel.writeString(language)
        parcel.writeString(user_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VerifyOtpRequest> {
        override fun createFromParcel(parcel: Parcel): VerifyOtpRequest {
            return VerifyOtpRequest(parcel)
        }

        override fun newArray(size: Int): Array<VerifyOtpRequest?> {
            return arrayOfNulls(size)
        }
    }


}