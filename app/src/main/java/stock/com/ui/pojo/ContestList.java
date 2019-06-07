package stock.com.ui.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ContestList extends BasePojo {

    @SerializedName("market")
    @Expose
    public ArrayList<Market> market = null;

    public class Exchangelist {

        @SerializedName("exchangesId")
        @Expose
        public String exchangesId;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("sector")
        @Expose
        public String sector;

    }

    public class Market {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("slug")
        @Expose
        public String slug;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("created")
        @Expose
        public String created;
        @SerializedName("modified")
        @Expose
        public String modified;
        @SerializedName("exchangelist")
        @Expose
        public ArrayList<Exchangelist> exchangelist = null;

    }
}
