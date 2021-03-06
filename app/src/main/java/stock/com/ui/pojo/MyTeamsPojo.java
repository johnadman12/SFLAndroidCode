package stock.com.ui.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MyTeamsPojo extends BasePojo {
    @SerializedName("myteams")
    @Expose
    public List<Myteamss> myteams = null;

    public class Myteamss {
        @SerializedName("totalchangePercentage")
        @Expose
        public String totalchangePercentage;
        @SerializedName("contestjoinId")
        @Expose
        public int contestjoinId;
        @SerializedName("user_id")
        @Expose
        public int userId;
        @SerializedName("userteamname")
        @Expose
        public String userteamname;
        @SerializedName("contest_id")
        @Expose
        public int contestId;
        @SerializedName("team_id")
        @Expose
        public int teamId;
        @SerializedName("created")
        @Expose
        public String created;
        @SerializedName("stocksid")
        @Expose
        public int stocksid;
        @SerializedName("exchangeid")
        @Expose
        public int exchangeid;
        @SerializedName("exchangename")
        @Expose
        public String exchangename;
        @SerializedName("exchangeimage")
        @Expose
        public String exchangeimage;
        @SerializedName("image")
        @Expose
        public String image;
        @SerializedName("marketid")
        @Expose
        public int mid;
        @SerializedName("marketname")
        @Expose
        public String marketname;
        @SerializedName("stock")
        @Expose
        public ArrayList<StockTeamPojo.Stock> stock = null;
        @SerializedName("crypto")
        @Expose
        public ArrayList<MarketList.Crypto> crypto = null;
        @SerializedName("currency")
        @Expose
        public ArrayList<CurrencyPojo.Currency> currencies = null;
    }
}
