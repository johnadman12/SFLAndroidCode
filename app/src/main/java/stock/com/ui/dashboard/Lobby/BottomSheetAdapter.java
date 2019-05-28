package stock.com.ui.dashboard.Lobby;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import stock.com.R;
import stock.com.utils.StockConstant;

import java.util.ArrayList;

public class BottomSheetAdapter extends RecyclerView.Adapter<BottomSheetAdapter.FeatureListHolder> {
    private Context mContext;
    private int count;
    private ArrayList<String> list;

    public BottomSheetAdapter(Context mContext, int count, ArrayList<String> list) {
        this.mContext = mContext;
        // this.onItemCheckListener = onItemCheckListener;
        this.count = count;
        this.list = list;
    }

    @NonNull
    @Override
    public FeatureListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_price_breakup, parent, false);
        return new FeatureListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FeatureListHolder holder, int position) {

        holder.txt_Winners.setText(list.get(position) + " " + "Winners");
        PriceBreakUpAdapter mAdapter = new PriceBreakUpAdapter(mContext);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        holder.recylerView.setLayoutManager(mLayoutManager);
        holder.recylerView.setItemAnimator(new DefaultItemAnimator());
        holder.recylerView.setAdapter(mAdapter);

        holder.radioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.radioBtn.setChecked(true);
                holder.rel_lay.setBackground(ContextCompat.getDrawable(mContext, R.drawable.green_empty_layout));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class FeatureListHolder extends RecyclerView.ViewHolder {
        public RecyclerView recylerView;
        public AppCompatTextView txt_Winners;

        public RadioButton radioBtn;
        public RelativeLayout rel_lay;

        public FeatureListHolder(@NonNull View itemView) {
            super(itemView);
            recylerView = itemView.findViewById(R.id.recylerView);
            txt_Winners = itemView.findViewById(R.id.txt_Winners);
            radioBtn = itemView.findViewById(R.id.radioBtn);
            rel_lay = itemView.findViewById(R.id.rel_lay);

        }
    }
}
