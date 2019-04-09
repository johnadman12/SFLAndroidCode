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
    var country: ArrayList<CountryPojo>? = null
    @SerializedName("entry_fees")
    @Expose
    var entryFees: List<EntryFee>? = null

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


    inner class CountryPojo {
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

    inner class EntryFee {
        @SerializedName("min_value")
        @Expose
        var minValue: Int? = null
        @SerializedName("max_value")
        @Expose
        var maxValue: Int? = null

    }
}