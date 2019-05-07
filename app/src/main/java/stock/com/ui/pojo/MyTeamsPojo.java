package stock.com.ui.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MyTeamsPojo extends BasePojo {
    @SerializedName("myteam")
    @Expose
    public List<Myteam> myteam = null;

    public class Myteam {

        @SerializedName("totalchangePercentage")
        @Expose
        public int totalchangePercentage;
        @SerializedName("contestjoinId")
        @Expose
        public int contestjoinId;
        @SerializedName("user_id")
        @Expose
        public int userId;
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
        @SerializedName("stock")
        @Expose
        public ArrayList<StockTeamPojo.Stock> stock = null;

    }
}
