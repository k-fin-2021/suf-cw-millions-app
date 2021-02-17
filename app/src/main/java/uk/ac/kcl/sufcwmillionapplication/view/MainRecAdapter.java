package uk.ac.kcl.sufcwmillionapplication.view;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.R;
import uk.ac.kcl.sufcwmillionapplication.bean.SearchBean;

public class MainRecAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SearchBean> mHistories;

    private static final int MAIN_ITEM = 0;
    private static final int SUB_ITEM = 1;

    public void setHistories(List<SearchBean> mHistories) {
        this.mHistories = mHistories;
        notifyDataSetChanged();
    }

    public MainRecAdapter() {

    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView historyName;

        public HistoryViewHolder(View view) {
            super(view);
            historyName = view.findViewById(R.id.history_name);
        }

    }

    static class MainViewHolder extends RecyclerView.ViewHolder {
        TextView searchView;

        public MainViewHolder(View view) {
            super(view);
            searchView = view.findViewById(R.id.search);
        }

    }



    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MAIN_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_main_item, parent, false);
            MainViewHolder holder = new MainViewHolder(view);
            return holder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_sub_item, parent, false);
            HistoryViewHolder holder = new HistoryViewHolder(view);
            return holder;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        Log.d(getClass().getCanonicalName(),"ITEM "+viewType+"  "+position);
        if (viewType == MAIN_ITEM) {
            MainViewHolder viewHolder = (MainViewHolder) holder;
            viewHolder.searchView.setText("HK");
            Log.d(getClass().getCanonicalName(),"MAIN ITEM");
        } else {
            Log.d(getClass().getCanonicalName(),"SUB ITEM");
            if (mHistories.size() > 1) {
                SearchBean searchBean = mHistories.get(position - 1);
                if (searchBean != null) {
                    Log.d(getClass().getCanonicalName(),searchBean.toString());
                    HistoryViewHolder viewHolder = (HistoryViewHolder) holder;
                    viewHolder.historyName.setText("History");
                }
            }

        }
    }

    @Override
    public int getItemCount() {
        Log.d(getClass().getCanonicalName(),"size "+ mHistories.size());
        return mHistories == null || mHistories.size() == 0 ? 1 : mHistories.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return MAIN_ITEM;
        }else {
            return SUB_ITEM;
        }
    }
}