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
    private static final int BOARD_ITEM = 1;
    private static final int SUB_ITEM = 2;

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

    static class BoardViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public BoardViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.whiteboard);
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
        } else if(viewType == BOARD_ITEM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_whiteboard_item, parent, false);
            BoardViewHolder holder = new BoardViewHolder(view);
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
            Log.d(getClass().getCanonicalName(),"MAIN ITEM");
        }else if (viewType == BOARD_ITEM){

        } else {
            Log.d(getClass().getCanonicalName(),"SUB ITEM");
            if (mHistories.size() > 1) {
                SearchBean searchBean = mHistories.get(position - 2);
                if (searchBean != null) {
                    Log.d(getClass().getCanonicalName(),searchBean.toString());
                    HistoryViewHolder viewHolder = (HistoryViewHolder) holder;
                }
            }

        }
    }

    @Override
    public int getItemCount() {
        Log.d(getClass().getCanonicalName(),"size "+ mHistories.size());
        return mHistories == null || mHistories.size() == 0 ? 2 : mHistories.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return MAIN_ITEM;
        }else if(position == 1){
            return BOARD_ITEM;
        } else {
            return SUB_ITEM;
        }
    }
}