package stock.com.ui.dashboard.Lobby;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import stock.com.R;
import stock.com.ui.pojo.WinningList;

import java.util.ArrayList;

public class PriceBreakUpAdapter extends RecyclerView.Adapter<PriceBreakUpAdapter.FeatureListHolder> {
    private Context mContext;
    private ArrayList<WinningList.Winner> list;

    public PriceBreakUpAdapter(Context mContext, ArrayList<WinningList.Winner> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public FeatureListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_pricebreakup_perecntage, parent, false);
        return new FeatureListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeatureListHolder holder, int position) {
        holder.teamname.setText(list.get(position).winnerRank);
        holder.txt_percentage.setText(list.get(position).winnerPer);
        holder.txt_rank.setText(list.get(position).price);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class FeatureListHolder extends RecyclerView.ViewHolder {
        TextView teamname, txt_percentage, txt_rank;


        public FeatureListHolder(@NonNull View itemView) {
            super(itemView);
            txt_rank = itemView.findViewById(R.id.txt_rank);
            txt_percentage = itemView.findViewById(R.id.txt_percentage);
            teamname = itemView.findViewById(R.id.txt_TeamName);
        }
    }

}
