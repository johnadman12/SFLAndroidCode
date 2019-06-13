package stock.com.ui.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateContest extends BasePojo {

    @SerializedName("usercontest")
    @Expose
    public List<Usercontest> usercontest = null;

    public class Usercontest {
        @SerializedName("entry_fees")
        @Expose
        public String entryFees;
        @SerializedName("exchange_id")
        @Expose
        public String exchangeId;
        @SerializedName("userContestId")
        @Expose
        public String userContestId;
        @SerializedName("slug")
        @Expose
        public String slug;
        @SerializedName("total_winning")
        @Expose
        public String totalWinning;
        @SerializedName("market_id")
        @Expose
        public String marketId;
        @SerializedName("exchangename")
        @Expose
        public String exchangename;
        @SerializedName("contest_size")
        @Expose
        public int contestSize;
        @SerializedName("join_multiple")
        @Expose
        public String joinMultiple;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("exchangeimage")
        @Expose
        public String exchangeimage;
        @SerializedName("contest_type")
        @Expose
        public String contestType;
        @SerializedName("contests_winner")
        @Expose
        public String contestsWinner;
        @SerializedName("schedule_end")
        @Expose
        public String scheduleEnd;
        @SerializedName("contest_size_id")
        @Expose
        public String contestSizeId;
        @SerializedName("ucontest_name")
        @Expose
        public String ucontestName;
        @SerializedName("schedule_start")
        @Expose
        public String scheduleStart;
        @SerializedName("team_joined")
        @Expose
        public int teamJoined;
        @SerializedName("invited_code")
        @Expose
        public String invited_code;

    }
}
