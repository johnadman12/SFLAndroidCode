package stock.com.ui.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import stock.com.ui.pojo.UserPojo.User


class HomeSearchPojo : BasePojo() {

    var title: String = ""
    var users: ArrayList<Demo>? = null;

}