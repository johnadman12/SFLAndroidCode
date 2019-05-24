package stock.com.ui.dashboard.Lobby;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import stock.com.R;

public class PriceBreakUpAdapter extends RecyclerView.Adapter<PriceBreakUpAdapter.FeatureListHolder> {
    private Context mContext;

    public PriceBreakUpAdapter(Context mContext) {
        this.mContext = mContext;
       // this.onItemCheckListener = onItemCheckListener;
    }

    @NonNull
    @Override
    public FeatureListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_pricebreakup_perecntage, parent, false);
        return new FeatureListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeatureListHolder holder, int position) {





    }

    @Override
    public int getItemCount() {
        return 3;
    }
     class FeatureListHolder  extends RecyclerView.ViewHolder{



         public FeatureListHolder(@NonNull View itemView) {
             super(itemView);
         }
     }

}
