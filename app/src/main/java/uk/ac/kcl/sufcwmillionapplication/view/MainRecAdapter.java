package uk.ac.kcl.sufcwmillionapplication.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.R;
import uk.ac.kcl.sufcwmillionapplication.bean.SearchBean;
import uk.ac.kcl.sufcwmillionapplication.bean.SimpleDate;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
public class MainRecAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SearchBean> mHistories;
    private Context mContext;
    private static final int MAIN_ITEM = 0;
    private static final int BOARD_ITEM = 1;
    private static final int SUB_ITEM = 2;

    static Calendar startDate;
    static Calendar endDate;

    public void setHistories(List<SearchBean> mHistories) {
        this.mHistories = mHistories;
        notifyDataSetChanged();
    }

    public MainRecAdapter(Context context) {
        this.mContext = context;
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
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
        TextView tvStartDate;
        TextView tvEndDate;
        TextView searchBtn;

        public MainViewHolder(View view) {
            super(view);
            searchView = view.findViewById(R.id.search);
            searchBtn = view.findViewById(R.id.search_btn);
            tvStartDate = view.findViewById(R.id.start_date);
            tvEndDate = view.findViewById(R.id.end_date);


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
            viewHolder.tvStartDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DataPicker","onClick");
                    if (startDate!=null){
                        initDataPicker(viewHolder.tvStartDate,startDate);
                    }
                }
            });

            viewHolder.tvEndDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DataPicker","onClick");
                    if (endDate!=null){
                        initDataPicker(viewHolder.tvEndDate,endDate);
                    }
                }
            });

            viewHolder.searchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext,"start search start name "+viewHolder.searchView.getText().toString()
                            +"  start date "+startDate.get(Calendar.DAY_OF_MONTH)+ " end date" + endDate.get(Calendar.DAY_OF_MONTH),Toast.LENGTH_SHORT).show();
                }
            });
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

    private void initDataPicker(TextView tv,final Calendar calendar) throws NullPointerException{


        DatePickerDialog datePickerDialog= DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        tv.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                    }
                },
                calendar.get(Calendar.YEAR), // Initial year selection
                calendar.get(Calendar.MONTH), // Initial month selection
                calendar.get(Calendar.DAY_OF_MONTH)// Inital day selection
        );
        datePickerDialog.setAccentColor(mContext.getResources().getColor(R.color.kcl_red));
        datePickerDialog.setCancelColor(Color.WHITE);
        datePickerDialog.setOkColor(Color.WHITE);
        datePickerDialog.show(((AppCompatActivity)mContext).getSupportFragmentManager(), "Datepickerdialog");
    }

}