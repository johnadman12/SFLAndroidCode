package stock.com.ui.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CityfalconNewsPojo extends BasePojo implements Parcelable {

    @SerializedName("stories")
    @Expose
    public ArrayList<Story> stories = null;

    protected CityfalconNewsPojo(Parcel in) {
    }

    public static final Creator<CityfalconNewsPojo> CREATOR = new Creator<CityfalconNewsPojo>() {
        @Override
        public CityfalconNewsPojo createFromParcel(Parcel in) {
            return new CityfalconNewsPojo(in);
        }

        @Override
        public CityfalconNewsPojo[] newArray(int size) {
            return new CityfalconNewsPojo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    public static class ImageUrls implements Parcelable{

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

        protected ImageUrls(Parcel in) {
            thumb = in.readString();
            small = in.readString();
            medium = in.readString();
            large = in.readString();
        }

        public static final Creator<ImageUrls> CREATOR = new Creator<ImageUrls>() {
            @Override
            public ImageUrls createFromParcel(Parcel in) {
                return new ImageUrls(in);
            }

            @Override
            public ImageUrls[] newArray(int size) {
                return new ImageUrls[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(thumb);
            parcel.writeString(small);
            parcel.writeString(medium);
            parcel.writeString(large);
        }
    }

    public class Source implements Parcelable {

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

        public Source(Parcel in) {
            name = in.readString();
            brandName = in.readString();
            imageUrl = in.readString();
            imageUrls = in.readParcelable(ImageUrls.class.getClassLoader());
        }

        public final Creator<Source> CREATOR = new Creator<Source>() {
            @Override
            public Source createFromParcel(Parcel in) {
                return new Source(in);
            }

            @Override
            public Source[] newArray(int size) {
                return new Source[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(name);
            parcel.writeString(brandName);
            parcel.writeString(imageUrl);
            parcel.writeParcelable(imageUrls, i);
        }
    }
    public static class Story implements Parcelable {

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

        protected Story(Parcel in) {
            uuid = in.readString();
            publishTime = in.readString();
            title = in.readString();
            description = in.readString();
            assetTags = in.createStringArrayList();
            searchTags = in.createStringArrayList();
            category = in.readString();
            url = in.readString();
            imageUrls = in.createStringArrayList();
            lang = in.readString();
            if (in.readByte() == 0) {
                cityfalconScore = null;
            } else {
                cityfalconScore = in.readInt();
            }
            if (in.readByte() == 0) {
                duplicatesCount = null;
            } else {
                duplicatesCount = in.readInt();
            }
            byte tmpPaywall = in.readByte();
            paywall = tmpPaywall == 0 ? null : tmpPaywall == 1;
            byte tmpRegistrationRequired = in.readByte();
            registrationRequired = tmpRegistrationRequired == 0 ? null : tmpRegistrationRequired == 1;
        }

        public static final Creator<Story> CREATOR = new Creator<Story>() {
            @Override
            public Story createFromParcel(Parcel in) {
                return new Story(in);
            }

            @Override
            public Story[] newArray(int size) {
                return new Story[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(uuid);
            parcel.writeString(publishTime);
            parcel.writeString(title);
            parcel.writeString(description);
            parcel.writeStringList(assetTags);
            parcel.writeStringList(searchTags);
            parcel.writeString(category);
            parcel.writeString(url);
            parcel.writeStringList(imageUrls);
            parcel.writeString(lang);
            if (cityfalconScore == null) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeInt(cityfalconScore);
            }
            if (duplicatesCount == null) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeInt(duplicatesCount);
            }
            parcel.writeByte((byte) (paywall == null ? 0 : paywall ? 1 : 2));
            parcel.writeByte((byte) (registrationRequired == null ? 0 : registrationRequired ? 1 : 2));
        }
    }
    public class AdditionalData {


    }
}
