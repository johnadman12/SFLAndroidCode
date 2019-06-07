package stock.com.ui.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateContest extends BasePojo {

    @SerializedName("usercontest")
    @Expose
    public List<Usercontest> usercontest = null;

    public class Usercontest {

        @SerializedName("userContestId")
        @Expose
        public String userContestId;
        @SerializedName("market_id")
        @Expose
        public String marketId;
        @SerializedName("exchange_id")
        @Expose
        public String exchangeId;
        @SerializedName("contest_size_id")
        @Expose
        public String contestSizeId;
        @SerializedName("ucontest_name")
        @Expose
        public String ucontestName;
        @SerializedName("contests_winner")
        @Expose
        public String contestsWinner;
        @SerializedName("contest_type")
        @Expose
        public String contestType;
        @SerializedName("total_winning")
        @Expose
        public String totalWinning;
        @SerializedName("contest_size")
        @Expose
        public String contestSize;
        @SerializedName("entry_fees")
        @Expose
        public String entryFees;
        @SerializedName("join_multiple")
        @Expose
        public String joinMultiple;
        @SerializedName("schedule_start")
        @Expose
        public String scheduleStart;
        @SerializedName("schedule_end")
        @Expose
        public String scheduleEnd;

    }
}
