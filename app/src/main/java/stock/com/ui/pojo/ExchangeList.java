package stock.com.ui.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExchangeList extends BasePojo {
    @SerializedName("exchange")
    @Expose
    public List<Exchange> exchange = null;

    public class Exchange {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("today_change")
        @Expose
        public String todayChange;

    }
}
