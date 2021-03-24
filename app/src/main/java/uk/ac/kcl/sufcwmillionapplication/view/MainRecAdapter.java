package uk.ac.kcl.sufcwmillionapplication.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.R;
import uk.ac.kcl.sufcwmillionapplication.activity.AnalysisActivity;
import uk.ac.kcl.sufcwmillionapplication.activity.DataNotFoundActivity;
import uk.ac.kcl.sufcwmillionapplication.activity.InternalErrorActivity;
import uk.ac.kcl.sufcwmillionapplication.api.ShareDao;
import uk.ac.kcl.sufcwmillionapplication.api.impl.YahooShareDaoImpl;
import uk.ac.kcl.sufcwmillionapplication.bean.DailyQuote;
import uk.ac.kcl.sufcwmillionapplication.bean.SearchBean;
import uk.ac.kcl.sufcwmillionapplication.bean.SymbolInfo;
import uk.ac.kcl.sufcwmillionapplication.utils.CacheUtils;
import uk.ac.kcl.sufcwmillionapplication.utils.CommonUtils;
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
        LinearLayout hints;

        public BoardViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.whiteboard);
            hints = view.findViewById(R.id.no_history_hints);
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
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String name = viewHolder.searchView.getText().toString();

                    if(CommonUtils.isEmptyString(name)){
                        showWarning("Please specify the symbol name.");
                        return;
                    }

                    Date startDate = null;
                    Date endDate = null;

                    try {
                        if(mContext.getString(R.string.index_start_date).equalsIgnoreCase(
                                viewHolder.tvStartDate.getText().toString())){
                            showWarning("Please select the start date");
                            return;
                        }

                        if(mContext.getString(R.string.index_end_date).equalsIgnoreCase(
                                viewHolder.tvEndDate.getText().toString())){
                            showWarning("Please select the end date");
                            return;
                        }

                        startDate = sdf.parse(viewHolder.tvStartDate.getText().toString());
                        endDate = sdf.parse(viewHolder.tvEndDate.getText().toString());
                        if(startDate.after(endDate)){
                            showWarning("Start date cannot later than end date");
                            return;
                        }
                        Calendar startDateCal = Calendar.getInstance();
                        Calendar endDateCal = Calendar.getInstance();
                        startDateCal.setTime(startDate);
                        endDateCal.setTime(endDate);
                        int startYear = startDateCal.get(Calendar.YEAR);
                        int endYear = endDateCal.get(Calendar.YEAR);
                        if(startYear != endYear){
                            if( endYear - startYear > 3 ){
                                showWarning("The maximum range of date is two years");
                                return;
                            }
                            int timeDistance = 0 ;
                            int maxInterval = 365 * 2;
                            for(int i = startYear; i < endYear; i ++ ) {
                                if( (i % 4 ==0 && i % 100!=0) || i % 400 == 0){
                                    timeDistance += 366;
                                    maxInterval ++;
                                }else{
                                    timeDistance += 365;
                                }
                            }
                            timeDistance += (endDateCal.get(Calendar.DAY_OF_YEAR) - startDateCal.get(Calendar.DAY_OF_YEAR));
                            if(timeDistance > maxInterval){
                                showWarning("The maximum range of date is two years");
                                return;
                            }
                        }
                    } catch (ParseException e) {
                        try {
                            startDate = sdf.parse("1900-01-01");
                            endDate = sdf.parse("1900-01-01");
                        } catch (ParseException parseException) {
                            parseException.printStackTrace();
                        }
                    }
                    SearchBean tmpHistory = new SearchBean(name,startDate,endDate);
                    Thread searchThread = new Thread(new SearchTask(tmpHistory, showProgress(),true));
                    searchThread.start();
                }
            });
            Log.d(getClass().getCanonicalName(),"MAIN ITEM");
        }else if (viewType == BOARD_ITEM){
            BoardViewHolder boardViewHolder = (BoardViewHolder) holder;
            if(mHistories.isEmpty()){
                boardViewHolder.hints.setVisibility(View.VISIBLE);
            }else{
                boardViewHolder.hints.setVisibility(View.GONE);
            }
        } else {
            Log.d(getClass().getCanonicalName(),"SUB ITEM");
            if (mHistories.size() > 0) {
                SearchBean searchBean = mHistories.get(position - 2);
                if (searchBean != null) {
                    Log.d(getClass().getCanonicalName(),searchBean.toString());
                    HistoryViewHolder viewHolder = (HistoryViewHolder) holder;
                    viewHolder.historyName.setText(mHistories.get(position-2).name);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    String startDate = sdf.format(mHistories.get(position-2).startDate);
                    String endDate = sdf.format(mHistories.get(position-2).endDate);
                    viewHolder.tvDate.setText(startDate + " - " + endDate);
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SearchBean searchBean = mHistories.get(position - 2);
                        Thread searchThread = new Thread(new SearchTask(searchBean, showProgress(),false));
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
                        String zeroMonth = ((monthOfYear+1) < 10)?"0":"";
                        String zeroDay = (dayOfMonth < 10)?"0":"";
                        tv.setText(year+"-"+ zeroMonth + (monthOfYear+1)+"-" + zeroDay+dayOfMonth);
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
        private boolean saveHistory = false;

        public SearchTask(SearchBean tmpHistory,ProgressDialog progressDialog, boolean saveHistory) {
            this.tmpHistory = tmpHistory;
            this.progressDialog = progressDialog;
            this.saveHistory = saveHistory;
        }

        @Override
        public void run() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String beginDate = sdf.format(tmpHistory.getStartDate());
            long timeStamp = tmpHistory.getStartDate().getTime();
            timeStamp = timeStamp - (1000L * 3600 * 24 * 56);
            tmpHistory.getStartDate().setTime(timeStamp);
            List<DailyQuote> quotesCal;
            SymbolInfo symbolInfo;
            List<DailyQuote> quotesDisplay = new ArrayList<>();
            symbolInfo = shareDao.getInfoOfSymbol(tmpHistory);
            if( symbolInfo == null ){
                progressDialog.dismiss();
                Intent jumpToError = new Intent(mContext, InternalErrorActivity.class);
                ((AppCompatActivity) mContext).startActivityForResult(jumpToError, 1);
                return;
            }else if(CommonUtils.isEmptyString(symbolInfo.getSymbol())){
                progressDialog.dismiss();
                Intent jumpToNotFound = new Intent(mContext, DataNotFoundActivity.class);
                ((AppCompatActivity)mContext).startActivityForResult(jumpToNotFound,1);
                return;
            }
            try{
                if (CacheUtils.isCache(mContext,tmpHistory)){
                    quotesCal = CacheUtils.getCacheDailyQuote(mContext,tmpHistory);
                    if (quotesCal == null || quotesCal.size()==0){
                        quotesCal = CacheUtils.getCacheDailyQuote(mContext,tmpHistory);
                        CacheUtils.updateCache(mContext,tmpHistory,quotesCal,symbolInfo);
                    }
                    Log.d("cache","get data from cache");
                }else {
                    quotesCal = shareDao.getHistoryQuotes(tmpHistory);
                    CacheUtils.updateCache(mContext,tmpHistory,quotesCal,symbolInfo);
                    Log.d("cache","get data from network");
                }
            }catch (Exception e){
                quotesCal = shareDao.getHistoryQuotes(tmpHistory);
                CacheUtils.updateCache(mContext,tmpHistory,quotesCal,symbolInfo);
                Log.d("cache","get data from network");
            };
            SimpleDateFormat tmpSDF = new SimpleDateFormat("yyyy-MM-dd");
            for (DailyQuote dailyQuote:quotesCal){
                try {
                    if (tmpSDF.parse(dailyQuote.date).after(tmpSDF.parse(beginDate))||
                            tmpSDF.parse(dailyQuote.date).equals(tmpSDF.parse(beginDate))){
                        quotesDisplay.add(dailyQuote);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            Log.d(TAG,"Result size --> " + quotesCal.size() + ", quotesDisplay --> " + quotesDisplay.size());
            if( quotesCal == null ) {
                progressDialog.dismiss();
                Intent jumpToError = new Intent(mContext, InternalErrorActivity.class);
                ((AppCompatActivity) mContext).startActivityForResult(jumpToError, 1);
                return;
            }else if( (quotesCal != null && quotesCal.isEmpty())){
                progressDialog.dismiss();
                Intent jumpToNotFound = new Intent(mContext, DataNotFoundActivity.class);
                ((AppCompatActivity)mContext).startActivityForResult(jumpToNotFound,1);
                return;
            }
            try{
                tmpHistory.setStartDate(sdf.parse(beginDate));
            }catch (Exception ex){

            }
            if(saveHistory){
                SPUtils.saveHistory(mContext,mHistories,tmpHistory);
            }
            Intent intent = new Intent(mContext, AnalysisActivity.class);
            intent.putExtra("symbolInfo",symbolInfo);
            // FIXME: Here may has bad performance...
            intent.putExtra("quotesCal",(Serializable) quotesCal);
            intent.putExtra("quotesDisplay",(Serializable) quotesDisplay);
            intent.putExtra("startDisplayDate", beginDate);
            progressDialog.dismiss();
            ((AppCompatActivity)mContext).startActivityForResult(intent,1);
        }
    }

    private void showWarning(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(msg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //DO Nothing;
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        .setBackgroundColor(mContext.getResources().getColor(R.color.kcl_grey));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        .setTextColor(mContext.getResources().getColor(R.color.white));
            }
        });
        dialog.show();
    }

}