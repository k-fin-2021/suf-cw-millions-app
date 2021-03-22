package uk.ac.kcl.sufcwmillionapplication.indicators;

import java.util.ArrayList;
import java.util.List;

import uk.ac.kcl.sufcwmillionapplication.bean.CalculateResult;
import uk.ac.kcl.sufcwmillionapplication.bean.DailyQuote;

public class SMAIndicators extends TechnicalIndicators {

    private int dataFrame = 0;

    public SMAIndicators(){
        this.dataFrame = 26;
    }

    public SMAIndicators(int Term){
        this.dataFrame = Term;
    }
    @Override
    public List<CalculateResult> calculate(List<DailyQuote> dailyQuoteList) {
        double result = 0;
        dataFrame = 26;
        List<CalculateResult> results = new ArrayList<>();
        if (dailyQuoteList.size()<dataFrame){
            for (DailyQuote dailyQuote:dailyQuoteList){
                result+=dailyQuote.close;
            }

            CalculateResult calculateResult = new CalculateResult();
            calculateResult.data = result/dailyQuoteList.size();
            calculateResult.date = dailyQuoteList.get(dailyQuoteList.size()-1).date;
            results.add(calculateResult);
            return results;
        }else {
            int curPos = 0;
            while (dailyQuoteList.size()-curPos>=dataFrame){
                CalculateResult calculateResult = new CalculateResult();
                double tmpResult = 0;
                for (int index=curPos;index<curPos+dataFrame;index++){
                    tmpResult+=dailyQuoteList.get(index).close;
                }

                calculateResult.data = tmpResult/dataFrame;
                calculateResult.date = dailyQuoteList.get(curPos+dataFrame-1).date;
                results.add(calculateResult);
                curPos++;
            }
            return results;
        }

    }
}
