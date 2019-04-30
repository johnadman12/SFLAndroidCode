package stock.com.ui.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LobbyContestPojo extends BasePojo {
    @SerializedName("contest")
    @Expose
    public ArrayList<Contest> contest = null;

    public class Contest implements Serializable {

        @SerializedName("schedule_id")
        @Expose
        public Integer scheduleId;
        @SerializedName("contest_id")
        @Expose
        public Integer contestId;
        @SerializedName("exchangeid")
        @Expose
        public String exchangeid;
        @SerializedName("exchangename")
        @Expose
        public String exchangename;
        @SerializedName("exchangeimage")
        @Expose
        public String exchangeimage;
        @SerializedName("scheduleid")
        @Expose
        public Integer scheduleid;
        @SerializedName("schedule_name")
        @Expose
        public String scheduleName;
        @SerializedName("schedule_start")
        @Expose
        public String scheduleStart;
        @SerializedName("winning_amount")
        @Expose
        public String winningAmount;
        @SerializedName("contestid")
        @Expose
        public Integer contestid;
        @SerializedName("category_id")
        @Expose
        public Integer categoryId;
        @SerializedName("contest_size")
        @Expose
        public Integer contestSize;
        @SerializedName("contest_type")
        @Expose
        public String contestType;
        @SerializedName("entry_fees")
        @Expose
        public String entryFees;
        @SerializedName("fees")
        @Expose
        public int fees;
        @SerializedName("catname")
        @Expose
        public String catname;
        @SerializedName("rank")
        @Expose
        public String rank;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("teams_joined")
        @Expose
        public Integer teamsJoined;

        public int calculatePosition;
        public int date;
        @SerializedName("price_break")
        @Expose
        public ArrayList<PriceBreak> priceBreak = null;

        @SerializedName("total_winners")
        @Expose
        public String totalWinners;

        public void setCalculatePosition(int calculatePosition) {
            this.calculatePosition = calculatePosition;
        }

        public int getCalculatePosition() {
            return calculatePosition;
        }

        public void setDate(int date) {
            this.date = date;
        }

        public int getDate() {
            return date;
        }

        protected Contest(Parcel in) {
            if (in.readByte() == 0) {
                scheduleId = null;
            } else {
                scheduleId = in.readInt();
            }
            if (in.readByte() == 0) {
                contestId = null;
            } else {
                contestId = in.readInt();
            }
            if (in.readByte() == 0) {
                exchangeid = null;
            } else {
                exchangeid = in.readString();
            }
            exchangename = in.readString();
            exchangeimage = in.readString();
            if (in.readByte() == 0) {
                scheduleid = null;
            } else {
                scheduleid = in.readInt();
            }
            scheduleName = in.readString();
            scheduleStart = in.readString();
            winningAmount = in.readString();
            if (in.readByte() == 0) {
                contestid = null;
            } else {
                contestid = in.readInt();
            }
            if (in.readByte() == 0) {
                categoryId = null;
            } else {
                categoryId = in.readInt();
            }
            if (in.readByte() == 0) {
                contestSize = null;
            } else {
                contestSize = in.readInt();
            }
            contestType = in.readString();
            entryFees = in.readString();
            catname = in.readString();
            description = in.readString();
            if (in.readByte() == 0) {
                teamsJoined = null;
            } else {
                teamsJoined = in.readInt();
            }
            if (in.readByte() == 0) {
                totalWinners ="";
            } else {
                totalWinners = in.readString();
            }
        }
    }


}
