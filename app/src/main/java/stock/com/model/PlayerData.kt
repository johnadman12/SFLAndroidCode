package stock.com.model

import android.os.Parcel
import android.os.Parcelable

class PlayerData() : Parcelable {
    var id = ""
    var isCaptain = false
    var isViceCaptain = false

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        isCaptain = parcel.readByte() != 0.toByte()
        isViceCaptain = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeByte(if (isCaptain) 1 else 0)
        parcel.writeByte(if (isViceCaptain) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlayerData> {
        override fun createFromParcel(parcel: Parcel): PlayerData {
            return PlayerData(parcel)
        }

        override fun newArray(size: Int): Array<PlayerData?> {
            return arrayOfNulls(size)
        }
    }
}