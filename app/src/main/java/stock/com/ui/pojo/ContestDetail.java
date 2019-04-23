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
        @SerializedName("category_id")
        @Expose
        public int categoryId;
        @SerializedName("winning_amount")
        @Expose
        public String winningAmount;
        @SerializedName("contest_size")
        @Expose
        public Integer contestSize;
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

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("image")
        @Expose
        public String image;
        @SerializedName("username")
        @Expose
        public String username;
        @SerializedName("rank")
        @Expose
        public String rank;
        @SerializedName("rankPercentage")
        @Expose
        public String rankPercentage;
        @SerializedName("price")
        @Expose
        public String price;

    }
}
