package stock.com.ui.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MarketList extends BasePojo {
    public String myteam = "";
    @SerializedName("crypto")
    @Expose
    public ArrayList<Crypto> crypto = null;

    public static class Crypto implements Parcelable {

        @SerializedName("cryptocurrencyid")
        @Expose
        public int cryptocurrencyid;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("symbol")
        @Expose
        public String symbol;
        @SerializedName("cmc_rank")
        @Expose
        public int cmcRank;
        @SerializedName("logo")
        @Expose
        public String logo;
        @SerializedName("marketcapital")
        @Expose
        public String marketcapital;
        @SerializedName("supply")
        @Expose
        public String supply;
        @SerializedName("latestPrice")
        @Expose
        public String latestPrice;
        @SerializedName("latestVolume")
        @Expose
        public String latestVolume;
        @SerializedName("changeper")
        @Expose
        public String changeper;
        @SerializedName("image")
        @Expose
        public String image;
        @SerializedName("crypto_type")
        @Expose
        public String cryptoType;
        public int addedToList = 1;

        protected Crypto(Parcel in) {
            cryptocurrencyid = in.readInt();
            name = in.readString();
            symbol = in.readString();
            cmcRank = in.readInt();
            logo = in.readString();
            marketcapital = in.readString();
            supply = in.readString();
            latestPrice = in.readString();
            latestVolume = in.readString();
            changeper = in.readString();
            image = in.readString();
            cryptoType = in.readString();
            addedToList = in.readInt();
        }

        public static final Creator<Crypto> CREATOR = new Creator<Crypto>() {
            @Override
            public Crypto createFromParcel(Parcel in) {
                return new Crypto(in);
            }

            @Override
            public Crypto[] newArray(int size) {
                return new Crypto[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(cryptocurrencyid);
            parcel.writeString(name);
            parcel.writeString(symbol);
            parcel.writeInt(cmcRank);
            parcel.writeString(logo);
            parcel.writeString(marketcapital);
            parcel.writeString(supply);
            parcel.writeString(latestPrice);
            parcel.writeString(latestVolume);
            parcel.writeString(changeper);
            parcel.writeString(image);
            parcel.writeString(cryptoType);
            parcel.writeInt(addedToList);
        }
    }

}
