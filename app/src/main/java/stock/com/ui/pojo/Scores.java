package stock.com.ui.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Scores extends BasePojo {

    @SerializedName("scores")
    @Expose
    public ArrayList<Score> scores = null;
    public class Score {
        @SerializedName("team_id")
        @Expose
        public int teamId;
        @SerializedName("totalchange_Per")
        @Expose
        public String totalchange_Per;
        @SerializedName("contestId")
        @Expose
        public int contestId;
        @SerializedName("rank")
        @Expose
        public String rank;
        @SerializedName("userteamname")
        @Expose
        public String userteamname;
        @SerializedName("points")
        @Expose
        public String points;
        @SerializedName("userid")
        @Expose
        public int userid;
        @SerializedName("username")
        @Expose
        public String username;
        @SerializedName("image")
        @Expose
        public String image;
        @SerializedName("team_name")
        @Expose
        public String teamName;
        @SerializedName("teamNameCount")
        @Expose
        public String teamNameCount;
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
