package stock.com.ui.dashboard.Lobby;

import android.content.Context;
import android.text.Html;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import stock.com.ui.pojo.WinningList;
import stock.com.utils.StockConstant;

import java.util.ArrayList;

public class BottomSheetAdapter extends RecyclerView.Adapter<BottomSheetAdapter.FeatureListHolder> {
    private Context mContext;
    private int count;
    private ArrayList<WinningList.Pricebreaklist> list;
    private static CheckBox lastChecked = null;
    private static int lastCheckedPos = 0;
    private SparseBooleanArray checkedHolder = null;
    BottonSheetPriceBreakup breakup;
    String contestSizeWinner = "";


    public BottomSheetAdapter(Context mContext, int count, ArrayList<WinningList.Pricebreaklist> list, BottonSheetPriceBreakup breakup, String userContestId) {
        this.mContext = mContext;
        this.count = count;
        this.list = list;
        this.breakup = breakup;
        this.contestSizeWinner = userContestId;
    }

    @NonNull
    @Override
    public FeatureListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_price_breakup, parent, false);
        return new FeatureListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FeatureListHolder holder, final int position) {
        /*for (int i = 0; i < list.size(); i++) {
            if (contestSizeWinner.equalsIgnoreCase(list.get(i).winner)) {
                holder.radioBtn.setChecked(true);
                holder.rel_lay.setBackground(ContextCompat.getDrawable(mContext, R.drawable.green_empty_layout));
            } else {
                holder.rel_lay.setBackground(ContextCompat.getDrawable(mContext, R.drawable.gray_empty_button));
            }
        }*/

        String text = "<font color=#52DF45>(Recommended)</font>";
        if (position == 0) {
            holder.txt_Winners.setText(Html.fromHtml(list.get(position).winner + text));
//            holder.rel_lay.setBackground(ContextCompat.getDrawable(mContext, R.drawable.green_empty_layout));
        } else {
            holder.txt_Winners.setText(list.get(position).winner);
//            holder.rel_lay.setBackground(ContextCompat.getDrawable(mContext, R.drawable.gray_empty_button));

        }

        PriceBreakUpAdapter mAdapter = new PriceBreakUpAdapter(mContext, list.get(position).winners);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        holder.recylerView.setLayoutManager(mLayoutManager);
        holder.recylerView.setItemAnimator(new DefaultItemAnimator());
        holder.recylerView.setAdapter(mAdapter);

      /*  holder.radioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.radioBtn.setChecked(true);
                holder.rel_lay.setBackground(ContextCompat.getDrawable(mContext, R.drawable.green_empty_layout));
                breakup.callAdapter(list.get(position).winners, list.get(position).winner, list.get(position).usercontestSizeId);
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class FeatureListHolder extends RecyclerView.ViewHolder {
        public RecyclerView recylerView;
        public AppCompatTextView txt_Winners;

        public RadioButton radioBtn;
        public LinearLayout rel_lay;


        public FeatureListHolder(@NonNull View itemView) {
            super(itemView);
            recylerView = itemView.findViewById(R.id.recylerView);
            txt_Winners = itemView.findViewById(R.id.txt_Winners);
            radioBtn = itemView.findViewById(R.id.radioBtn);
            rel_lay = itemView.findViewById(R.id.rel_lay);

        /*radioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               radioBtn.setChecked(true);
                rel_lay.setBackground(ContextCompat.getDrawable(mContext, R.drawable.green_empty_layout));
                breakup.callAdapter(list.get(getAdapterPosition()).winners, list.get(getAdapterPosition()).winner, list.get(getAdapterPosition()).usercontestSizeId);
            }
        }); */

            recylerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    radioBtn.setChecked(true);
                    rel_lay.setBackground(ContextCompat.getDrawable(mContext, R.drawable.green_empty_layout));
                    breakup.callAdapter(list.get(getAdapterPosition()).winners, list.get(getAdapterPosition()).winner, list.get(getAdapterPosition()).usercontestSizeId);

                }
            });
        }
    }


}
