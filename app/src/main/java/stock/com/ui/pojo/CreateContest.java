package stock.com.ui.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateContest extends BasePojo {

    @SerializedName("usercontest")
    @Expose
    public List<Usercontest> usercontest = null;

    public class Usercontest {
        @SerializedName("schedule_end")
        @Expose
        public String scheduleEnd;
        @SerializedName("exchangeid")
        @Expose
        public String exchangeid;
        @SerializedName("catname")
        @Expose
        public String catname;
        @SerializedName("category_id")
        @Expose
        public String categoryId;
        @SerializedName("winning_amount")
        @Expose
        public String winningAmount;
        @SerializedName("mid")
        @Expose
        public String mid;
        @SerializedName("price_break")
        @Expose
        public List<PriceBreak> priceBreak = null;
        @SerializedName("total_winners")
        @Expose
        public String totalWinners;
        @SerializedName("marketname")
        @Expose
        public String marketname;
        @SerializedName("invited_code")
        @Expose
        public String invitedCode;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("entry_fees")
        @Expose
        public String entryFees;
        @SerializedName("contestid")
        @Expose
        public String contestid;
        @SerializedName("scheduleid")
        @Expose
        public String scheduleid;
        @SerializedName("contest_teamremaining")
        @Expose
        public String contestTeamremaining;
        @SerializedName("join_multiple")
        @Expose
        public String joinMultiple;
        @SerializedName("exchangeimage")
        @Expose
        public String exchangeimage;
        @SerializedName("teams_joined")
        @Expose
        public int teamsJoined;
        @SerializedName("exchangename")
        @Expose
        public String exchangename;
        @SerializedName("schedule_name")
        @Expose
        public String scheduleName;
        @SerializedName("contest_type")
        @Expose
        public String contestType;
        @SerializedName("schedule_start")
        @Expose
        public String scheduleStart;
        @SerializedName("contest_size")
        @Expose
        public int contestSize;


    }
    public class PriceBreak {

        @SerializedName("start_num")
        @Expose
        public Integer startNum;
        @SerializedName("contest_id")
        @Expose
        public Integer contestId;
        @SerializedName("price_each")
        @Expose
        public String priceEach;
        @SerializedName("end_num")
        @Expose
        public Integer endNum;

    }
}
