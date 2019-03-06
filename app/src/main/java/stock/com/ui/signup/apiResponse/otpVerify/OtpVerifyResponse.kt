package stock.com.ui.signup.apiResponse.otpVerify

import android.os.Parcel
import android.os.Parcelable

class OtpVerifyResponse() : Parcelable {
    var response: Response? = null

    constructor(parcel: Parcel) : this() {

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OtpVerifyResponse> {
        override fun createFromParcel(parcel: Parcel): OtpVerifyResponse {
            return OtpVerifyResponse(parcel)
        }

        override fun newArray(size: Int): Array<OtpVerifyResponse?> {
            return arrayOfNulls(size)
        }
    }

}
