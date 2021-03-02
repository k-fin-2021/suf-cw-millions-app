package uk.ac.kcl.sufcwmillionapplication.indicators;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.bean.CalculateResult;
import uk.ac.kcl.sufcwmillionapplication.bean.DailyQuote;

public class EMAIndicators extends TechnicalIndicators {

    private int term = 0;
    public EMAIndicators(int Term){
        term = Term;
    }

    @Override
    public List<CalculateResult> calculate(List<DailyQuote> dailyQuoteList) {
        //if the length of dailyQuoteList bigger than term, we get the most recent term data of dailyQuoteList
        List<DailyQuote> dq = new ArrayList<DailyQuote>();
        if(dailyQuoteList.size()>term){
            for(int t=term;t>0;t--){
                dq.add(dailyQuoteList.get(dailyQuoteList.size()-t));
            }
        }else{
            dq = dailyQuoteList;
        }
        //start EMA calculate
        //the result set
        List<CalculateResult> result = new ArrayList<>();
        //The first EMA equals the close price of the first day
        CalculateResult cal = new CalculateResult();
        String lastDate = super.extractLastDate(dq,dq.size()).get(0);
        cal.date = lastDate;
        Double lastEma = super.extractLastClosingPrice(dq,dq.size()).get(0);
        cal.data = lastEma;
        result.add(cal);

        //Calcute EMA
        Double[] EMAIndex = getEMAIndex(term);
        for(int i = 1;i<dq.size();i++){
            String date = super.extractAllDate(dq).get(i);
            CalculateResult calculate = new CalculateResult();
            calculate.date = date;
            Double ema = EMAIndex[0] * super.extractAllClosingPrice(dq).get(i) + EMAIndex[1] * lastEma;
            calculate.data = ema;
            result.add(calculate);
            lastEma = ema;
        }

        return result;
    }

}
