package uk.ac.kcl.sufcwmillionapplication.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.R;
import uk.ac.kcl.sufcwmillionapplication.activity.AnalysisActivity;
import uk.ac.kcl.sufcwmillionapplication.api.ShareDao;
import uk.ac.kcl.sufcwmillionapplication.api.impl.YahooShareDaoImpl;
import uk.ac.kcl.sufcwmillionapplication.bean.DailyQuote;
import uk.ac.kcl.sufcwmillionapplication.bean.SearchBean;
import uk.ac.kcl.sufcwmillionapplication.bean.SymbolInfo;
import uk.ac.kcl.sufcwmillionapplication.utils.SPUtils;
public class MainRecAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "MainRecAdapter";

    private List<SearchBean> mHistories;
    private Context mContext;
    private static final int MAIN_ITEM = 0;
    private static final int BOARD_ITEM = 1;
    private static final int SUB_ITEM = 2;

    private static ShareDao shareDao = new YahooShareDaoImpl();

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
        TextView tvDate;

        public HistoryViewHolder(View view) {
            super(view);
            historyName = view.findViewById(R.id.history_name);
            tvDate = view.findViewById(R.id.date);
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
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String name = viewHolder.searchView.getText().toString();
                    Date startDate = null;
                    Date endDate = null;

                    try {
                        startDate = sdf.parse(viewHolder.tvStartDate.getText().toString());
                        endDate = sdf.parse(viewHolder.tvEndDate.getText().toString());

                    } catch (ParseException e) {
                        try {
                            startDate = sdf.parse("1900-01-01");
                            endDate = sdf.parse("1900-01-01");
                        } catch (ParseException parseException) {
                            parseException.printStackTrace();
                        }
                    }
                    SearchBean tmpHistory = new SearchBean(name,startDate,endDate);
                    SPUtils.saveHistory(mContext,mHistories,tmpHistory);
                    Thread searchThread = new Thread(new SearchTask(tmpHistory, showProgress()));
                    searchThread.start();
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
                    viewHolder.historyName.setText(mHistories.get(position-2).name);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/mm/dd");
                    String startDate = sdf.format(mHistories.get(position-2).startDate);
                    String endDate = sdf.format(mHistories.get(position-2).endDate);
                    viewHolder.tvDate.setText(startDate + " - " + endDate);
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SearchBean searchBean = mHistories.get(position - 2);
                        Thread searchThread = new Thread(new SearchTask(searchBean, showProgress()));
                        searchThread.start();
                    }
                });
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

    private ProgressDialog showProgress(){
        ProgressDialog progressDialog= new ProgressDialog(mContext);
        progressDialog.setTitle("Fetching data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        return progressDialog;
    }

    private class SearchTask implements Runnable{

        private SearchBean tmpHistory;
        private ProgressDialog progressDialog;

        public SearchTask(SearchBean tmpHistory,ProgressDialog progressDialog) {
            this.tmpHistory = tmpHistory;
            this.progressDialog = progressDialog;
        }

        @Override
        public void run() {
            List<DailyQuote> quotes = shareDao.getHistoryQuotes(tmpHistory);
            SymbolInfo symbolInfo = shareDao.getInfoOfSymbol(tmpHistory);
            if(quotes == null || symbolInfo == null){
                //TODO: Data not found...
            }
            SPUtils.saveHistory(mContext,mHistories,tmpHistory);
            Intent intent = new Intent(mContext, AnalysisActivity.class);
            intent.putExtra("symbolInfo",symbolInfo);
            // FIXME: Here may has bad performance...
            intent.putExtra("quotes",(Serializable) quotes);
            progressDialog.dismiss();
            ((AppCompatActivity)mContext).startActivityForResult(intent,1);
        }
    }

}