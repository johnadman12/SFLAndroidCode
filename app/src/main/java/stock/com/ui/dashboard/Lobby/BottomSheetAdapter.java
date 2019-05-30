package stock.com.ui.dashboard.Lobby;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
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
    private static CheckBox lastChecked = null;
    private static int lastCheckedPos = 0;
    private SparseBooleanArray checkedHolder = null;


    public BottomSheetAdapter(Context mContext, int count, ArrayList<String> list) {
        this.mContext = mContext;
        // this.onItemCheckListener = onItemCheckListener;
        this.count = count;
        this.list = list;
//        createCheckedHolder();
    }

    private void createCheckedHolder() {
        checkedHolder = new SparseBooleanArray(list.size());

       /* for (int i =0; i<=list.size();i++){
            if (i == 0)
                checkedHolder.get
            else
            checkedHolder !!.set(i, false);
        }*/
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
        holder.ll_main.setOnClickListener(new View.OnClickListener() {
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

        public CheckBox radioBtn;
        public RelativeLayout rel_lay;
        public LinearLayout ll_main;

        public FeatureListHolder(@NonNull View itemView) {
            super(itemView);
            recylerView = itemView.findViewById(R.id.recylerView);
            txt_Winners = itemView.findViewById(R.id.txt_Winners);
            radioBtn = itemView.findViewById(R.id.radioBtn);
            rel_lay = itemView.findViewById(R.id.rel_lay);
            ll_main = itemView.findViewById(R.id.ll_main);

        }
    }
}
