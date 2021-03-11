package uk.ac.kcl.sufcwmillionapplication.indicators;

import java.util.ArrayList;
import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.bean.CalculateResult;
import uk.ac.kcl.sufcwmillionapplication.bean.DailyQuote;

public class MACDIndicators extends TechnicalIndicators {

    @Override
    public List<CalculateResult> calculate(List<DailyQuote> dailyQuoteList) {
        EMAIndicators ema26 = (EMAIndicators) IndicatorFactory.get(IndicatorNames.EMA_26);
        List<CalculateResult> res26 = ema26.calculate(dailyQuoteList);
        EMAIndicators ema12 = (EMAIndicators) IndicatorFactory.get(IndicatorNames.EMA_12);
        List<CalculateResult> res12 = ema12.calculate(dailyQuoteList);
        List<CalculateResult> results = new ArrayList<>();

        System.out.println(ema12);
        int j = 0;
        if(res12.size() > res26.size()){
            for (int i = 0; i < res12.size(); i++) {
                CalculateResult cal = new CalculateResult();
                if(!res12.get(i).date.equals(res26.get(j).date)){
                    cal.date = res12.get(i).date;
                    double value1 = res12.get(i).data;
                    double value2 = res26.get(0).data;
                    cal.data = value1 - value2;
                    results.add(cal);
                }
                else {
                    cal.date = res12.get(i).date;
                    double value1 = res12.get(i).data;
                    double value2 = res26.get(j).data;
                    j = j + 1;
                    cal.data = value1 - value2;
                    results.add(cal);
                }
            }
            return results;
        }
        else {
            for (int i = 0; i < res12.size(); i++) {
                CalculateResult cal = new CalculateResult();
                double value1 = res12.get(i).data;
                double value2 = res26.get(i).data;
                cal.date = res12.get(i).date;
                cal.data = value1 - value2;
                results.add(cal);
            }
            return results;
        }
    }
}