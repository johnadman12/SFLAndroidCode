package stock.com.networkCall.responseModel

import android.os.Parcel
import android.os.Parcelable

class Response() : Parcelable {

    var status: Boolean? = null
    var message: String? = null
    var is_deactivate: String? = null

    constructor(parcel: Parcel) : this() {
        status = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        message = parcel.readString()
        is_deactivate = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(status)
        parcel.writeString(message)
        parcel.writeString(is_deactivate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Response> {
        override fun createFromParcel(parcel: Parcel): Response {
            return Response(parcel)
        }

        override fun newArray(size: Int): Array<Response?> {
            return arrayOfNulls(size)
        }
    }

}
