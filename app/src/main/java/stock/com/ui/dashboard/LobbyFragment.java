package stock.com.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import stock.com.AppBase.BaseFragment;
import stock.com.R;
import stock.com.ui.dashboard.home.adapter.LobbyContestAdapter;

public class LobbyFragment extends BaseFragment {
    RecyclerView recycleContest;
    LobbyContestAdapter lobbyContestAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycleContest = view.findViewById(R.id.recyclerView_contest);
        setContestAdapter();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lobby, container, false);
    }

    private void setContestAdapter() {
        LinearLayoutManager ll = new LinearLayoutManager(getContext());
        recycleContest.setLayoutManager(ll);
        lobbyContestAdapter = new LobbyContestAdapter(getContext());
        recycleContest.setAdapter(lobbyContestAdapter);
        //recyclerView_stock_name.addItemDecoration(CirclePagerIndicatorDecoration(activity))
    }
}
