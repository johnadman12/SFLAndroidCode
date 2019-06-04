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
        @SerializedName("previousClose")
        @Expose
        public String previousClose;
        @SerializedName("marketopen")
        @Expose
        public String marketopen;
        @SerializedName("marketclose")
        @Expose
        public String marketclose;
        @SerializedName("sector")
        @Expose
        public String sector;
        @SerializedName("cryptocurrencyid")
        @Expose
        public String cryptocurrencyid;
        @SerializedName("cmc_rank")
        @Expose
        public String cmcRank;
        @SerializedName("marketcapital")
        @Expose
        public String marketcapital;
        @SerializedName("supply")
        @Expose
        public String supply;
        @SerializedName("country_id")
        @Expose
        public String countryId;
        @SerializedName("marketId")
        @Expose
        public String marketId;
        @SerializedName("marketname")
        @Expose
        public String marketname;
        @SerializedName("symbol")
        @Expose
        public String symbol;
        @SerializedName("image")
        @Expose
        public String image;
        @SerializedName("latestVolume")
        @Expose
        public String latestVolume;
        @SerializedName("latestPrice")
        @Expose
        public String latestPrice;
        @SerializedName("changePercent")
        @Expose
        public String changePercent;
        @SerializedName("companyName")
        @Expose
        public String companyName;


    }
}
