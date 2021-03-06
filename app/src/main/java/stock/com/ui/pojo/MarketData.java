package stock.com.ui.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public  class MarketData extends BasePojo {

    @SerializedName("stock")
    @Expose
    public ArrayList<StockTeamPojo.Stock> stock = null;
    @SerializedName("crypto")
    @Expose
    public ArrayList<MarketList.Crypto> crypto = null;

    public List<StockTeamPojo.Stock> getStock() {
        return stock;
    }

    public void setStock(ArrayList<StockTeamPojo.Stock> stock) {
        this.stock = stock;
    }

    public List<MarketList.Crypto> getCrypto() {
        return crypto;
    }

    public void setCrypto(ArrayList<MarketList.Crypto> crypto) {
        this.crypto = crypto;
    }
}
