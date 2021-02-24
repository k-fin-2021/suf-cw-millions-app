package uk.ac.kcl.sufcwmillionapplication.indicators;

import java.util.ArrayList;
import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.bean.CalculateResult;
import uk.ac.kcl.sufcwmillionapplication.bean.DailyQuote;

public class MACDAVGIndicators extends TechnicalIndicators {

    @Override
    public List<CalculateResult> calculate(List<DailyQuote> dailyQuoteList) {
        //the result set
        List<CalculateResult> result = new ArrayList<>();
        //The first MACDAVG_EMA equals the close price of the first day
        CalculateResult cal = new CalculateResult();
        String Date = super.extractLastDate(dailyQuoteList,dailyQuoteList.size()).get(0);
        cal.date = Date;
        Double LastEma = super.extractLastClosingPrice(dailyQuoteList,dailyQuoteList.size()).get(0);
        cal.data = LastEma;
        result.add(cal);

        //calculate MACD
        EMAIndicators ema26 = new EMAIndicators(26);
        EMAIndicators ema12 = new EMAIndicators(12);
        List<CalculateResult> res12 = ema12.calculate(dailyQuoteList);
        List<CalculateResult> res26 = ema26.calculate(dailyQuoteList);
        List<CalculateResult> MACD_results = new ArrayList<>();
        CalculateResult cal_MACDAVG = new CalculateResult();
        for(int i = 0; i < res12.size(); i++){
            double value1 = res12.get(i).data;
            double value2 = res26.get(i).data;
            // insert date
            cal_MACDAVG.date = res12.get(i).date;
            // caluculate MACD
            double MACD_result = value1 - value2;
            // caluculate MACDAVG_EMA, term=9
            Double[] EMAIndex = GetEMAIndex(9);
            Double MACDAVG_EMA = EMAIndex[0] * MACD_result + EMAIndex[1] * LastEma;
            // insert calculated data
            cal_MACDAVG.data = MACDAVG_EMA;
            result.add(cal_MACDAVG);
            // change the lastema
            LastEma = MACDAVG_EMA;

        }
        return result;
    }

    private  static Double[] GetEMAIndex(int term){
        return new Double[]{2D / (term + 1), (term - 1) * 1.0D / (term + 1)};
    }
}
