package uk.ac.kcl.sufcwmillionapplication.indicators;

import java.util.ArrayList;
import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.bean.CalculateResult;
import uk.ac.kcl.sufcwmillionapplication.bean.DailyQuote;

public class MACDIndicators extends TechnicalIndicators {

    @Override
    public List<CalculateResult> calculate(List<DailyQuote> dailyQuoteList) {
        EMAIndicators ema26 = new EMAIndicators(26);
        EMAIndicators ema12 = new EMAIndicators(12);
        List<CalculateResult> res12 = ema12.calculate(dailyQuoteList);
        List<CalculateResult> res26 = ema26.calculate(dailyQuoteList);
        List<CalculateResult> results = new ArrayList<>();
        CalculateResult cal = new CalculateResult();
        for(int i = 0; i < res12.size(); i++){
            double value1 = res12.get(i).data;
            double value2 = res26.get(i).data;
            cal.date = res12.get(i).date;
            cal.data = value1 - value2;
            results.add(cal);
        }
        return results;
    }
}