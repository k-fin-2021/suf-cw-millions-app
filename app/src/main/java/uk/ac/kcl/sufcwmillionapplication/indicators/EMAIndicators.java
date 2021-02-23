package uk.ac.kcl.sufcwmillionapplication.indicators;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.bean.CalculateResult;
import uk.ac.kcl.sufcwmillionapplication.bean.DailyQuote;

public class EMAIndicators extends TechnicalIndicators {

    static public int term = 0;
    public EMAIndicators(int Term){
        term = Term;
    }

    @Override
    public List<CalculateResult> calculate(List<DailyQuote> dailyQuoteList) {
        //start EMA calculate
        //the result set
        List<CalculateResult> result = new ArrayList<>();
        //The first EMA equals the close price of the first day
        CalculateResult cal = new CalculateResult();
        String Date = super.extractLastDate(dailyQuoteList,dailyQuoteList.size()).get(0);
        cal.date = Date;
        Double LastEma = super.extractLastClosingPrice(dailyQuoteList,dailyQuoteList.size()).get(0);
        cal.data = LastEma;
        result.add(cal);

        //Calcute EMA
        Double[] EMAIndex = GetEMAIndex(term);
        for(int i = 1;i<dailyQuoteList.size();i++){
            String date = super.extractAllDate(dailyQuoteList).get(i);
            CalculateResult calculate = new CalculateResult();
            calculate.date = date;
            Double ema = EMAIndex[0] * super.extractAllClosingPrice(dailyQuoteList).get(i) + EMAIndex[1] * LastEma;
            calculate.data = ema;
            result.add(calculate);
            LastEma = ema;
        }

        return result;
    }

    /**
     * get EMAIndex
     * @param term
     * @return
     */

    private  static Double[] GetEMAIndex(int term){
        return new Double[]{2D / (term + 1), (term - 1) * 1.0D / (term + 1)};
    }

}
