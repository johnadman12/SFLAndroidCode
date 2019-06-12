package stock.com.ui.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MarketTypeFilters extends BasePojo {
    @SerializedName("stocks")
    @Expose
    public Stocks stocks;
    @SerializedName("crypto")
    @Expose
    public Crypto crypto;

    public class Crypto {

        @SerializedName("name")
        @Expose
        public String name;

    }

    public class Sector {
        @SerializedName("sector")
        @Expose
        public String sector;

    }

    public class Stocks {

        @SerializedName("exchanges")
        @Expose
        public List<Exchange> exchanges = null;
        @SerializedName("sector")
        @Expose
        public List<Sector> sector = null;

    }

    public class Exchange {

        @SerializedName("name")
        @Expose
        public String name;

    }
}
