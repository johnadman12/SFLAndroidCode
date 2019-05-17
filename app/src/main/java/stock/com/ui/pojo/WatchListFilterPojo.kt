package stock.com.ui.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WatchListFilterPojo : BasePojo() {


    @SerializedName("market")
    @Expose
    var marketList: ArrayList<market>? = null;
    @SerializedName("asset")
    @Expose
    var assetList: ArrayList<asset>? = null;
    @SerializedName("country")
    @Expose
    var countrytList: ArrayList<country>? = null;
    @SerializedName("sector")
    @Expose
    var sectorList: ArrayList<sector>? = null;



    inner class market {
        var name: String = ""
    }

    inner class asset {
        var name: String = ""
        var id: String = ""
    }
    inner class sector {
        var sector: String = ""
    }

    inner class country {
        var id: String = "";
        var name: String = "";
        var alpha_2: String = "";
        var flag_url_1616: String = "";
        var flag_url_6464: String = "";
    }
}