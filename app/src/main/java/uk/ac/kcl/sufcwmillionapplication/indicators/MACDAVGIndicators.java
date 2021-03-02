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

        //calculate MACD
        EMAIndicators ema26 = (EMAIndicators) IndicatorFactory.get(IndicatorNames.EMA_26);
        List<CalculateResult> res26 = ema26.calculate(dailyQuoteList);
        EMAIndicators ema12 = (EMAIndicators) IndicatorFactory.get(IndicatorNames.EMA_12);
        List<CalculateResult> res12 = ema12.calculate(dailyQuoteList);
        //The first MACDAVG_EMA, equals the MACD of the first day
        double lastEma=res12.get(0).data - res26.get(0).data;
        for(int i = 0; i < res12.size(); i++){
            CalculateResult calMACDAVG = new CalculateResult();
            double value1 = res12.get(i).data;
            double value2 = res26.get(i).data;
            calMACDAVG.date = res12.get(i).date;
            double MACDResult= value1 - value2;
            System.out.println("macd:"+MACDResult);
            // caluculate MACDAVG_EMA, term=9
            Double[] EMAIndex = getEMAIndex(9);
            System.out.println("lastema:"+lastEma);
            Double MACDAVG_EMA = EMAIndex[0] * MACDResult + EMAIndex[1] * lastEma;
            // insert calculated data
            calMACDAVG.data = MACDAVG_EMA;
            result.add(calMACDAVG);
            // change the lastema
            lastEma = MACDAVG_EMA;
        }
        return result;
    }

}
