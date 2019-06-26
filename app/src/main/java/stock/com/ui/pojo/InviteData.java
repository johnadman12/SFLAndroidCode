package stock.com.ui.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class InviteData extends BasePojo {

    @SerializedName("user_data")
    @Expose
    public ArrayList<UserDatum> userData = null;
    @SerializedName("friend_data")
    @Expose
    public ArrayList<FriendDatum> friendData = null;

    public class FriendDatum {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("username")
        @Expose
        public String username;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("profile_image")
        @Expose
        public String profileImage;
        @SerializedName("contest_invite")
        @Expose
        public String contestInvite;
        @SerializedName("level_type")
        @Expose
        public String levelType;

    }

    public class UserDatum {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("username")
        @Expose
        public String username;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("profile_image")
        @Expose
        public String profileImage;
        @SerializedName("contest_invite")
        @Expose
        public String contestInvite;
        @SerializedName("level_type")
        @Expose
        public String levelType;

    }
}
