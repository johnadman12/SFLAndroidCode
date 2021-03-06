package stock.com.ui.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ContestDetail extends BasePojo {
    @SerializedName("contest")
    @Expose
    public List<Contest> contest = null;
    @SerializedName("rules")
    @Expose
    public List<Rule> rules = null;
    @SerializedName("scores")
    @Expose
    public List<Score> scores = null;

    public class Contest {

        @SerializedName("contestid")
        @Expose
        public int contestid;
        @SerializedName("contest_teamremaining")
        @Expose
        public int contest_teamremaining;
        @SerializedName("category_id")
        @Expose
        public int categoryId;
        @SerializedName("winning_amount")
        @Expose
        public String winningAmount;
        @SerializedName("confirm_winning")
        @Expose
        public String confirm_winning = "";
        @SerializedName("join_multiple")
        @Expose
        public String join_multiple = "";
        @SerializedName("contest_size")
        @Expose
        public int contestSize;
        @SerializedName("contest_type")
        @Expose
        public String contestType;
        @SerializedName("entry_fees")
        @Expose
        public String entryFees;
        @SerializedName("scheduleid")
        @Expose
        public int scheduleid;
        @SerializedName("schedule_name")
        @Expose
        public String scheduleName;
        @SerializedName("schedule_start")
        @Expose
        public String scheduleStart;
        @SerializedName("schedule_end")
        @Expose
        public String schedule_end;
        @SerializedName("catname")
        @Expose
        public String catname;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("exchangeid")
        @Expose
        public int exchangeid;
        @SerializedName("exchangename")
        @Expose
        public String exchangename;
        @SerializedName("exchangeimage")
        @Expose
        public String exchangeimage;
        @SerializedName("teams_joined")
        @Expose
        public int teamsJoined;
        @SerializedName("total_winners")
        @Expose
        public String totalwinners;
        @SerializedName("rank")
        @Expose
        public String rank;
        @SerializedName("mid")
        @Expose
        public int mid;
        @SerializedName("marketname")
        @Expose
        public String marketname;
        @SerializedName("price_break")
        @Expose
        public ArrayList<PriceBreak> priceBreak = null;


    }

    public class Rule {

        @SerializedName("description")
        @Expose
        public String description;

    }

    public class Score {
        @SerializedName("team_id")
        @Expose
        public int teamId;
        @SerializedName("teamNameCount")
        @Expose
        public String teamNameCount;
        @SerializedName("rank")
        @Expose
        public String rank;
        @SerializedName("totalchange_Per")
        @Expose
        public String totalchange_Per;
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
