package uk.ac.kcl.sufcwmillionapplication.indicators;
import java.util.ArrayList;
import java.util.List;
import uk.ac.kcl.sufcwmillionapplication.bean.CalculateResult;
import uk.ac.kcl.sufcwmillionapplication.bean.DailyQuote;


public class MACDAVGIndicators extends TechnicalIndicators {

    @Override
    public List<CalculateResult> calculate(List<DailyQuote> dailyQuoteList) {
        //calculation of MACD
        EMAIndicators ema26 = (EMAIndicators) IndicatorFactory.get(IndicatorNames.EMA_26);
        List<CalculateResult> res26 = ema26.calculate(dailyQuoteList);
        EMAIndicators ema12 = (EMAIndicators) IndicatorFactory.get(IndicatorNames.EMA_12);
        List<CalculateResult> res12 = ema12.calculate(dailyQuoteList);
        List<CalculateResult> MACD_results = new ArrayList<>();

        System.out.println(ema12);
        int j = 0;
        if(res12.size() > res26.size()){
            for (int i = 0; i < res12.size(); i++) {
                CalculateResult cal = new CalculateResult();
                if(!res12.get(i).date.equals(res26.get(j).date)){
                    continue;
                }
                else {
                    cal.date = res12.get(i).date;
                    double value1 = res12.get(i).data;
                    double value2 = res26.get(j).data;
                    j = j + 1;
                    cal.data = value1 - value2;
                    MACD_results.add(cal);
                }
            }
        }
        else {
            for (int i = 0; i < res12.size(); i++) {
                CalculateResult cal = new CalculateResult();
                double value1 = res12.get(i).data;
                double value2 = res26.get(i).data;
                cal.date = res12.get(i).date;
                cal.data = value1 - value2;
                MACD_results.add(cal);
            }
        }

        //calculation of MACDAVG
        List<CalculateResult> result = new ArrayList<>();
        Double[] MACDAVGIndex = getEMAIndex(9);
        if(MACD_results.isEmpty() || MACD_results == null){
            return result;
        }
        if(result.isEmpty()){
            // add data of the first day
            CalculateResult calculateResult = new CalculateResult();
            calculateResult.data = MACD_results.get(0).data;
            calculateResult.date = MACD_results.get(0).date;
            result.add(calculateResult);
            System.out.println("MACD:"+MACD_results.get(0).data+','+MACD_results.get(0).date);
            System.out.println("MACDAVG:"+MACD_results.get(0).data+','+MACD_results.get(0).date);
        }
        for(int i = 1;i < MACD_results.size() ; i++) {
            CalculateResult calculateResult = new CalculateResult();
            calculateResult.date = MACD_results.get(i).date;
            calculateResult.data= MACDAVGIndex[0] * MACD_results.get(i).data + MACDAVGIndex[1] * result.get(i-1).data;
            System.out.println("MACD:"+MACD_results.get(i).data+','+MACD_results.get(i).date);
            result.add(calculateResult);
            System.out.println("MACDAVG:"+result.get(i).data+','+result.get(i).date);
        }
        System.out.println("-------------");
        return result;

    }
}

