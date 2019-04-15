package stock.com.ui.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsDetailPojo extends BasePojo {
    @SerializedName("news")
    @Expose
    public News news;

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
        @SerializedName("channel_image")
        @Expose
        public String channel_image;
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
