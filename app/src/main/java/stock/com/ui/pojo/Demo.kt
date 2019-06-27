package stock.com.ui.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Demo : BasePojo() {

    @SerializedName("name")
    @Expose
    var name: String = ""
    @SerializedName("id")
    @Expose
    var id: String = ""
    @SerializedName("type")
    @Expose
    var type: String = ""


}