package stock.com.ui.signup.apiResponse.signup

import android.os.Parcel
import android.os.Parcelable

class SignUpResponse() : Parcelable {
    var response: Response? = null

    constructor(parcel: Parcel) : this() {

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SignUpResponse> {
        override fun createFromParcel(parcel: Parcel): SignUpResponse {
            return SignUpResponse(parcel)
        }

        override fun newArray(size: Int): Array<SignUpResponse?> {
            return arrayOfNulls(size)
        }
    }

}
