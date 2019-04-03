package stock.com.ui.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class FilterPojo : BasePojo() {
    @SerializedName("category")
    @Expose
    var category: List<Category>? = null
    @SerializedName("market")
    @Expose
    var market: List<Market>? = null
    @SerializedName("country")
    @Expose
    var country: List<Country>? = null

    inner class Market {

        @SerializedName("id")
        @Expose
        var id: Int? = null
        @SerializedName("name")
        @Expose
        var name: String? = null
        @SerializedName("image")
        @Expose
        var image: String? = null
        @SerializedName("image_url")
        @Expose
        var imageUrl: String? = null

    }

    inner class Country {

        @SerializedName("id")
        @Expose
        var id: Int? = null
        @SerializedName("name")
        @Expose
        var name: String? = null
        @SerializedName("alpha_2")
        @Expose
        var alpha2: String? = null
        @SerializedName("flag_url_1616")
        @Expose
        var flagUrl1616: String? = null
        @SerializedName("flag_url_6464")
        @Expose
        var flagUrl6464: String? = null

    }

    inner class Category {

        @SerializedName("id")
        @Expose
        var id: Int? = null
        @SerializedName("name")
        @Expose
        var name: String? = null

    }
}