package stock.com.ui.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CityfalconNewsPojo extends BasePojo implements Serializable {

    @SerializedName("stories")
    @Expose
    public ArrayList<Story> stories = null;

    public ArrayList<Story> getStories() {
        return stories;
    }

    public void setStories(ArrayList<Story> stories) {
        this.stories = stories;
    }

    public  class Story implements Serializable{

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public ArrayList<String> getAssetTags() {
            return assetTags;
        }

        public void setAssetTags(ArrayList<String> assetTags) {
            this.assetTags = assetTags;
        }

        public ArrayList<String> getSearchTags() {
            return searchTags;
        }

        public void setSearchTags(ArrayList<String> searchTags) {
            this.searchTags = searchTags;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<String> getImageUrls() {
            return imageUrls;
        }

        public void setImageUrls(List<String> imageUrls) {
            this.imageUrls = imageUrls;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public int getCityfalconScore() {
            return cityfalconScore;
        }

        public void setCityfalconScore(int cityfalconScore) {
            this.cityfalconScore = cityfalconScore;
        }

        public AdditionalData getAdditionalData() {
            return additionalData;
        }

        public void setAdditionalData(AdditionalData additionalData) {
            this.additionalData = additionalData;
        }

        public Source getSource() {
            return source;
        }

        public void setSource(Source source) {
            this.source = source;
        }

        public int getDuplicatesCount() {
            return duplicatesCount;
        }

        public void setDuplicatesCount(int duplicatesCount) {
            this.duplicatesCount = duplicatesCount;
        }

        public Boolean getPaywall() {
            return paywall;
        }

        public void setPaywall(Boolean paywall) {
            this.paywall = paywall;
        }

        public Boolean getRegistrationRequired() {
            return registrationRequired;
        }

        public void setRegistrationRequired(Boolean registrationRequired) {
            this.registrationRequired = registrationRequired;
        }

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
        public int cityfalconScore;
        @SerializedName("additionalData")
        @Expose
        public AdditionalData additionalData;
        @SerializedName("source")
        @Expose
        public Source source;
        @SerializedName("duplicatesCount")
        @Expose
        public int duplicatesCount;
        @SerializedName("paywall")
        @Expose
        public Boolean paywall;
        @SerializedName("registrationRequired")
        @Expose
        public Boolean registrationRequired;






    }
    public class AdditionalData implements Serializable{


    }
    public class ImageUrls implements Serializable{

        @SerializedName("thumb")
        @Expose
        public String thumb;
        @SerializedName("small")
        @Expose
        public String small;
        @SerializedName("medium")
        @Expose
        public String medium;

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        @SerializedName("large")
        @Expose
        public String large;

    }


    public  class Source  implements Serializable{
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public ImageUrls getImageUrls() {
            return imageUrls;
        }

        public void setImageUrls(ImageUrls imageUrls) {
            this.imageUrls = imageUrls;
        }

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
}
