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
        //MACDAVG_EMA of yesterday
        double LastEma=0;
        int counter=0;
        int j = 0;
        if(res12.size() > res26.size()) {
            for (int i = 0; i < res12.size(); i++) {
                if(!res12.get(i).date.equals(res26.get(j).date)){
                    continue;
                }else{
                    CalculateResult cal_MACDAVG = new CalculateResult();
                    double value1 = res12.get(i).data; //i
                    double value2 = res26.get(j).data; //j
                    j++;
                    cal_MACDAVG.date = res12.get(i).date;
                    double MACD_result = value1 - value2;
                    if(counter==0) { //first date
                        counter++;
                        //The first MACDAVG_EMA, equals the MACD of the first day
                        LastEma = MACD_result;
                        System.out.println("macd:" + MACD_result);
                        // caluculate MACDAVG_EMA, term=9
                        Double MACDAVG_EMA = LastEma;
                        System.out.println("MACDAVG_first:" + MACDAVG_EMA);
                        // insert calculated data
                        cal_MACDAVG.data = MACDAVG_EMA;
                        result.add(cal_MACDAVG);
                        // change the lastema
                        LastEma = MACDAVG_EMA;
                    }else{
                        System.out.println("macd:" + MACD_result);
                        // caluculate MACDAVG_EMA, term=9
                        Double[] EMAIndex = getEMAIndex(9);
                        Double MACDAVG_EMA = EMAIndex[0] * MACD_result + EMAIndex[1] * LastEma;
                        System.out.println("MACDAVG:" + MACDAVG_EMA);
                        // insert calculated data
                        cal_MACDAVG.data = MACDAVG_EMA;
                        result.add(cal_MACDAVG);
                        // change the lastema
                        LastEma = MACDAVG_EMA;
                    }
                }
            }
            return result;
        }  //res12.size() < res26.size()
        else {
            for (int i = 0; i < res12.size(); i++) {
                CalculateResult cal_MACDAVG = new CalculateResult();
                double value1 = res12.get(i).data;
                double value2 = res26.get(i).data;
                cal_MACDAVG.date = res12.get(i).date;
                double MACD_result = value1 - value2;
                if(counter==0) { //first date
                    counter++;
                    //The first MACDAVG_EMA, equals the MACD of the first day
                    LastEma = MACD_result;
                    System.out.println("macd:" + MACD_result);
                    // caluculate MACDAVG_EMA, term=9
                    Double MACDAVG_EMA = LastEma;
                    System.out.println("MACDAVG_first:" + MACDAVG_EMA);
                    // insert calculated data
                    cal_MACDAVG.data = MACDAVG_EMA;
                    result.add(cal_MACDAVG);
                    // change the lastema
                    LastEma = MACDAVG_EMA;
                }else{
                    System.out.println("macd:" + MACD_result);
                    // caluculate MACDAVG_EMA, term=9
                    Double[] EMAIndex = getEMAIndex(9);
                    Double MACDAVG_EMA = EMAIndex[0] * MACD_result + EMAIndex[1] * LastEma;
                    System.out.println("MACDAVG:" + MACDAVG_EMA);
                    // insert calculated data
                    cal_MACDAVG.data = MACDAVG_EMA;
                    result.add(cal_MACDAVG);
                    // change the lastema
                    LastEma = MACDAVG_EMA;
                }
            }
            return result;
        }
    }
}
