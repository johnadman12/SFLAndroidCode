package stock.com.ui.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CityfalconNewsPojo extends BasePojo {

    @SerializedName("stories")
    @Expose
    public ArrayList<Story> stories = null;

    public  class Story {

        @SerializedName("uuid")
        @Expose
        public String uuid;
        @SerializedName("publishTime")
        @Expose
        public String publishTime;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("assetTags")
        @Expose
        public ArrayList<String> assetTags = null;
        @SerializedName("searchTags")
        @Expose
        public ArrayList<String> searchTags = null;
        @SerializedName("category")
        @Expose
        public String category;
        @SerializedName("url")
        @Expose
        public String url;
        @SerializedName("imageUrls")
        @Expose
        public List<String> imageUrls = null;
        @SerializedName("lang")
        @Expose
        public String lang;
        @SerializedName("cityfalconScore")
        @Expose
        public Integer cityfalconScore;
        @SerializedName("additionalData")
        @Expose
        public AdditionalData additionalData;
        @SerializedName("source")
        @Expose
        public Source source;
        @SerializedName("duplicatesCount")
        @Expose
        public Integer duplicatesCount;
        @SerializedName("paywall")
        @Expose
        public Boolean paywall;
        @SerializedName("registrationRequired")
        @Expose
        public Boolean registrationRequired;
    }

    public  class ImageUrls {

        @SerializedName("thumb")
        @Expose
        public String thumb;
        @SerializedName("small")
        @Expose
        public String small;
        @SerializedName("medium")
        @Expose
        public String medium;
        @SerializedName("large")
        @Expose
        public String large;
    }

    public  class Source {

        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("brandName")
        @Expose
        public String brandName;
        @SerializedName("imageUrl")
        @Expose
        public String imageUrl;
        @SerializedName("imageUrls")
        @Expose
        public ImageUrls imageUrls;


    }

    public class AdditionalData {


    }

}
