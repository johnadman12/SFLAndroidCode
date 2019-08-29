package stock.com.ui.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Comments extends BasePojo {

    @SerializedName("commentlist")
    @Expose
    public ArrayList<Commentlist> commentlist = null;

    public class Commentlist {

        @SerializedName("statuscount")
        @Expose
        public int statuscount;
        @SerializedName("country_id")
        @Expose
        public String country_id;
        @SerializedName("country_name")
        @Expose
        public String country_name;
        @SerializedName("likesstatus")
        @Expose
        public String likesstatus;
        @SerializedName("stockCommentId")
        @Expose
        public int stockCommentId;
        @SerializedName("stocks_id")
        @Expose
        public int stocksId;
        @SerializedName("users_id")
        @Expose
        public int usersId;
        @SerializedName("comments")
        @Expose
        public String comments;
        @SerializedName("likescount")
        @Expose
        public int likescount;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("username")
        @Expose
        public String username;
        @SerializedName("profile_image")
        @Expose
        public String profileImage;

    }
}
