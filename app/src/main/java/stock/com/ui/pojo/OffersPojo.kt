package stock.com.ui.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class OffersPojo : BasePojo() {

    @SerializedName("offers")
    @Expose
    var offerList:ArrayList<offers>?=null;


    inner class offers {
        @SerializedName("id")
        @Expose
        var id: String = "";

        @SerializedName("title")
        @Expose
        var title: String = "";

        @SerializedName("description")
        @Expose
        var description: String = "";

        @SerializedName("image")
        @Expose
        var image: String = "";

        @SerializedName("expire_date")
        @Expose
        var expire_date: String = "";

        @SerializedName("created")
        @Expose
        var created: String = "";





    }
}