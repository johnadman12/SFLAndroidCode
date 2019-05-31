package stock.com.ui.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WatchlistPojo extends BasePojo {
    @SerializedName("stock")
    @Expose
    public ArrayList<WatchStock> stock = null;

    public class WatchStock {
        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("stockid")
        @Expose
        public String stockid;
        @SerializedName("symbol")
        @Expose
        public String symbol;
        @SerializedName("image")
        @Expose
        public String image;
        @SerializedName("companyName")
        @Expose
        public String companyName;
        @SerializedName("previousClose")
        @Expose
        public String previousClose;
        @SerializedName("latestVolume")
        @Expose
        public String latestVolume;
        @SerializedName("marketopen")
        @Expose
        public String marketopen;
        @SerializedName("marketclose")
        @Expose
        public String marketclose;
        @SerializedName("latestPrice")
        @Expose
        public String latestPrice;
        @SerializedName("changePercent")
        @Expose
        public String changePercent;
        @SerializedName("sector")
        @Expose
        public String sector;
        @SerializedName("cryptocurrencyid")
        @Expose
        public String cryptocurrencyid;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("cmc_rank")
        @Expose
        public String cmcRank;
        @SerializedName("logo")
        @Expose
        public String logo;
        @SerializedName("marketcapital")
        @Expose
        public String marketcapital;
        @SerializedName("supply")
        @Expose
        public String supply;
        @SerializedName("changeper")
        @Expose
        public String changeper;
        @SerializedName("country_id")
        @Expose
        public String countryId;
        @SerializedName("market_categories_id")
        @Expose
        public String marketCategoriesId;
        @SerializedName("marketId")
        @Expose
        public String marketId;
        @SerializedName("marketname")
        @Expose
        public String marketname;
        @SerializedName("markettype")
        @Expose
        public String markettype;

    }
}
