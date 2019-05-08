package stock.com.ui.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StockTeamPojo extends BasePojo implements Parcelable {
    @SerializedName("stock")
    @Expose
    public List<Stock> stock = null;

    protected StockTeamPojo(Parcel in) {
    }

    public static final Creator<StockTeamPojo> CREATOR = new Creator<StockTeamPojo>() {
        @Override
        public StockTeamPojo createFromParcel(Parcel in) {
            return new StockTeamPojo(in);
        }

        @Override
        public StockTeamPojo[] newArray(int size) {
            return new StockTeamPojo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    public static class Stock implements Parcelable {

        @SerializedName("stockid")
        @Expose
        public int stockid;
        @SerializedName("symbol")
        @Expose
        public String symbol;
        @SerializedName("image")
        @Expose
        public String image;
        @SerializedName("companyName")
        @Expose
        public String companyName;
        @SerializedName("previousClose")
        @Expose
        public String previousClose;
        @SerializedName("latestVolume")
        @Expose
        public String latestVolume;
        @SerializedName("marketopen")
        @Expose
        public String marketopen;
        @SerializedName("marketclose")
        @Expose
        public String marketclose;
        @SerializedName("latestPrice")
        @Expose
        public String latestPrice;
        @SerializedName("changePercent")
        @Expose
        public String changePercent;
        @SerializedName("stock_type")
        @Expose
        public String stock_type;
        @SerializedName("sector")
        @Expose
        public String sector;
        @SerializedName("addedStock")
        @Expose
        public String addedStock;
        public int addedToList=1;
        public boolean flagAddStockToList;

        public void setAddedToList(int addedToList) {
            this.addedToList = addedToList;
        }

        public int getAddedToList() {
            return addedToList;
        }

        public String getAddedStock() {
            return addedStock;
        }

        public void setAddedStock(String addedStock) {
            this.addedStock = addedStock;
        }

        protected Stock(Parcel in) {
            if (in.readByte() == 0) {
                stockid = 0;
            } else {
                stockid = in.readInt();
            }
            symbol = in.readString();
            image = in.readString();
            companyName = in.readString();
            sector = in.readString();
            addedStock = in.readString();
            addedToList = in.readInt();
            latestVolume = in.readString();
            marketopen = in.readString();
            marketclose = in.readString();
            latestPrice = in.readString();
            changePercent = in.readString();
            stock_type = in.readString();
        }

        public static final Creator<Stock> CREATOR = new Creator<Stock>() {
            @Override
            public Stock createFromParcel(Parcel in) {
                return new Stock(in);
            }

            @Override
            public Stock[] newArray(int size) {
                return new Stock[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            if (stockid == 0) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeInt(stockid);
            }
            parcel.writeString(symbol);
            parcel.writeString(image);
            parcel.writeString(companyName);
            parcel.writeString(sector);
            parcel.writeString(addedStock);
            parcel.writeInt(addedToList);
            parcel.writeString(latestVolume);
            parcel.writeString(marketopen);
            parcel.writeString(marketclose);
            parcel.writeString(latestPrice);
            parcel.writeString(changePercent);
            parcel.writeString(stock_type);
        }
    }
}
