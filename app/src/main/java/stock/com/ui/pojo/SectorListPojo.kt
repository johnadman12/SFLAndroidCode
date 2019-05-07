package stock.com.ui.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SectorListPojo: BasePojo(){
    @SerializedName("sectorlist")
    @Expose
    var sectorList: ArrayList<Sector>? = null

    inner class Sector {
        @SerializedName("sector")
        @Expose
        var sector: String? = null

    }

}