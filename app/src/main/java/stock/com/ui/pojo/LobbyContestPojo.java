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
        public int scheduleId; 
        @SerializedName("contest_teamremaining")
        @Expose
        public int contest_teamremaining;
        @SerializedName("contest_id")
        @Expose
        public int contestId;
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
        public int scheduleid;
        @SerializedName("confirm_winning")
        @Expose
        public String confirm_winning = "";
        @SerializedName("join_multiple")
        @Expose
        public String join_multiple= "";
        @SerializedName("schedule_name")
        @Expose
        public String scheduleName;
        @SerializedName("schedule_start")
        @Expose
        public String scheduleStart;
        @SerializedName("schedule_end")
        @Expose
        public String scheduleEnd;
        @SerializedName("winning_amount")
        @Expose
        public String winningAmount;
        @SerializedName("mid")
        @Expose
        public int  mid=0;
        @SerializedName("marketname")
        @Expose
        public String  marketname;

        public String winningAmount_temp="";

        public String getWinningAmount_temp() {
            return winningAmount_temp;
        }

        public void setWinningAmount_temp(String winningAmount_temp) {
            this.winningAmount_temp = winningAmount_temp;
        }

        @SerializedName("contestid")
        @Expose
        public int contestid;
        @SerializedName("category_id")
        @Expose
        public int categoryId;
        @SerializedName("contest_size")
        @Expose
        public int contestSize;
        @SerializedName("contest_type")
        @Expose
        public String contestType;
        @SerializedName("entry_fees")
        @Expose
        public String entryFees;
        @SerializedName("fees")
        @Expose
        public String fees;
        @SerializedName("win_amount")
        @Expose
        public String win_amount;
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
        public int teamsJoined;

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
                scheduleId = 0;
            } else {
                scheduleId = in.readInt();
            }
            if (in.readByte() == 0) {
                contestId = 0;
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
                scheduleid = 0;
            } else {
                scheduleid = in.readInt();
            }
            scheduleName = in.readString();
            scheduleStart = in.readString();
            winningAmount = in.readString();
            if (in.readByte() == 0) {
                contestid = 0;
            } else {
                contestid = in.readInt();
            }
            if (in.readByte() == 0) {
                categoryId = 0;
            } else {
                categoryId = in.readInt();
            }
            if (in.readByte() == 0) {
                contestSize = 0;
            } else {
                contestSize = in.readInt();
            }
            contestType = in.readString();
            entryFees = in.readString();
            catname = in.readString();
            description = in.readString();
            if (in.readByte() == 0) {
                teamsJoined = 0;
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
