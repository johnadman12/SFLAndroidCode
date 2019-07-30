package stock.com.ui.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WatchlistPojo extends BasePojo {
    @SerializedName("stock")
    @Expose
    public ArrayList<WatchStock> stock = null;

    public class WatchStock implements Serializable {

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

        @SerializedName("crypto_logo")
        @Expose
        public String crypto_logo;

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
        @SerializedName("currencyid")
        @Expose
        public String currencyid;
        @SerializedName("ask")
        @Expose
        public String ask;
        @SerializedName("bid")
        @Expose
        public String bid;

        @SerializedName("currency_netchange")
        @Expose
        public String currency_netchange;
        @SerializedName("currency_perchange")
        @Expose
        public String currency_perchange;
        @SerializedName("currency_volume")
        @Expose
        public String currency_volume;
        @SerializedName("crypto_market_id")
        @Expose
        public String crypto_market_id;
        @SerializedName("currency_market_id")
        @Expose
        public String currency_market_id;
        @SerializedName("currency_symbol")
        @Expose
        public String currency_symbol;

        @SerializedName("currency_firstflag")
        @Expose
        public String currency_firstflag;
         @SerializedName("currency_secondflag")
         @Expose
         public String currency_secondflag;

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
