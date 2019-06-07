package stock.com.ui.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WinningList extends BasePojo {

    @SerializedName("pricebreaklist")
    @Expose
    public ArrayList<Pricebreaklist> pricebreaklist = null;

    public static class Pricebreaklist implements Parcelable {

        @SerializedName("winner")
        @Expose
        public String winner;
        @SerializedName("usercontestSizeId")
        @Expose
        public String usercontestSizeId;
        @SerializedName("Winners")
        @Expose
        public ArrayList<Winner> winners = null;

        protected Pricebreaklist(Parcel in) {
            winner = in.readString();
            usercontestSizeId = in.readString();
            winners = in.createTypedArrayList(Winner.CREATOR);
        }

        public static final Creator<Pricebreaklist> CREATOR = new Creator<Pricebreaklist>() {
            @Override
            public Pricebreaklist createFromParcel(Parcel in) {
                return new Pricebreaklist(in);
            }

            @Override
            public Pricebreaklist[] newArray(int size) {
                return new Pricebreaklist[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(winner);
            parcel.writeString(usercontestSizeId);
            parcel.writeTypedList(winners);
        }
    }

    public static class Winner implements Parcelable {

        @SerializedName("winnerRank")
        @Expose
        public String winnerRank;
        @SerializedName("winnerPer")
        @Expose
        public String winnerPer;
        @SerializedName("price")
        @Expose
        public String price;

        protected Winner(Parcel in) {
            winnerRank = in.readString();
            winnerPer = in.readString();
            price = in.readString();
        }

        public static final Creator<Winner> CREATOR = new Creator<Winner>() {
            @Override
            public Winner createFromParcel(Parcel in) {
                return new Winner(in);
            }

            @Override
            public Winner[] newArray(int size) {
                return new Winner[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(winnerRank);
            parcel.writeString(winnerPer);
            parcel.writeString(price);
        }
    }
}
