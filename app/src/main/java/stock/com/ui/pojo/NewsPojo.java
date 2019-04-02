package stock.com.ui.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsPojo extends BasePojo {
    @SerializedName("news")
    @Expose
    public List<News> news = null;

    public class News {
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("channel")
        @Expose
        public String channel;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("image")
        @Expose
        public String image;
        @SerializedName("newstime")
        @Expose
        public String newstime;
        @SerializedName("newspercentage")
        @Expose
        public String newspercentage;
        @SerializedName("timeleft")
        @Expose
        public String timeleft;
    }
}
