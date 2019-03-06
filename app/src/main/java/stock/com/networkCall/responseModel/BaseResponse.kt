package stock.com.networkCall.responseModel

import android.os.Parcel
import android.os.Parcelable

class BaseResponse() : Parcelable {
    var response: Response? = null

    constructor(parcel: Parcel) : this() {

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BaseResponse> {
        override fun createFromParcel(parcel: Parcel): BaseResponse {
            return BaseResponse(parcel)
        }

        override fun newArray(size: Int): Array<BaseResponse?> {
            return arrayOfNulls(size)
        }
    }

}
