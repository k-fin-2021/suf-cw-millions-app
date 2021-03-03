package uk.ac.kcl.sufcwmillionapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.R;
import uk.ac.kcl.sufcwmillionapplication.bean.CalculateResult;
import uk.ac.kcl.sufcwmillionapplication.bean.DailyQuote;
import uk.ac.kcl.sufcwmillionapplication.bean.SymbolInfo;
import uk.ac.kcl.sufcwmillionapplication.indicators.IndicatorFactory;
import uk.ac.kcl.sufcwmillionapplication.indicators.IndicatorNames;

public class AnalysisActivity extends AppCompatActivity {
    public final static String TAG = "AnalysisActivity";

    private CombinedChart chart;

    private SymbolInfo symbolInfo;
    private List<DailyQuote> quotes;

    private CandleData candleData;

    private boolean showSMA = false;
    private boolean showEMA = false;
    private boolean showMACD = false;
    private boolean showMACDAVG = false;

    private List<String> xDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analysis_main);
        chart = findViewById(R.id.analysis_chart);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ImageButton backBtn = findViewById(R.id.analysis_bar_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnalysisActivity.this.finish();
            }
        });
        init();
    }

    void init() {
        Intent intent = getIntent();
        symbolInfo = (SymbolInfo) intent.getSerializableExtra("symbolInfo");
        quotes = (List<DailyQuote>) intent.getSerializableExtra("quotes");
        TextView tvSymbolName = findViewById(R.id.analysis_board_name);
        tvSymbolName.setText(symbolInfo.getSymbol());
        TextView tvOrgName = findViewById(R.id.analysis_board_content);
        tvOrgName.setText(symbolInfo.getLongName());
        this.prepareCandleData();
        this.setupChart();
        this.initListeners();
    }

    void initListeners(){
        CheckBox smaChk = findViewById(R.id.SMA_CHK);
        smaChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                showSMA = isChecked;
                updateData();
            }
        });

        CheckBox emaChk = findViewById(R.id.EMA_CHK);
        emaChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                showEMA = isChecked;
                updateData();
            }
        });

        CheckBox macdChk = findViewById(R.id.MACD_CHK);
        macdChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                showMACD = isChecked;
                updateData();
            }
        });

        CheckBox macdAvgChk = findViewById(R.id.MACDAVG_CHK);
        macdAvgChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                showMACDAVG = isChecked;
                updateData();
            }
        });
    }

    private void updateData(){
        chart.clear();
        List<ILineDataSet> dataSets = new ArrayList<>();
        CombinedData data = new CombinedData();
        data.setData(candleData);
        if(showEMA){
            dataSets.add(getEMAList());
        }
        if(showMACD){
            dataSets.add(getMACDList());
        }
        if(showMACDAVG){
            dataSets.add(getMACDAVGList());
        }
        if(showSMA){
            dataSets.add(getSMAList());
        }
        if(!dataSets.isEmpty()){
            data.setData(new LineData(dataSets));
        }
        Log.i(TAG,"showEMA-->" + showEMA + " showMACD-->" + showMACD + " showMACDAVG-->"+showMACDAVG + " showSMA-->" + showSMA);
        chart.setData(data);
    }


    private void setupChart() {

        chart.setPadding(10,10 ,10,20);
        chart.setDrawGridBackground(false);
        chart.getDescription().setEnabled(false);
        chart.setDrawBorders(false);

        chart.setDrawOrder(new CombinedChart.DrawOrder[]{
                DrawOrder.LINE, DrawOrder.CANDLE
        });

        chart.setBackgroundColor(Color.rgb(246, 246, 246));

        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setDrawAxisLine(false);
        chart.getAxisLeft().setDrawGridLines(false);

        chart.getXAxis().setDrawAxisLine(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setYOffset(-10);

        xDate = new ArrayList<>();
        if(!quotes.isEmpty()){
            for (int i = 0; i < quotes.size() ; i ++ ){
                xDate.add(quotes.get(i).date);
            }
        }
        chart.getXAxis().setValueFormatter(new ValueFormatter(){
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return xDate.get((int) value);
            }
        });

        this.updateData();
        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);

        chart.animateX(2500);

    }

    private void prepareCandleData(){
        if(quotes == null){
            //TODO: Jump to error page
            return;
        }
        ArrayList<CandleEntry> values = new ArrayList<>();
        int size = quotes.size();
        for (int i = 0; i < size ; i ++){
            DailyQuote quote = quotes.get(i);
            values.add(new CandleEntry(i, (float)quote.high, (float)quote.low,
                    (float)quote.open, (float)quote.close));
        }
        CandleDataSet set1 = new CandleDataSet(values, this.symbolInfo.getSymbol());

        set1.setDrawIcons(false);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setShadowColor(Color.DKGRAY);
        set1.setShadowWidth(0.7f);
        set1.setDecreasingColor(Color.rgb(231,59,63));
        set1.setDecreasingPaintStyle(Paint.Style.FILL);
        set1.setIncreasingColor(Color.rgb(35,152,136));
        set1.setIncreasingPaintStyle(Paint.Style.FILL);
        set1.setNeutralColor(Color.rgb(146,147,151));

        candleData = new CandleData(set1);

    }

    private ILineDataSet getEMAList(){
        List<CalculateResult> res = IndicatorFactory.get(IndicatorNames.EMA_9).calculate(this.quotes);
        return extractData(res,"EMA", Color.rgb(15,125,254));
    }

    private ILineDataSet getSMAList(){
        List<CalculateResult> res = IndicatorFactory.get(IndicatorNames.SMA_20).calculate(this.quotes);
        return extractData(res, "SMA",Color.rgb(251,83,7));
    }

    private ILineDataSet getMACDList(){
        List<CalculateResult> res = IndicatorFactory.get(IndicatorNames.MACD_12_26).calculate(this.quotes);
        return extractData(res,"MACD",Color.rgb(39,187,209));
    }

    private ILineDataSet getMACDAVGList(){
        List<CalculateResult> res = IndicatorFactory.get(IndicatorNames.MACD_AVG).calculate(this.quotes);
        return extractData(res,"MACDAVG",Color.rgb(106,62,181));
    }

    private ILineDataSet extractData(List<CalculateResult> res, String label, int color){
        ArrayList<Entry> values = new ArrayList<>();
        int index = this.quotes.size() - res.size();
        for (int i = 0; i < res.size(); i ++ ){
            float val = (float) res.get(i).data;
            values.add(new Entry((index++), val));
        }

        LineDataSet dataSet = new LineDataSet(values, label);
        dataSet.setLineWidth(0.75f);
        dataSet.setColor(color);
        dataSet.setDrawValues(false);
        dataSet.setDrawCircles(false);
        dataSet.setDrawCircleHole(false);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        return dataSet;
    }
}